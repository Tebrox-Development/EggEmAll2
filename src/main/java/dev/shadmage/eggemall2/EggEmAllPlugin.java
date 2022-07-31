package dev.shadmage.eggemall2;

import dev.shadmage.eggemall2.Model.SpawnEggs;
import dev.shadmage.eggemall2.Settings.Settings;
import org.bukkit.entity.Egg;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.plugin.SimplePlugin;

import java.util.ArrayList;
import java.util.List;

public class EggEmAllPlugin extends SimplePlugin {

	public static List<Egg> thrownEggs = new ArrayList<>();
	public static SpawnEggs catchableMobs = new SpawnEggs();

	public static EggEmAllPlugin getInstance() {
		return (EggEmAllPlugin) SimplePlugin.getInstance();
	}

	@Override
	protected void onPluginStart() {
		Common.setLogPrefix(Settings.LOG_PREFIX);
		Common.setTellPrefix(Settings.CHAT_PREFIX);
	}

}
