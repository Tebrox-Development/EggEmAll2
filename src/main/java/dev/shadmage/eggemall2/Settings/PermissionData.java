package dev.shadmage.eggemall2.Settings;

import org.mineacademy.fo.command.annotation.Permission;


public class PermissionData {

	@Permission
	public static final String GUI_COMMAND = "eggemall.command.gui";
	@Permission
	public static final String RELOAD_COMMAND = "eggemall.command.reload";
	@Permission
	public static final String CATCH_VILLAGERS = "eggemall.villagers";
	@Permission
	public static final String CATCH_AGGRESSIVE = "eggemall.aggressive";
	@Permission
	public static final String CATCH_PASSIVE = "eggemall.passive";
	@Permission
	public static final String CATCH_UNKNOWN = "eggemall.unknown";
}
