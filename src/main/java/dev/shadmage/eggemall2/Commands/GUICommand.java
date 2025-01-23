package dev.shadmage.eggemall2.Commands;

import dev.shadmage.eggemall2.GUI.EggEmAllGUI;
import dev.shadmage.eggemall2.Settings.PermissionData;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;

public final class GUICommand extends SimpleSubCommand {
	public GUICommand(final SimpleCommandGroup parent) {
		super(parent, "menu");
		setName("menu");
		setDescription("Show details about this plugin");
		setPermission(PermissionData.GUI_COMMAND);
	}

	@Override
	protected void onCommand() {
		checkConsole();
		new EggEmAllGUI().displayTo(getPlayer());
	}
}