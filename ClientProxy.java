package com.lorcomp.mod;

import com.lorcomp.mod.init.ModBlocks;
import com.lorcomp.mod.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Клиентский прокси.
 *
 * <p>Все ItemBlock и предметы получают явную inventory-модель,
 * как требует Forge 1.12.x.</p>
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        registerItem(ModItems.LEAD_INGOT);
        registerItem(ModItems.ALUMINUM_INGOT);
        registerItem(ModItems.COPPER_INGOT);
        registerItem(ModItems.STEEL_INGOT);
        registerItem(ModItems.BRONZE_INGOT);
        registerItem(ModItems.ROSE_GOLD_INGOT);

        registerBlock(ModBlocks.LEAD_ORE);
        registerBlock(ModBlocks.ALUMINUM_ORE);
        registerBlock(ModBlocks.COPPER_ORE);
        registerBlock(ModBlocks.REINFORCED_CONCRETE);
        registerBlock(ModBlocks.ARMORED_GLASS);
        registerBlock(ModBlocks.PURE_GLASS);
        registerBlock(ModBlocks.BUNKER_DOOR);

        registerBlock(ModBlocks.COPPER_CABLE);
        registerBlock(ModBlocks.GOLD_CABLE);
        registerBlock(ModBlocks.ROSE_GOLD_CABLE);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    /**
     * Регистрирует inventory-модель обычного Item.
     */
    private void registerItem(Item item) {
        if (item == null || item.getRegistryName() == null) {
            return;
        }

        ModelLoader.setCustomModelResourceLocation(
                item,
                0,
                new ModelResourceLocation(
                        item.getRegistryName(),
                        "inventory"
                )
        );
    }

    /**
     * Регистрирует inventory-модель ItemBlock.
     *
     * <p>Это отдельная регистрация: Block и ItemBlock находятся
     * в разных Forge Registry.</p>
     */
    private void registerBlock(Block block) {
        if (block == null || block.getRegistryName() == null) {
            return;
        }

        Item item = Item.getItemFromBlock(block);
        if (item == null) {
            return;
        }

        ModelLoader.setCustomModelResourceLocation(
                item,
                0,
                new ModelResourceLocation(
                        block.getRegistryName(),
                        "inventory"
                )
        );
    }
}
