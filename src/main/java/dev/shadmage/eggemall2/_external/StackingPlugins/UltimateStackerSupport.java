package dev.shadmage.eggemall2._external.StackingPlugins;


import com.songoda.ultimatestacker.api.UltimateStackerApi;
import org.bukkit.entity.Entity;
import org.mineacademy.fo.Common;

import java.util.UUID;

public class UltimateStackerSupport implements StackingPluginAPI {


	@Override
	public boolean isStackingPluginLoaded() {
		if (Common.doesPluginExist("UltimateStacker")) {
			return true;
		} else
			return false;
	}

	@Override
	public boolean unstackEntity(Entity entity) {
		UUID entityUUID = entity.getUniqueId();
		int qty = UltimateStackerApi.getEntityStackManager().getStackedEntity(entityUUID).getAmount();
		if (qty > 1) {
			UltimateStackerApi.getEntityStackManager().getStackedEntity(entityUUID).setAmount(qty - 1);
			return true;
		}
		return false;
	}

	@Override
	public boolean isStackedEntity(Entity entity) {
		return UltimateStackerApi.getEntityStackManager().getStackedEntity(entity.getUniqueId()) != null;
	}
}
