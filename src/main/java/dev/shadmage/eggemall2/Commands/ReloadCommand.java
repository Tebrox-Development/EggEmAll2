package dev.shadmage.eggemall2.Commands;

import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;
import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.fo.settings.SimpleLocalization;

public final class ReloadCommand extends SimpleSubCommand {
	ReloadCommand(final SimpleCommandGroup parent) {
		super(parent, "reload");
		setDescription("Reload the EggEmAll Plugin");
	}

	@Override
	protected void onCommand() {
		try {
			SimplePlugin.getInstance().reload();
			tell(SimpleLocalization.Commands.RELOAD_SUCCESS);
		} catch (Throwable t) {
			t.printStackTrace();
			tell(SimpleLocalization.Commands.RELOAD_FAIL.replace("{error}", t.toString()));
		}
	}
}
