package dev.shadmage.eggemall2.Settings;

import org.mineacademy.fo.Common;
import org.mineacademy.fo.settings.SimpleSettings;

import java.util.List;

public final class Settings extends SimpleSettings {

	private static void init() {
		setPathPrefix("");
		Common.setLogPrefix(getString("LogPrefix"));
		Common.setTellPrefix(getString("ChatPrefix"));
	}

	@Override
	protected int getConfigVersion() {
		setPathPrefix("");
		return getInteger("Version");
	}

	/*
	Example settings category


	public static class EggCatcher {
		public static String EGGCATCHER_GENERAL_CHAT_PREFIX;
		public static Boolean EGGCATCHER_GENERAL_UsePermissions;
		public static List<String> EGGCATCHER_TRAILS_DisabledWorlds;

		private static void init() {
			setPathPrefix("ExampleSettings");
			EGGCATCHER_GENERAL_CHAT_PREFIX = getString("ChatPrefix");
			EGGCATCHER_GENERAL_UsePermissions = getBoolean("UsePermissions");
			EGGCATCHER_TRAILS_DisabledWorlds = getList("Disabled-Worlds", String.class);
		}
	}
	*/
	public static class BlacklistWorlds {
		public static Boolean AS_WHITELIST;
		public static List<String> WORLDS;

		private static void init() {
			setPathPrefix("BlacklistWorlds");
			AS_WHITELIST = getBoolean("AsWhitelist");
			WORLDS = getList("Worlds", String.class);
		}
	}

	public static class Particles {
		public static Boolean EGG_TRAILS;
		public static Boolean PLAYER_THROW_ONLY;
		public static Boolean EXPLOSION_ON_SUCCESS;
		public static Boolean SMOKE_ON_ESCAPE;

		private static void init() {
			setPathPrefix("Particles");
			EGG_TRAILS = getBoolean("EggTrails");
			PLAYER_THROW_ONLY = getBoolean("PlayerThrowOnly");
			EXPLOSION_ON_SUCCESS = getBoolean("ExplosionOnSuccess");
			SMOKE_ON_ESCAPE = getBoolean("SmokeOnEscape");
		}
	}

	public static class CatchChance {
		public static Integer CHANCE_PERCENTAGE;
		public static Boolean SPAWN_CHICKEN_ON_FAIL;
		public static Boolean REMOVE_ENTITY_ON_FAIL_CHANCE;
		public static Boolean ADD_LORE_TO_EGG;

		private static void init() {
			setPathPrefix("CatchChance");
			CHANCE_PERCENTAGE = getInteger("ChancePercentage", 100);
			SPAWN_CHICKEN_ON_FAIL = getBoolean("SpawnChickenOnFail");
			REMOVE_ENTITY_ON_FAIL_CHANCE = getBoolean("RemoveEntityOnFail");
			ADD_LORE_TO_EGG = getBoolean("AddLoreToSpawnEgg");
		}
	}

	public static class Restrictions {
		public static Boolean ONLY_ALLOW_PLAYER_THROWN_EGGS;
		public static Boolean PREVENT_CATCHING_BABIES;
		public static Boolean PREVENT_CATCHING_TAMED;
		public static Boolean PREVENT_CATCHING_SHEARED_SHEEP;
		public static Boolean REQUIRE_PERMISSIONS;

		private static void init() {
			setPathPrefix("Restrictions");
			ONLY_ALLOW_PLAYER_THROWN_EGGS = getBoolean("OnlyAllowPlayerThrownEggs");
			PREVENT_CATCHING_BABIES = getBoolean("PreventCatchingBabyEntities");
			PREVENT_CATCHING_TAMED = getBoolean("PreventCatchingTamedEntities");
			PREVENT_CATCHING_SHEARED_SHEEP = getBoolean("PreventCatchingShearedSheep");
			REQUIRE_PERMISSIONS = getBoolean("RequirePermissions");
		}
	}

	public static class EntityInventories {
		public static Boolean ERASE_ENTITY_INVENTORY;


		private static void init() {
			setPathPrefix("EntityInventories");
			ERASE_ENTITY_INVENTORY = getBoolean("DeleteVillagerInventoryOnCatch");
		}
	}

	public static class Messages {
		public static String NO_PERMISSION;
		public static String CATCH_SUCCESS;
		public static String CATCH_FAILED_CHANCE;
		public static String NO_BABIES;
		public static String NO_TAMED;
		public static String NO_SHEARED_SHEEP;

		private static void init() {
			setPathPrefix("Messages");
			NO_PERMISSION = getString("NoPermissions");
			CATCH_SUCCESS = getString("CatchSuccess");
			CATCH_FAILED_CHANCE = getString("CatchFailedChance");
			NO_BABIES = getString("NoBabies");
			NO_TAMED = getString("NoTamed");
			NO_SHEARED_SHEEP = getString("NoShearedSheep");
		}
	}

}
