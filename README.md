# Egg Em All 2

EggEmAll2 lets players capture entities and receive their spawn eggs by throwing chicken eggs at them.

## Maintained fork

This repository is a maintained fork of [Dirty-Dog-Gaming/EggEmAll2](https://github.com/Dirty-Dog-Gaming/EggEmAll2), originally developed by **shadmage / Dirty-Dog-Gaming**.

The goal of this fork is to keep the plugin working on current Minecraft server releases while preserving the original gameplay, configuration and permissions as far as practical. The upstream project and its original author remain the source of the plugin; maintenance changes in this fork are intentionally kept traceable through Git history.

### Compatibility policy

- Primary target: **Paper 26.2**
- Manually validated runtimes: **Paper 26.2, Paper 1.21.7, Spigot 26.2 and Spigot 1.21.7**
- Build toolchain: **Java 25**
- Produced plugin bytecode: **Java 17** where feasible
- Existing configuration keys and permission nodes should remain compatible unless a breaking change is unavoidable and documented

See [docs/COMPATIBILITY.md](docs/COMPATIBILITY.md) for the current validation status.

## Permissions

- `eggemall.command.reload` - reload the plugin
- `eggemall.command.menu` - open the GUI showing catchable and blacklisted entities
- `eggemall.all` - grant all category permissions below
- `eggemall.villagers` - allow catching villager-type entities
- `eggemall.aggressive` - allow catching monsters
- `eggemall.passive` - allow catching animals
- `eggemall.unknown` - fallback category for entities not grouped above
- `eggemall.catchmob.<entity_type>` - mob-specific catch permission using the lowercase Bukkit entity type, for example `eggemall.catchmob.cow`

For compatibility with upstream 2.1.1 configurations, the previous display-name-derived mob-specific permission is also accepted during the transition.

## Commands

- `/eggemall menu` - opens the GUI with catchable and blacklisted mobs
- `/eggemall reload` - reloads the plugin configuration

## PlaceholderAPI

PlaceholderAPI support is optional. EggEmAll2 works normally when PlaceholderAPI is not installed.

When PlaceholderAPI is available, normal `%...%` placeholders can be used in EggEmAll2 message values and spawn-egg lore in addition to the existing built-in `{...}` placeholders.

The PlaceholderAPI integration has been manually validated on **Paper 26.2** and **Paper 1.21.7**.

### EggEmAll PlaceholderAPI placeholders

EggEmAll2 registers the following PlaceholderAPI placeholders:

- `%eggemall_version%` - installed EggEmAll2 version
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

## Dependencies and attribution

EggEmAll2 uses [MineAcademy Foundation](https://github.com/kangarko/Foundation), which is bundled into the plugin JAR and relocated into EggEmAll2's own package. Foundation remains the work of MineAcademy and is not claimed as part of EggEmAll2.

Optional runtime integrations are provided for PlaceholderAPI, RoseStacker and UltimateStacker when those plugins are installed. The UltimateStacker integration detects both the legacy Songoda API package and the current Craftaro API package at runtime, so neither generation is a hard EggEmAll2 build dependency.

## Original project

- Upstream repository: https://github.com/Dirty-Dog-Gaming/EggEmAll2
- Original SpigotMC resource: https://www.spigotmc.org/resources/eggemall.122056/
- Original bStats page: https://bstats.org/plugin/bukkit/EggEmAll2/15978
