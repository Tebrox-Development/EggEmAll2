package dev.shadmage.eggemall2.Model;

import org.bukkit.entity.AbstractVillager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.EntityUtil;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class SpawnEggs {

	private final List<EntityType> CatchableEntities;
	private final List<EntityType> blockedMobs = Arrays.asList(
			EntityType.ELDER_GUARDIAN,
			EntityType.ENDER_DRAGON,
			EntityType.WITHER,
			EntityType.WARDEN);

	public SpawnEggs() {
		CatchableEntities = Arrays.stream(EntityType.values()).filter(
				entityType -> entityType.isSpawnable() && !(blockedMobs.contains(entityType)) && entityType.isAlive() && (entityType == EntityType.SHEEP || CompMaterial.makeMonsterEgg(entityType) != CompMaterial.SHEEP_SPAWN_EGG)
		).collect(Collectors.toList());
	}

	public boolean isCatchable(Entity entity) {
		return isCatchable(entity.getType());
	}

	public boolean isCatchable(EntityType entityType) {
		return CatchableEntities.contains(entityType);
	}

	public ItemStack getSpawnEgg(Entity entity) {
		EntityType entityType = entity.getType();
		if (CatchableEntities.contains(entityType) && !blockedMobs.contains(entityType)) {
			return ItemCreator.of(CompMaterial.makeMonsterEgg(entityType)).make();
		} else {
			return ItemCreator.of(CompMaterial.EGG).make();
		}
	}

	public String getCatchPermission(Entity entity) {
		String perm = "eggemall.";
		if (entity instanceof AbstractVillager) {
			perm += "villagers";
		} else if (EntityUtil.isAggressive(entity)) {
			perm += "aggressive";
		} else if (EntityUtil.isCreature(entity)) {
			perm += "passive";
		} else {
			perm += "unknown";
		}
		return perm;
	}

	/*
	private void getEntityClassType(Entity entity) {
		if (entity instanceof LivingEntity && !(entity instanceof Player)) {
			boolean isAnimal = entity instanceof Animals || entity instanceof WaterMob || entity instanceof Golem;
			boolean isMonster = entity instanceof Monster || entity instanceof Ghast || entity instanceof Slime;
			boolean isNpc = entity instanceof NPC;

		}

		// return the type... i.e.
		// Animal
		// Monster
		// Npc

	}
	*/
}