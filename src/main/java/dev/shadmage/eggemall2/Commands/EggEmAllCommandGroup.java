package dev.shadmage.eggemall2.Commands;

import lombok.NoArgsConstructor;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommandGroup;

@AutoRegister
@NoArgsConstructor
public final class EggEmAllCommandGroup extends SimpleCommandGroup {
	@Override
	protected void registerSubcommands() {
		registerSubcommand(new GUICommand(this));
	}

	@Override
	protected String getCredits() {
		return "&bVisit &dhttps://dirtydoggaming.co.za";
	}
}
