### Gameplay Changes
* Netherite Shield enchantability has been reduced. `20 -> 15`
* Barbed durability damage has been reduced. `2 -> 1`
* Barbed success chance has been increased. `20% -> 33%`

### Config Changes
* The enchantability of the Shield and Netherite Shield have been made configurable.
* Configurations for Pummeling and Barbed and projectile deflection strength have been removed.

### Technical Changes
Guarding now uses 1.21's data driven enchantments, for this reason, several configurations were removed in favor of this new system.

Added 3 new enchantment effect components:

`guarding:shield_blocked`: Effects applying after an attack is blocked.
* Condition Context: Damage Parameters
* Effect: Entity Effect
* Additional fields:
    - `cancel_on_parry`: When true, stops the effects from being applied when a parry is landed
    - `affected`: A specifier for whom the effect is applied to. Possible values are `attacker`, `damaging_entity`, and `victim`

`guarding:shield_parried`: Effects applying after an attack is parried.
* Condition Context: Damage Parameters
* Effect: Entity Effect
* Additional fields:
  - `affected`: A specifier for whom the effect is applied to. Possible values are `attacker`, `damaging_entity`, and `victim`

`guarding:shield_knockback`: Effects for the amount of knockback to deal when an attack is blocked.
* Condition Context: Damage Parameters
* Effect: Value Effect
