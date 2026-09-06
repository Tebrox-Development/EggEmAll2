# Publishing EggEmAll Reloaded

This checklist covers the remaining manual publication steps for the maintained fork.

## Release blockers

The Spigot update checker is intentionally disabled until EggEmAll Reloaded has its own SpigotMC resource and resource ID.

Before the first public release:

1. Create the new **SpigotMC resource** for EggEmAll Reloaded.
2. Re-enable `SpigotUpdateChecker` in `src/main/java/dev/shadmage/eggemall2/EggEmAllPlugin.java` using the new EggEmAll Reloaded resource ID.
3. Run the full Maven verification and runtime smoke tests again after the update checker is re-enabled.
4. Verify on a test server that bStats reports to the registered EggEmAll Reloaded project ID `33887`.

## SpigotMC

Publish the project as **EggEmAll Reloaded** and state clearly that it is a maintained fork of EggEmAll2 by shadmage / Dirty-Dog-Gaming, published with the original author's permission.

Link back to:

- the original EggEmAll2 project/resource;
- the original upstream GitHub repository;
- the maintained Tebrox-Development repository.

After the resource is live, add its final URL to the README and use its numeric resource ID when re-enabling the update checker.

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

Because the Bukkit/Paper plugin name changes from `EggEmAll2` to `EggEmAllReloaded`, EggEmAll Reloaded uses its own plugin data directory.

Version 3.0.0 includes `/eggemall migrate` for importing an existing `plugins/EggEmAll2/settings.yml`. The command:

- requires `eggemall.command.migrate`;
- creates a backup of the current Reloaded `settings.yml` before replacing it;
- leaves the original `plugins/EggEmAll2` directory untouched;
- reloads the imported configuration immediately;
- restores the previous Reloaded settings automatically if the imported configuration cannot be loaded;
- creates a migration marker after success so the old settings are not imported again accidentally.

When legacy settings are present and no successful migration has been recorded yet, the plugin prints a startup hint telling the administrator to run `/eggemall migrate`.

Before the first public release, test the migration with a real EggEmAll2 installation and verify the imported gameplay settings, permissions and restrictions after reload. Also verify that a second `/eggemall migrate` attempt is rejected and that the old EggEmAll2 files remain unchanged.

The following compatibility identifiers intentionally stay unchanged unless a separate breaking release explicitly decides otherwise:

- `/eggemall` command namespace
- `eggemall.*` permission namespace
- `%eggemall_...%` PlaceholderAPI namespace
- legacy entity-data PDC keys needed to restore existing captured eggs

## Final release check

Before uploading the first public build:

- new Spigot resource exists and the update checker is re-enabled with its new resource ID;
- bStats project ID `33887` configured and reporting verified;
- SpigotMC attribution text present;
- Modrinth attribution/derivative disclosure present;
- README links updated;
- clean-install smoke test passed;
- EggEmAll2 upgrade/migration test passed;
- GitHub stable release artifact uses the EggEmAll Reloaded name.
