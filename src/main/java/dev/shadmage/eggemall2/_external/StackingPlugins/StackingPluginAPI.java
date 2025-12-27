package dev.shadmage.eggemall2._external.StackingPlugins;

import org.bukkit.entity.Entity;

public interface StackingPluginAPI {

	boolean isStackingPluginLoaded();

	boolean unstackEntity(Entity entity);

	boolean isStackedEntity(Entity entity);
}
