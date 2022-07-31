package dev.shadmage.eggemall2.Model;

import org.bukkit.entity.Entity;

public final class ProcessPlaceholderMessages {

	public static String ReplacePlaceholders(String msg, Entity entity) {
		if (entity != null) {
			msg = msg.replace("{entity}", entity.getName());
		}


		return msg;
	}

}
