package dev.shadmage.eggemall2.CustomEvents;

import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;
import org.jetbrains.annotations.NotNull;

public final class EntityEscapeCaptureEvent extends EntityEvent {

	private static final HandlerList handlers = new HandlerList();
	Egg egg;

	public EntityEscapeCaptureEvent(@NotNull Entity what, Egg egg) {
		super(what);
		this.egg = egg;
	}

	public Egg getEgg() {
		return this.egg;
	}

	@Override
	public @NotNull HandlerList getHandlers() {
		return handlers;
	}
}
