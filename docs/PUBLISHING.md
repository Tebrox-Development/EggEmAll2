# Publishing EggEmAll Reloaded

This checklist covers the remaining manual publication steps for the maintained fork.

## Release blockers

Do not publish an EggEmAll Reloaded build to SpigotMC or Modrinth while it still uses the original project's Spigot publication identifier.

Before the first public release:

1. Create the new **SpigotMC resource** for EggEmAll Reloaded.
2. Replace the original Spigot resource ID `122056` used by `SpigotUpdateChecker` in `src/main/java/dev/shadmage/eggemall2/EggEmAllPlugin.java` with the new EggEmAll Reloaded resource ID.
3. Run the full Maven verification and runtime smoke tests again after the Spigot resource ID is changed.
4. Verify on a test server that bStats reports to the registered EggEmAll Reloaded project ID `33887`.

## SpigotMC

Publish the project as **EggEmAll Reloaded** and state clearly that it is a maintained fork of EggEmAll2 by shadmage / Dirty-Dog-Gaming, published with the original author's permission.

Link back to:

- the original EggEmAll2 project/resource;
- the original upstream GitHub repository;
- the maintained Tebrox-Development repository.

After the resource is live, add its final URL to the README and use its numeric resource ID for the update checker.

## Modrinth

Publish the project as **EggEmAll Reloaded** and mark/disclose it as derivative content/a maintained fork where the Modrinth publishing form requires it. Credit shadmage / Dirty-Dog-Gaming and link the original project/repository.

After the project is live, add the final Modrinth URL to the README.

## bStats

EggEmAll Reloaded is registered as its own bStats project with ID **`33887`**.

The plugin uses the official `org.bstats:bstats-bukkit:3.2.1` dependency. bStats is included in the shaded plugin JAR and relocated under the plugin's private library namespace to avoid class conflicts with other plugins.

Before the public release, start a test server with metrics enabled and verify that the server appears on the EggEmAll Reloaded bStats project rather than the original EggEmAll2 project.

## GitHub repository name

The repository has been renamed to **`Tebrox-Development/EggEmAll-Reloaded`**. Check badges, documentation and any hard-coded repository links for stale `EggEmAll2` URLs as part of the release preparation.

## Upgrade check

Because the Bukkit/Paper plugin name changes from `EggEmAll2` to `EggEmAllReloaded`, test an upgrade using an existing EggEmAll2 installation before the first public release. Back up the existing `plugins/EggEmAll2` data directory and verify how the configuration is migrated or copied to the new plugin data directory.

The following compatibility identifiers intentionally stay unchanged unless a separate breaking release explicitly decides otherwise:

- `/eggemall` command namespace
- `eggemall.*` permission namespace
- `%eggemall_...%` PlaceholderAPI namespace
- legacy entity-data PDC keys needed to restore existing captured eggs

## Final release check

Before uploading the first public build:

- new Spigot resource ID configured;
- bStats project ID `33887` configured and reporting verified;
- SpigotMC attribution text present;
- Modrinth attribution/derivative disclosure present;
- README links updated;
- clean-install smoke test passed;
- EggEmAll2 upgrade/migration test passed;
- GitHub stable release artifact uses the EggEmAll Reloaded name.
