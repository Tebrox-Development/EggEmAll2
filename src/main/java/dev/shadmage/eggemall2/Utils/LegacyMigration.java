package dev.shadmage.eggemall2.Utils;

import dev.shadmage.eggemall2.EggEmAllPlugin;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public final class LegacyMigration {
	private static final String LEGACY_PLUGIN_DIRECTORY = "EggEmAll2";
	private static final String SETTINGS_FILE = "settings.yml";
	private static final String MIGRATION_MARKER = ".migrated-from-eggemall2";
	private static final String LEGACY_DEFAULT_PREFIX = "&8[&aEggEmAll&8]";
	private static final String RELOADED_DEFAULT_PREFIX = "&8[&aEggEmAll Reloaded&8]";
	private static final DateTimeFormatter BACKUP_TIMESTAMP = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")
			.withZone(ZoneOffset.UTC);

	private LegacyMigration() {
	}

	public static boolean hasLegacySettings(EggEmAllPlugin plugin) {
		return Files.isRegularFile(getLegacySettings(plugin));
	}

	public static boolean hasMigrationMarker(EggEmAllPlugin plugin) {
		return Files.isRegularFile(getMigrationMarker(plugin));
	}

	public static Path getLegacySettings(EggEmAllPlugin plugin) {
		Path pluginsDirectory = plugin.getDataFolder().toPath().getParent();
		if (pluginsDirectory == null) {
			pluginsDirectory = Path.of("plugins");
		}
		return pluginsDirectory.resolve(LEGACY_PLUGIN_DIRECTORY).resolve(SETTINGS_FILE);
	}

	public static MigrationResult migrate(EggEmAllPlugin plugin) throws IOException {
		Path source = getLegacySettings(plugin);
		if (!Files.isRegularFile(source)) {
			throw new IOException("Legacy EggEmAll2 settings.yml was not found at " + source);
		}

		if (hasMigrationMarker(plugin)) {
			throw new IOException("EggEmAll2 settings have already been migrated to EggEmAll Reloaded.");
		}

		Path dataDirectory = plugin.getDataFolder().toPath();
		Files.createDirectories(dataDirectory);
		Path target = dataDirectory.resolve(SETTINGS_FILE);
		Path marker = getMigrationMarker(plugin);
		Path backup = null;
		boolean targetExisted = Files.isRegularFile(target);

		if (targetExisted) {
			String timestamp = BACKUP_TIMESTAMP.format(Instant.now());
			backup = dataDirectory.resolve("settings.yml.backup-" + timestamp);
			int suffix = 1;
			while (Files.exists(backup)) {
				backup = dataDirectory.resolve("settings.yml.backup-" + timestamp + "-" + suffix++);
			}
			Files.copy(target, backup, StandardCopyOption.COPY_ATTRIBUTES);
		}

		Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
		updateLegacyDefaultBranding(target);

		try {
			plugin.reload();

			String markerContents = "Migrated EggEmAll2 settings from: " + source.toAbsolutePath() + System.lineSeparator()
					+ "Migrated at: " + Instant.now() + System.lineSeparator();
			Files.writeString(marker, markerContents, StandardCharsets.UTF_8);
		} catch (Throwable migrationFailure) {
			try {
				Files.deleteIfExists(marker);
			} catch (IOException markerCleanupFailure) {
				migrationFailure.addSuppressed(markerCleanupFailure);
			}
			rollback(plugin, target, backup, targetExisted, migrationFailure);
			throw new IOException("The migration could not be completed. The previous Reloaded settings were restored.", migrationFailure);
		}

		return new MigrationResult(source, target, backup);
	}

	private static void updateLegacyDefaultBranding(Path settingsFile) throws IOException {
		String settings = Files.readString(settingsFile, StandardCharsets.UTF_8);
		String updated = replaceDefaultPrefix(settings, "LogPrefix");
		updated = replaceDefaultPrefix(updated, "ChatPrefix");

		if (!settings.equals(updated)) {
			Files.writeString(settingsFile, updated, StandardCharsets.UTF_8);
		}
	}

	private static String replaceDefaultPrefix(String settings, String key) {
		return settings
				.replace(key + ": \"" + LEGACY_DEFAULT_PREFIX + "\"", key + ": \"" + RELOADED_DEFAULT_PREFIX + "\"")
				.replace(key + ": '" + LEGACY_DEFAULT_PREFIX + "'", key + ": '" + RELOADED_DEFAULT_PREFIX + "'");
	}

	private static void rollback(EggEmAllPlugin plugin, Path target, Path backup, boolean targetExisted, Throwable migrationFailure) {
		try {
			if (targetExisted && backup != null && Files.isRegularFile(backup)) {
				Files.copy(backup, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
			} else {
				Files.deleteIfExists(target);
			}
			plugin.reload();
		} catch (Throwable rollbackFailure) {
			migrationFailure.addSuppressed(rollbackFailure);
		}
	}

	private static Path getMigrationMarker(EggEmAllPlugin plugin) {
		return plugin.getDataFolder().toPath().resolve(MIGRATION_MARKER);
	}

	public record MigrationResult(Path source, Path target, Path backup) {
	}
}
