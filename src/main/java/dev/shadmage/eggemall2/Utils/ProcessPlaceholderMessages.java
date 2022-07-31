package dev.shadmage.eggemall2.Utils;

import org.bukkit.entity.Entity;

import java.util.Objects;

public final class ProcessPlaceholderMessages {

	public static String ReplacePlaceholders(String msg, Entity entity) {
		if (entity != null) {
			msg = msg.replace("{entity}", entity.getName());
			msg = msg.replace("{world}", Objects.requireNonNull(entity.getLocation().getWorld()).getName().replace("world_", ""));
		}
		return msg;
	}

}
