package dev.shadmage.eggemall2.Commands;

import dev.shadmage.eggemall2.EggEmAllPlugin;
import dev.shadmage.eggemall2.Settings.PermissionData;
import dev.shadmage.eggemall2.Utils.LegacyMigration;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;

import java.io.IOException;

public final class MigrateCommand extends SimpleSubCommand {
	public MigrateCommand(final SimpleCommandGroup parent) {
		super(parent, "migrate");
		setDescription("Migrate settings from EggEmAll2");
		setPermission(PermissionData.MIGRATE_COMMAND);
	}

	@Override
	protected void onCommand() {
		EggEmAllPlugin plugin = EggEmAllPlugin.getInstance();

		if (!LegacyMigration.hasLegacySettings(plugin)) {
			tell("&cNo EggEmAll2 settings.yml was found to migrate.");
			return;
		}

		if (LegacyMigration.hasMigrationMarker(plugin)) {
			tell("&eEggEmAll2 settings have already been migrated to EggEmAll Reloaded.");
			return;
		}

		tell("&eMigrating EggEmAll2 settings to EggEmAll Reloaded...");

		try {
			LegacyMigration.MigrationResult result = LegacyMigration.migrate(plugin);
			tell("&aEggEmAll2 settings migrated successfully.");
			if (result.backup() != null) {
				tell("&7Backup created: &f" + result.backup().getFileName());
			}
			tell("&7The original EggEmAll2 files were left untouched.");
		} catch (IOException ex) {
			ex.printStackTrace();
			tell("&cMigration failed: " + ex.getMessage());
		}
	}
}
