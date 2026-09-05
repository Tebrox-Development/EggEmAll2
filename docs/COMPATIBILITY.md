# Compatibility

EggEmAll2 is maintained with current Paper support as the primary target while avoiding unnecessary runtime requirements.

## Java

The project is built with **JDK 25** because Paper 26.2 and its current API are developed for the current Java generation. The plugin itself is compiled with `--release 17`, so its own classes remain Java-17 bytecode where the dependency/API surface permits this.

This distinction is intentional: the build environment can move forward without automatically excluding servers that still run a Java version capable of loading Java-17 plugins.

## Minecraft / Paper

| Runtime | Status |
| --- | --- |
| Paper 26.2 build 121 | Primary target; compile/API verification is green, automated startup smoke enabled |
| Paper 26.1 | Compatibility intended; runtime validation pending |
| Paper 1.21.7 | Backward-compatibility compile/API verification is green; runtime validation still pending |
| Other Paper / Spigot 1.21.x | Best-effort compatibility; not claimed until explicitly tested |
| Older than 1.21 | Not a maintenance target for this fork unless compatibility comes for free |

Compile/API verification means the same source builds against that Paper API. Runtime validation is tracked separately and is only claimed after the plugin actually starts successfully on that server line. Capture/restore gameplay is still a separate functional test and is not implied by a startup smoke.

## Entity data compatibility

New captured eggs use Paper's native `SpawnEggMeta` / `EntitySnapshot` support when `NBT.MaintainEntityDataValues` is enabled. This avoids treating `EntitySnapshot#getAsString()` as a long-term persistence format.

Eggs produced by upstream EggEmAll2 versions used the plugin PDC key `eggemall_entity_data` containing the snapshot string. The legacy reader remains in place so those existing eggs can still be restored where the current server can parse their stored snapshot data.

## Compatibility rules

1. Keep existing config keys and defaults unless a migration is necessary.
2. Keep existing commands and permission nodes unless a security or correctness issue requires a change.
3. Prefer version-adapter or reflective fallbacks for isolated API differences instead of raising the whole plugin's minimum version.
4. Do not emulate removed server behavior when doing so would be fragile or unsafe; document the minimum version instead.
5. Keep optional integrations optional. A missing or incompatible stacker plugin must not prevent EggEmAll2 from starting.

## Current dependency baseline

- Paper API: `26.2.build.121-stable` (primary compile target)
- Foundation: `6.10.1`
- RoseStacker API: `1.5.41` (provided/optional runtime integration)
- UltimateStacker: no hard build dependency; legacy `com.songoda` and current `com.craftaro` API packages are detected reflectively
