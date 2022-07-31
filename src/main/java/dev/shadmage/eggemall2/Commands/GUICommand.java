package dev.shadmage.eggemall2.Commands;

import dev.shadmage.eggemall2.GUI.EggEmAllGUI;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;

public final class GUICommand extends SimpleSubCommand {

	protected GUICommand(final SimpleCommandGroup parent) {
		super(parent, "menu");
		setDescription("Show details about this plugin");
	}

	@Override
	protected void onCommand() {
		checkConsole();
		new EggEmAllGUI().displayTo(getPlayer());
	}
}
