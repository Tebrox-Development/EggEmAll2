package dev.shadmage.eggemall2.Events;

import dev.shadmage.eggemall2.CustomEvents.EntityCaptureEvent;
import dev.shadmage.eggemall2.CustomEvents.EntityEscapeCaptureEvent;
import dev.shadmage.eggemall2.EggEmAllPlugin;
import dev.shadmage.eggemall2.Settings.Settings;
import dev.shadmage.eggemall2.Utils.ProcessPlaceholderMessages;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.RandomUtil;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.remain.CompParticle;

import java.util.Arrays;
import java.util.List;

@AutoRegister
public final class EggListener implements Listener {

	@EventHandler
	public void onPlayerEggThrow(PlayerEggThrowEvent event) {
		if (EggEmAllPlugin.thrownEggs.contains(event.getEgg())) {
			event.setHatching(false);
			EggEmAllPlugin.thrownEggs.remove(event.getEgg());
		}
	}

	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent event) {
		Projectile shot = event.getEntity();
		if (Settings.Particles.PLAYER_THROW_ONLY && !(shot.getShooter() instanceof Player)) {
			return;
		}
		if (shot instanceof Egg) {
			String currentWorldName = shot.getWorld().getName();
			if (Settings.BlacklistWorlds.AS_WHITELIST == Settings.BlacklistWorlds.WORLDS.contains(currentWorldName)) {
				if (Settings.Particles.EGG_TRAILS) {
					new BukkitRunnable() {
						@Override
						public void run() {
							if (!shot.isValid() || shot.isOnGround() || shot.isInWater()) {
								cancel();
								return;
							}
							CompParticle.SPELL_WITCH.spawn(shot.getLocation());
						}
					}.runTaskTimer(EggEmAllPlugin.getInstance(), 0, 1);
				}
			}
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onEntityHitByEgg(EntityDamageEvent event) {
		Common.setTellPrefix(Settings.CHAT_PREFIX);
		Entity targetEntity = event.getEntity();
		if (!(event instanceof EntityDamageByEntityEvent damageEvent))
			return;

		if (!(damageEvent.getDamager() instanceof Egg egg))
			return;

		EntityCaptureEvent entityCaptureEvent = new EntityCaptureEvent(targetEntity, egg);
		EntityEscapeCaptureEvent entityEscapeEvent = new EntityEscapeCaptureEvent(targetEntity, egg);

		if (!Settings.BlacklistWorlds.AS_WHITELIST && Settings.BlacklistWorlds.WORLDS.contains(egg.getWorld().getName())) {
			if (Settings.Messages.BLACKLISTED_WORLD.length() > 0 && egg.getShooter() instanceof Player player)
				Common.tell(player, ProcessPlaceholderMessages.ReplacePlaceholders(Settings.Messages.BLACKLISTED_WORLD, targetEntity));
			return;
		}

		if (!EggEmAllPlugin.catchableMobs.isCatchable(targetEntity)) {
			if (Settings.Messages.NOT_CATCHABLE.length() > 0 && egg.getShooter() instanceof Player player)
				Common.tell(player, ProcessPlaceholderMessages.ReplacePlaceholders(Settings.Messages.NOT_CATCHABLE, targetEntity));
			return;
		}

		if (Settings.CatchChance.SPAWN_CHICKEN_ON_FAIL)
			EggEmAllPlugin.thrownEggs.add(egg);

		if (Settings.Restrictions.PREVENT_CATCHING_BABIES)
			if (targetEntity instanceof Ageable)
				if (!((Ageable) targetEntity).isAdult()) {
					if (Settings.Messages.NO_BABIES.length() > 0 && egg.getShooter() instanceof Player player)
						Common.tell(player, Settings.Messages.NO_BABIES);
					return;
				}

		if (Settings.Restrictions.PREVENT_CATCHING_TAMED)
			if (targetEntity instanceof Tameable)
				if (((Tameable) targetEntity).isTamed()) {
					if (Settings.Messages.NO_TAMED.length() > 0 && egg.getShooter() instanceof Player player)
						Common.tell(player, Settings.Messages.NO_TAMED);
					return;
				}

		if (Settings.Restrictions.PREVENT_CATCHING_SHEARED_SHEEP)
			if (targetEntity instanceof Sheep)
				if (((Sheep) targetEntity).isSheared()) {
					if (Settings.Messages.NO_SHEARED_SHEEP.length() > 0 && egg.getShooter() instanceof Player player)
						Common.tell(player, Settings.Messages.NO_SHEARED_SHEEP);
					return;
				}

		if (Settings.Restrictions.PREVENT_CATCHING_NAMED_ENTITIES)
			if (targetEntity.getCustomName() != null) {
				if (Settings.Messages.NO_NAMED_ENTITIES.length() > 0 && egg.getShooter() instanceof Player player)
					Common.tell(player, Settings.Messages.NO_NAMED_ENTITIES);
				return;
			}

		EggEmAllPlugin.getInstance().getServer().getPluginManager().callEvent(entityCaptureEvent);
		if (entityCaptureEvent.isCancelled())
			return;

		if (!(egg.getShooter() instanceof Player player)) {
			if (Settings.Restrictions.ONLY_ALLOW_PLAYER_THROWN_EGGS)
				return;
			if (!RandomUtil.chance(Settings.CatchChance.CHANCE_PERCENTAGE)) {
				EggEmAllPlugin.getInstance().getServer().getPluginManager().callEvent(entityEscapeEvent);
				return;
			}
		} else {
			if (Settings.Restrictions.REQUIRE_PERMISSIONS)
				if (!player.hasPermission(EggEmAllPlugin.catchableMobs.getCatchPermission(targetEntity))) {
					if (Settings.Messages.NO_PERMISSION.length() > 0)
						Common.tell(player, ProcessPlaceholderMessages.ReplacePlaceholders(Settings.Messages.NO_PERMISSION, targetEntity));
					return;
				}

			if (RandomUtil.chance(Settings.CatchChance.CHANCE_PERCENTAGE)) {
				if (Settings.Messages.CATCH_SUCCESS.length() > 0)
					Common.tell(player, ProcessPlaceholderMessages.ReplacePlaceholders(Settings.Messages.CATCH_SUCCESS, targetEntity));
			} else {
				EggEmAllPlugin.getInstance().getServer().getPluginManager().callEvent(entityEscapeEvent);
				if (Settings.Messages.CATCH_FAILED_CHANCE.length() > 0)
					Common.tell(player, ProcessPlaceholderMessages.ReplacePlaceholders(Settings.Messages.CATCH_FAILED_CHANCE, targetEntity));
				return;
			}
		}

		targetEntity.remove();

		if (Settings.Particles.EXPLOSION_ON_SUCCESS) {
			CompParticle.EXPLOSION_LARGE.spawn(targetEntity.getLocation());
		}

		ItemStack eggStack = EggEmAllPlugin.catchableMobs.getSpawnEgg(targetEntity);

		if (!Settings.EntityInventories.ERASE_ENTITY_INVENTORY && targetEntity instanceof InventoryHolder) {
			ItemStack[] items = ((InventoryHolder) targetEntity).getInventory().getContents();
			for (ItemStack itemStack : items) {
				if (itemStack != null) {
					targetEntity.getWorld().dropItemNaturally(targetEntity.getLocation(), itemStack);
				}
			}
		}

		if (egg.getShooter() instanceof Player player && Settings.CatchChance.ADD_LORE_TO_EGG) {
			String playerName = player.getName();
			ItemMeta meta = eggStack.getItemMeta();
			List<String> newLore = Arrays.asList("", ChatColor.translateAlternateColorCodes('&', "&9Captured by: &e&l" + playerName));
			assert meta != null;
			meta.setLore(newLore);
			eggStack.setItemMeta(meta);
		}

		targetEntity.getWorld().dropItem(targetEntity.getLocation(), eggStack);

		if (!EggEmAllPlugin.thrownEggs.contains(egg)) {
			EggEmAllPlugin.thrownEggs.add(egg);
		}
	}

	@EventHandler
	public void onEntityEscapeCapture(EntityEscapeCaptureEvent event) {
		if (Settings.CatchChance.REMOVE_ENTITY_ON_FAIL_CHANCE) {
			event.getEntity().remove();
			if (Settings.Particles.SMOKE_ON_ESCAPE) {
				CompParticle.SMOKE_LARGE.spawn(event.getEntity().getLocation());
			}
		}
	}
}
