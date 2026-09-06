# Publishing EggEmAll Reloaded

This checklist covers the remaining manual publication steps for the maintained fork.

## Release blockers

Do not publish an EggEmAll Reloaded build to SpigotMC or Modrinth while it still uses the original project's publication/metrics identifiers.

Before the first public release:

1. Create the new **SpigotMC resource** for EggEmAll Reloaded.
2. Replace the original Spigot resource ID `122056` used by `SpigotUpdateChecker` in `src/main/java/dev/shadmage/eggemall2/EggEmAllPlugin.java` with the new EggEmAll Reloaded resource ID.
3. Create the new **bStats plugin page** for EggEmAll Reloaded.
4. Replace the original bStats plugin ID `15978` used by `Metrics` in `src/main/java/dev/shadmage/eggemall2/EggEmAllPlugin.java` with the new EggEmAll Reloaded bStats ID.
5. Run the full Maven verification and runtime smoke tests again after both IDs are changed.

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

Create a separate bStats project for EggEmAll Reloaded rather than sending metrics to the original EggEmAll2 project. Once the new project exists, replace ID `15978` in `EggEmAllPlugin.java` with the new ID and verify that a test server reports to the new project.

## GitHub repository name

The repository can be renamed from `EggEmAll2` to **EggEmAll-Reloaded** after the rebranding changes are merged. GitHub normally redirects existing repository URLs after a rename, but repository metadata, badges and any hard-coded links should still be checked afterward.

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
- new bStats ID configured;
- SpigotMC attribution text present;
- Modrinth attribution/derivative disclosure present;
- README links updated;
- clean-install smoke test passed;
- EggEmAll2 upgrade/migration test passed;
- GitHub stable release artifact uses the EggEmAll Reloaded name.
