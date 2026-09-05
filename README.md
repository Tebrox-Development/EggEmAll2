# Egg Em All 2

EggEmAll2 lets players capture entities and receive their spawn eggs by throwing chicken eggs at them.

## Maintained fork

This repository is a maintained fork of [Dirty-Dog-Gaming/EggEmAll2](https://github.com/Dirty-Dog-Gaming/EggEmAll2), originally developed by **shadmage / Dirty-Dog-Gaming**.

The goal of this fork is to keep the plugin working on current Paper releases while preserving the original gameplay, configuration and permissions as far as practical. The upstream project and its original author remain the source of the plugin; maintenance changes in this fork are intentionally kept traceable through Git history.

### Compatibility policy

- Primary target: **Paper 26.2**
- Build toolchain: **Java 25**
- Produced plugin bytecode: **Java 17** where feasible
- Existing 1.21.x compatibility is preserved where the APIs and dependencies still allow it
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
- `eggemall.catchmob.<mob>` - mob-specific catch permission

## Commands

- `/eggemall menu` - opens the GUI with catchable and blacklisted mobs
- `/eggemall reload` - reloads the plugin configuration

## Dependencies and attribution

EggEmAll2 uses [MineAcademy Foundation](https://github.com/kangarko/Foundation), which is bundled into the plugin JAR and relocated into EggEmAll2's own package. Foundation remains the work of MineAcademy and is not claimed as part of EggEmAll2.

Optional runtime integrations are provided for RoseStacker and UltimateStacker when those plugins are installed.

## Original project

- Upstream repository: https://github.com/Dirty-Dog-Gaming/EggEmAll2
- Original SpigotMC resource: https://www.spigotmc.org/resources/eggemall.122056/
- Original bStats page: https://bstats.org/plugin/bukkit/EggEmAll2/15978
