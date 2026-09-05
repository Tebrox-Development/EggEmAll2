package dev.shadmage.eggemall2._external.PlaceholderAPI;

import dev.shadmage.eggemall2.EggEmAllPlugin;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public final class PlaceholderAPISupport {

	private PlaceholderAPISupport() {
	}

	public static boolean registerExpansion(EggEmAllPlugin plugin) {
		return new EggEmAllPlaceholderExpansion(plugin).register();
	}

	public static String setPlaceholders(Player player, String text) {
		return PlaceholderAPI.setPlaceholders(player, text);
	}
}
