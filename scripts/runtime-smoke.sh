#!/usr/bin/env bash
set -euo pipefail

PAPER_VERSION="${PAPER_VERSION:-26.2}"
PAPER_BUILD="${PAPER_BUILD:-121}"
EXTRA_PLUGIN_URL="${EXTRA_PLUGIN_URL:-}"
EXTRA_PLUGIN_NAME="${EXTRA_PLUGIN_NAME:-extra-plugin.jar}"
EXPECT_LOG_PATTERN="${EXPECT_LOG_PATTERN:-}"
RUNTIME_VARIANT="${RUNTIME_VARIANT:-base}"
USER_AGENT="EggEmAllReloaded-runtime-smoke/1.0 (https://github.com/Tebrox-Development/EggEmAll2)"
ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
RUNTIME_DIR="${ROOT_DIR}/target/runtime-smoke-${PAPER_VERSION}-${PAPER_BUILD}-${RUNTIME_VARIANT}"
SERVER_DIR="${RUNTIME_DIR}/server"
LOG_FILE="${SERVER_DIR}/logs/latest.log"
BUILD_JSON="${RUNTIME_DIR}/paper-builds.json"
PROJECT_VERSION="$(mvn -B -ntp help:evaluate -Dexpression=project.version -q -DforceStdout)"
PLUGIN_JAR="${ROOT_DIR}/target/EggEmAllReloaded-${PROJECT_VERSION}.jar"

rm -rf "${RUNTIME_DIR}"
mkdir -p "${SERVER_DIR}/plugins"

if [[ ! -f "${PLUGIN_JAR}" ]]; then
  echo "EggEmAll Reloaded build artifact not found: ${PLUGIN_JAR}" >&2
  exit 1
fi

curl --fail --silent --show-error --location \
  --header "User-Agent: ${USER_AGENT}" \
  "https://fill.papermc.io/v3/projects/paper/versions/${PAPER_VERSION}/builds" \
  --output "${BUILD_JSON}"

PAPER_URL="$(python3 - "${BUILD_JSON}" "${PAPER_BUILD}" <<'PY'
import json
import sys

path, build_id = sys.argv[1], int(sys.argv[2])
with open(path, encoding="utf-8") as handle:
    builds = json.load(handle)

build = next((entry for entry in builds if entry.get("id") == build_id), None)
if build is None:
    raise SystemExit(f"Pinned Paper build {build_id} not found")
if build.get("channel") != "STABLE":
    raise SystemExit(f"Pinned Paper build {build_id} is not STABLE")

download = build.get("downloads", {}).get("server:default")
if not download or not download.get("url"):
    raise SystemExit(f"Pinned Paper build {build_id} has no server:default download")
print(download["url"])
PY
)"

curl --fail --silent --show-error --location \
  --header "User-Agent: ${USER_AGENT}" \
  "${PAPER_URL}" \
  --output "${SERVER_DIR}/paper.jar"

cp "${PLUGIN_JAR}" "${SERVER_DIR}/plugins/EggEmAllReloaded.jar"

if [[ -n "${EXTRA_PLUGIN_URL}" ]]; then
  curl --fail --silent --show-error --location \
    --header "User-Agent: ${USER_AGENT}" \
    "${EXTRA_PLUGIN_URL}" \
    --output "${SERVER_DIR}/plugins/${EXTRA_PLUGIN_NAME}"
fi

SERVER_PORT="$(python3 - <<'PY'
import socket
with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as sock:
    sock.bind(("127.0.0.1", 0))
    print(sock.getsockname()[1])
PY
)"

printf 'eula=true\n' > "${SERVER_DIR}/eula.txt"
printf 'server-ip=127.0.0.1\nserver-port=%s\nonline-mode=false\n' "${SERVER_PORT}" > "${SERVER_DIR}/server.properties"
mkfifo "${SERVER_DIR}/console.in"

pushd "${SERVER_DIR}" >/dev/null
exec 3<>console.in
java -Xms512M -Xmx1024M -jar paper.jar --nogui <console.in >server-console.log 2>&1 &
SERVER_PID=$!
popd >/dev/null

cleanup() {
  if kill -0 "${SERVER_PID}" 2>/dev/null; then
    printf 'stop\n' >&3 || true
    for _ in $(seq 1 30); do
      kill -0 "${SERVER_PID}" 2>/dev/null || return 0
      sleep 1
    done
    kill "${SERVER_PID}" 2>/dev/null || true
  fi
}
trap cleanup EXIT

READY=0
for _ in $(seq 1 180); do
  if ! kill -0 "${SERVER_PID}" 2>/dev/null; then
    echo "Paper exited before EggEmAll Reloaded reached ready state" >&2
    cat "${SERVER_DIR}/server-console.log" >&2 || true
    exit 1
  fi

  if [[ -f "${LOG_FILE}" ]] \
    && grep -Fq 'EGGEMALL RELOADED' "${LOG_FILE}" \
    && grep -Eq 'Done \([0-9.]+s\)! For help, type "help"' "${LOG_FILE}"; then
    READY=1
    break
  fi
  sleep 1
done

if [[ "${READY}" -ne 1 ]]; then
  echo "Timed out waiting for Paper ${PAPER_VERSION} build ${PAPER_BUILD} and EggEmAll Reloaded" >&2
  cat "${SERVER_DIR}/server-console.log" >&2 || true
  exit 1
fi

if grep -Eiq '(Could not load.*EggEmAllReloaded|Error occurred while enabling EggEmAllReloaded|Exception.*EggEmAllReloaded|Could not pass event.*EggEmAllReloaded|NoClassDefFoundError.*EggEmAllReloaded)' "${LOG_FILE}"; then
  echo "EggEmAll Reloaded startup error detected" >&2
  cat "${LOG_FILE}" >&2
  exit 1
fi

if [[ -n "${EXPECT_LOG_PATTERN}" ]] && ! grep -Fq "${EXPECT_LOG_PATTERN}" "${LOG_FILE}"; then
  echo "Expected runtime log marker not found: ${EXPECT_LOG_PATTERN}" >&2
  cat "${LOG_FILE}" >&2
  exit 1
fi

printf 'stop\n' >&3
for _ in $(seq 1 60); do
  if ! kill -0 "${SERVER_PID}" 2>/dev/null; then
    wait "${SERVER_PID}"
    trap - EXIT
    break
  fi
  sleep 1
done

if kill -0 "${SERVER_PID}" 2>/dev/null; then
  echo "Paper did not stop within 60 seconds" >&2
  exit 1
fi

if grep -Eiq '(Error occurred while disabling EggEmAllReloaded|Exception.*EggEmAllReloaded)' "${LOG_FILE}"; then
  echo "EggEmAll Reloaded shutdown error detected" >&2
  cat "${LOG_FILE}" >&2
  exit 1
fi

echo "Paper ${PAPER_VERSION} build ${PAPER_BUILD} startup smoke passed for EggEmAll Reloaded on port ${SERVER_PORT}."
