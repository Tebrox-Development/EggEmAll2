# Publishing EggEmAll Reloaded

This checklist covers the remaining manual publication steps for the maintained fork.

## Release blockers

The EggEmAll Reloaded SpigotMC resource now exists at:

https://www.spigotmc.org/resources/eggemall-reloaded.138577/

Its numeric resource ID is **`138577`**, and the Spigot update checker is enabled against that ID.

Before the first stable public release:

1. Run the full Maven verification and runtime smoke tests again with the update checker enabled.
2. Verify on a test server that the update checker reaches resource ID `138577` without errors.
3. Verify on a test server that bStats reports to the registered EggEmAll Reloaded project ID `33887`.
4. Replace the temporary Snapshot build on SpigotMC with the final `3.0.0` release artifact.

## SpigotMC

EggEmAll Reloaded is published at:

https://www.spigotmc.org/resources/eggemall-reloaded.138577/

The page should state clearly that it is a maintained fork of EggEmAll2 by shadmage / Dirty-Dog-Gaming, published with the original author's permission.

Keep links to:

- the original EggEmAll2 project/resource;
- the original upstream GitHub repository;
- the maintained Tebrox-Development repository.

The runtime update checker uses SpigotMC resource ID **`138577`**.

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
- creates a migration marker after success so the old settings are not imported again accidentally;
- replaces only the legacy default `[EggEmAll]` log/chat prefixes with `[EggEmAll Reloaded]`, while preserving custom prefixes.

When legacy settings are present and no successful migration has been recorded yet, the plugin prints a startup hint telling the administrator to run `/eggemall migrate`.

The migration has been manually validated on the 3.0.0 Snapshot line: the old settings were imported, the Reloaded settings backup was created, the original EggEmAll2 files remained untouched, and the imported configuration reloaded successfully.

The following compatibility identifiers intentionally stay unchanged unless a separate breaking release explicitly decides otherwise:

- `/eggemall` command namespace
- `eggemall.*` permission namespace
- `%eggemall_...%` PlaceholderAPI namespace
- legacy entity-data PDC keys needed to restore existing captured eggs

## Final release check

Before uploading the first stable public build:

- Spigot resource `138577` exists and the update checker is enabled against it;
- bStats project ID `33887` configured and reporting verified;
- SpigotMC attribution text present;
- Modrinth attribution/derivative disclosure present;
- README links updated;
- clean-install smoke test passed;
- EggEmAll2 upgrade/migration test passed;
- GitHub stable release artifact uses the EggEmAll Reloaded name.
