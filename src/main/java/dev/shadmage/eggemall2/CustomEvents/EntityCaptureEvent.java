package dev.shadmage.eggemall2.CustomEvents;

import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;
import org.jetbrains.annotations.NotNull;

public final class EntityCaptureEvent extends EntityEvent implements Cancellable {
	/***
	 * This event is available for other plugins to cancel the capture event if required
	 */
	private static final HandlerList handlers = new HandlerList();
	boolean cancelled = false;
	Egg egg;

	public EntityCaptureEvent(Entity what, Egg egg) {
		super(what);
		this.egg = egg;
	}

	public Egg getEgg() {
		return this.egg;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean isCancelled) {
		this.cancelled = isCancelled;
	}

	@Override
	public @NotNull HandlerList getHandlers() {
		return handlers;
	}
}
