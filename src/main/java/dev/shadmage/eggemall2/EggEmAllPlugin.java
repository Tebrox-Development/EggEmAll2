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
	public static SpawnEggs catchableMobs;

	public static EggEmAllPlugin getInstance() {
		return (EggEmAllPlugin) SimplePlugin.getInstance();
	}

	@Override
	protected void onPluginStart() {
		Common.setLogPrefix(Settings.LOG_PREFIX);
		Common.setTellPrefix(Settings.CHAT_PREFIX);
		catchableMobs = new SpawnEggs();

		Common.log("&6===================== &bEGG EM ALL &6=====================");
		Common.log("&aCatchable Entities: &f" + catchableMobs.countCatchableEntities());
		Common.log("&cBlacklisted Entities: &f" + Settings.Restrictions.BLACKLISTED_ENTITIES.size());
		Common.log((Settings.BlacklistWorlds.AS_WHITELIST ? "&aWhitelisted" : "&cBlacklisted") + " Worlds: &f" + Settings.BlacklistWorlds.WORLDS.size());
		Common.log("&6======================================================");
	}

}
