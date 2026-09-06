<p align="center">
  <img src="docs/assets/eggemall-reloaded-banner.jpg" alt="EggEmAll Reloaded">
</p>

<h1 align="center">EggEmAll Reloaded</h1>

<p align="center">
  <a href="https://www.spigotmc.org/resources/eggemall-reloaded.138577/">
    <img src="https://img.shields.io/badge/Spigot-Download-orange?logo=spigotmc" alt="Spigot">
  </a>
  <a href="https://github.com/Tebrox-Development/EggEmAll-Reloaded/releases/latest">
    <img src="https://img.shields.io/github/v/release/Tebrox-Development/EggEmAll-Reloaded?label=Release&logo=github" alt="Latest release">
  </a>
  <a href="https://github.com/Tebrox-Development/EggEmAll-Reloaded/actions/workflows/ci.yml">
    <img src="https://github.com/Tebrox-Development/EggEmAll-Reloaded/actions/workflows/ci.yml/badge.svg?branch=master" alt="Build">
  </a>
  <a href="https://bstats.org/plugin/bukkit/EggEmAll%20Reloaded/33887">
    <img src="https://img.shields.io/badge/bStats-Statistics-4c9b3f" alt="bStats">
  </a>
</p>

**EggEmAll Reloaded** lets players capture entities and receive their spawn eggs by throwing chicken eggs at them.

## Features

- Capture mobs by throwing chicken eggs at them
- Preserve supported entity data when mobs are captured
- Configurable capture chances and restrictions
- Per-category and per-mob permissions
- World blacklist/whitelist support
- PlaceholderAPI integration
- RoseStacker and UltimateStacker support
- Migration support for existing EggEmAll2 installations

## Installation

