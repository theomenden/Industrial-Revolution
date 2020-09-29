package me.steven.indrev.blockentities.crafters

import me.steven.indrev.components.CraftingComponent
import me.steven.indrev.components.TemperatureComponent
import me.steven.indrev.inventories.inventory
import me.steven.indrev.items.upgrade.Upgrade
import me.steven.indrev.recipes.machines.IRRecipeType
import me.steven.indrev.recipes.machines.PulverizerRecipe
import me.steven.indrev.registry.MachineRegistry
import me.steven.indrev.utils.Tier
import net.minecraft.screen.ArrayPropertyDelegate

class PulverizerFactoryBlockEntity(tier: Tier) :
    CraftingMachineBlockEntity<PulverizerRecipe>(tier, MachineRegistry.PULVERIZER_FACTORY_REGISTRY) {

    init {
        this.propertyDelegate = ArrayPropertyDelegate(15)
        this.temperatureComponent = TemperatureComponent({ this }, 0.06, 700..1100, 1400.0)
        this.inventoryComponent = inventory(this) {
            input {
                slots = when (tier) {
                    Tier.MK1 -> intArrayOf(6, 8)
                    Tier.MK2 -> intArrayOf(6, 8, 10)
                    Tier.MK3 -> intArrayOf(6, 8, 10, 12)
                    else -> intArrayOf(6, 8, 10, 12, 14)
                }
            }
            output {
                slots = when (tier) {
                    Tier.MK1 -> intArrayOf(7, 9)
                    Tier.MK2 -> intArrayOf(7, 9, 11)
                    Tier.MK3 -> intArrayOf(7, 9, 11, 13)
                    else ->  intArrayOf(7, 9, 11, 13, 15)
                }
            }
        }
        this.craftingComponents = Array(5) { index ->
            CraftingComponent(index, this).apply {
                inputSlots = intArrayOf(6 + (index * 2))
                outputSlots = intArrayOf(6 + (index * 2) + 1)
            }
        }
    }

    override val type: IRRecipeType<PulverizerRecipe> = PulverizerRecipe.TYPE

    override fun getUpgradeSlots(): IntArray = intArrayOf(2, 3, 4, 5)

    override fun getAvailableUpgrades(): Array<Upgrade> = Upgrade.values()
}