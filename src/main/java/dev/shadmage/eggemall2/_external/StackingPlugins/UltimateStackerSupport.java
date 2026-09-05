package dev.shadmage.eggemall2._external.StackingPlugins;

import dev.shadmage.eggemall2.EggEmAllPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Compatibility bridge for both the legacy Songoda and current Craftaro
 * UltimateStacker API packages. UltimateStacker is optional, so keeping this
 * integration reflective prevents either API generation from becoming a hard
 * runtime or build dependency of EggEmAll2.
 */
public class UltimateStackerSupport implements StackingPluginAPI {
	private static final String[] API_CLASS_NAMES = {
			"com.craftaro.ultimatestacker.api.UltimateStackerApi",
			"com.songoda.ultimatestacker.api.UltimateStackerApi"
	};

	private Class<?> apiClass;
	private boolean compatibilityFailureLogged;

	@Override
	public boolean isStackingPluginLoaded() {
		Plugin plugin = Bukkit.getPluginManager().getPlugin("UltimateStacker");
		if (plugin == null || !plugin.isEnabled()) {
			return false;
		}

		ClassLoader pluginClassLoader = plugin.getClass().getClassLoader();
		for (String apiClassName : API_CLASS_NAMES) {
			try {
				apiClass = Class.forName(apiClassName, false, pluginClassLoader);
				return true;
			} catch (ClassNotFoundException ignored) {
				// Try the next supported package generation.
			}
		}

		logCompatibilityFailure("no supported API class was found", null);
		return false;
	}

	@Override
	public boolean unstackEntity(Entity entity) {
		try {
			StackAccess stackAccess = getStack(entity);
			if (stackAccess == null) {
				return false;
			}

			Method getAmount = stackAccess.stackType().getMethod("getAmount");
			int amount = ((Number) getAmount.invoke(stackAccess.stack())).intValue();
			if (amount <= 1) {
				return false;
			}

			Method setAmount = stackAccess.stackType().getMethod("setAmount", int.class);
			setAmount.invoke(stackAccess.stack(), amount - 1);
			return true;
		} catch (ReflectiveOperationException | RuntimeException exception) {
			logCompatibilityFailure("the installed API could not be invoked", exception);
			return false;
		}
	}

	@Override
	public boolean isStackedEntity(Entity entity) {
		try {
			return getStack(entity) != null;
		} catch (ReflectiveOperationException | RuntimeException exception) {
			logCompatibilityFailure("the installed API could not be invoked", exception);
			return false;
		}
	}

	private StackAccess getStack(Entity entity) throws ReflectiveOperationException {
		if (apiClass == null && !isStackingPluginLoaded()) {
			return null;
		}

		Method getManager = apiClass.getMethod("getEntityStackManager");
		Object manager = getManager.invoke(null);
		if (manager == null) {
			return null;
		}

		Class<?> managerType = getManager.getReturnType();
		Method getStackedEntity = managerType.getMethod("getStackedEntity", UUID.class);
		Object stack = getStackedEntity.invoke(manager, entity.getUniqueId());
		if (stack == null) {
			return null;
		}

		return new StackAccess(stack, getStackedEntity.getReturnType());
	}

	private void logCompatibilityFailure(String reason, Exception exception) {
		if (compatibilityFailureLogged) {
			return;
		}

		compatibilityFailureLogged = true;
		String detail = exception == null ? "" : " (" + exception.getClass().getSimpleName() + ")";
		EggEmAllPlugin.getInstance().getLogger().warning(
				"UltimateStacker detected, but its integration has been disabled because " + reason + detail + ".");
	}

	private record StackAccess(Object stack, Class<?> stackType) {
	}
}