1. Download the latest stable release from [SpigotMC](https://www.spigotmc.org/resources/eggemall-reloaded.138577/) or [GitHub Releases](https://github.com/Tebrox-Development/EggEmAll-Reloaded/releases/latest).
2. Place the JAR in your server's `plugins` directory.
3. Start or restart the server.
4. Configure EggEmAll Reloaded in `plugins/EggEmAllReloaded/settings.yml` as needed.

Existing EggEmAll2 users should follow the [upgrade instructions](#upgrading-from-eggemall2) below.

## Maintained fork

EggEmAll Reloaded is a maintained fork of [Dirty-Dog-Gaming/EggEmAll2](https://github.com/Dirty-Dog-Gaming/EggEmAll2), originally developed by **shadmage / Dirty-Dog-Gaming**.

The original author explicitly allowed the project to be forked and continued, and has also approved publishing the maintained fork on SpigotMC and Modrinth under the **EggEmAll Reloaded** name with attribution to the original project.

The goal of this fork is to keep the plugin working on current Minecraft server releases while preserving the original gameplay, configuration, commands, placeholders and permissions as far as practical. The upstream project and its original author remain the source of the plugin; maintenance changes in this fork are intentionally kept traceable through Git history.

The Java package and existing command/permission namespaces intentionally remain compatible with EggEmAll2 for now. The plugin descriptor also declares that EggEmAll Reloaded provides `EggEmAll2` for dependency compatibility where supported by the server.

## Compatibility

- Primary target: **Paper 26.2**
- Manually validated runtimes: **Paper 26.2, Paper 1.21.7, Spigot 26.2 and Spigot 1.21.7**
- Build toolchain: **Java 25**
- Produced plugin bytecode: **Java 17** where feasible
- Existing configuration keys, commands and permission nodes should remain compatible unless a breaking change is unavoidable and documented

See [docs/COMPATIBILITY.md](docs/COMPATIBILITY.md) for the current validation status.

## Upgrading from EggEmAll2

EggEmAll Reloaded uses a new plugin name and therefore creates its own plugin data directory. Existing command aliases, permission nodes and PlaceholderAPI identifiers remain unchanged.

If `plugins/EggEmAll2/settings.yml` is present, EggEmAll Reloaded reports this on startup. Run `/eggemall migrate` to import the legacy settings into the Reloaded data directory. The migration creates a backup of the current Reloaded `settings.yml`, leaves the original EggEmAll2 directory untouched and reloads the imported configuration. A successful migration is marked so the legacy file is not imported again accidentally. Legacy default `[EggEmAll]` log/chat prefixes are updated to `[EggEmAll Reloaded]`; custom prefixes are preserved.

The Reloaded SpigotMC resource is available as resource ID `138577`, and the update checker uses that resource. Release and publishing procedures are documented in [docs/PUBLISHING.md](docs/PUBLISHING.md).

## Commands

- `/eggemall menu` - opens the GUI with catchable and blacklisted mobs
- `/eggemall reload` - reloads the plugin configuration
- `/eggemall migrate` - safely imports `plugins/EggEmAll2/settings.yml` into EggEmAll Reloaded

## Permissions

- `eggemall.command.reload` - reload the plugin
- `eggemall.command.gui` - open the GUI showing catchable and blacklisted entities
- `eggemall.command.migrate` - migrate `settings.yml` from an existing EggEmAll2 installation
- `eggemall.all` - grant all category permissions below
- `eggemall.villagers` - allow catching villager-type entities
- `eggemall.aggressive` - allow catching monsters
- `eggemall.passive` - allow catching animals
- `eggemall.unknown` - fallback category for entities not grouped above
- `eggemall.catchmob.<entity_type>` - mob-specific catch permission using the lowercase Bukkit entity type, for example `eggemall.catchmob.cow`

For compatibility with upstream 2.1.1 configurations, the previous display-name-derived mob-specific permission is also accepted during the transition.

## PlaceholderAPI

PlaceholderAPI support is optional. EggEmAll Reloaded works normally when PlaceholderAPI is not installed.

When PlaceholderAPI is available, normal `%...%` placeholders can be used in EggEmAll Reloaded message values and spawn-egg lore in addition to the existing built-in `{...}` placeholders.

The PlaceholderAPI integration has been manually validated on **Paper 26.2** and **Paper 1.21.7**.

### EggEmAll PlaceholderAPI placeholders

The existing `eggemall` PlaceholderAPI namespace is intentionally retained for compatibility:

- `%eggemall_version%` - installed EggEmAll Reloaded version
- `%eggemall_catch_chance%` - configured capture chance percentage
- `%eggemall_world_mode%` - `blacklist` or `whitelist`
- `%eggemall_world_allowed%` - whether capture is allowed in the current player's world
- `%eggemall_require_permissions%` - whether capture permissions are required
- `%eggemall_catchable_entities%` - number of currently catchable entity types
- `%eggemall_blacklisted_entities%` - number of configured blacklisted entity types

### Built-in EggEmAll placeholders

The existing EggEmAll placeholders remain supported in their original message/lore contexts:

- `{player}` - player who captured the entity
- `{entity}` - captured entity type
- `{entity_name}` - entity name used for the spawn-egg lore
- `{profession}` - villager profession, or blank when not applicable
- `{world}` - current world name in supported messages

Villager professions are rendered as readable names such as `Fletcher` instead of the server's internal profession representation.

## Support

Found a bug or have a feature request? Please use the [GitHub issue tracker](https://github.com/Tebrox-Development/EggEmAll-Reloaded/issues).

## Dependencies and attribution

EggEmAll Reloaded uses [MineAcademy Foundation](https://github.com/kangarko/Foundation), which is bundled into the plugin JAR and relocated into the plugin's own package. Foundation remains the work of MineAcademy and is not claimed as part of EggEmAll Reloaded.

Anonymous usage metrics are provided through the official **bStats Bukkit 3.2.1** library, which is shaded and relocated into the plugin JAR. EggEmAll Reloaded reports to its own bStats project with plugin ID **33887**, separate from the original EggEmAll2 metrics project.

Optional runtime integrations are provided for PlaceholderAPI, RoseStacker and UltimateStacker when those plugins are installed. The UltimateStacker integration detects both the legacy Songoda API package and the current Craftaro API package at runtime, so neither generation is a hard build dependency.

## Project links

- SpigotMC: https://www.spigotmc.org/resources/eggemall-reloaded.138577/
- GitHub Releases: https://github.com/Tebrox-Development/EggEmAll-Reloaded/releases
- bStats: https://bstats.org/plugin/bukkit/EggEmAll%20Reloaded/33887
- Issue tracker: https://github.com/Tebrox-Development/EggEmAll-Reloaded/issues
- Source repository: https://github.com/Tebrox-Development/EggEmAll-Reloaded

## Original project

- Upstream repository: https://github.com/Dirty-Dog-Gaming/EggEmAll2
- Original SpigotMC resource: https://www.spigotmc.org/resources/eggemall.122056/
- Original bStats page: https://bstats.org/plugin/bukkit/EggEmAll2/15978

EggEmAll Reloaded is independently maintained by **Tebrox-Development** and is not an official release of Dirty-Dog-Gaming.
