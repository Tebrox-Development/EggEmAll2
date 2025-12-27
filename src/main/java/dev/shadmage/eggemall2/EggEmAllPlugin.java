package dev.shadmage.eggemall2;

import dev.shadmage.eggemall2.Model.SpawnEggs;
import dev.shadmage.eggemall2.Settings.Settings;
import dev.shadmage.eggemall2._external.Metrics;
import dev.shadmage.eggemall2._external.SpigotUpdateChecker;
import dev.shadmage.eggemall2._external.StackingPlugins.RoseStackerSupport;
import dev.shadmage.eggemall2._external.StackingPlugins.StackingPluginAPI;
import dev.shadmage.eggemall2._external.StackingPlugins.UltimateStackerSupport;
import lombok.Getter;
import org.bukkit.entity.Egg;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.plugin.SimplePlugin;

import java.util.ArrayList;
import java.util.List;

public class EggEmAllPlugin extends SimplePlugin {
	public static List<Egg> thrownEggs = new ArrayList<>();
	public static SpawnEggs catchableMobs;

	@Getter
	private StackingPluginAPI stackingPlugin;

	public static EggEmAllPlugin getInstance() {
		return (EggEmAllPlugin) SimplePlugin.getInstance();
	}

	@Override
	protected void onPluginStart() {
		Metrics metrics = new Metrics(this, 15978);
		SpigotUpdateChecker spigotUpdateChecker = new SpigotUpdateChecker(this, 122056);

		//set logging & chat prefixes
		Common.setLogPrefix(Settings.LOG_PREFIX);
		Common.setTellPrefix(Settings.CHAT_PREFIX);


		// Check for a supported stacking plugin
		RoseStackerSupport rsSupport = new RoseStackerSupport();
		if (rsSupport.isStackingPluginLoaded()) {
			stackingPlugin = rsSupport;
			Common.log("&6RoseStacker detected! &fEgg em All will work with RoseStacker!");
		} else if (Common.doesPluginExist("UltimateStacker")) {
			stackingPlugin = new UltimateStackerSupport();
			Common.log("&6UltimateStacker detected! &fEgg em All will work with UltimateStacker!");
		} else
			stackingPlugin = null;
	}

	@Override
	protected void onReloadablesStart() {
		catchableMobs = new SpawnEggs();
		if (Settings.General.STARTUP_CONSOLE_STATS) {
			//print plugin loaded summary to console
			Common.log("&6===================== &bEGG EM ALL &6=====================");
			Common.log("&aCatchable Entities: &f" + catchableMobs.countCatchableEntities());
			Common.log("&cBlacklisted Entities: &f" + Settings.Restrictions.BLACKLISTED_ENTITIES.size());
			Common.log((Settings.BlacklistWorlds.AS_WHITELIST ? "&aWhitelisted" : "&cBlacklisted") + " Worlds: &f" + Settings.BlacklistWorlds.WORLDS.size());
			Common.log("&6======================================================");
		}
	}

}
