package com.lorcomp.mod.init;

import com.lorcomp.mod.LorComp;
import com.lorcomp.mod.blocks.ModArmoredGlass;
import com.lorcomp.mod.blocks.ModEnergyCableBlock;
import com.lorcomp.mod.blocks.ModOreBlock;
import com.lorcomp.mod.blocks.ModPureGlass;
import com.lorcomp.mod.blocks.ModReinforcedConcrete;
import com.lorcomp.mod.blocks.ModSimpleBlock;
import com.lorcomp.mod.energy.CableTier;
import com.lorcomp.mod.items.ModCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Центральный Registry блоков и TileEntity.
 */
@Mod.EventBusSubscriber(modid = LorComp.MOD_ID)
public final class ModBlocks {

    private ModBlocks() {
    }

    public static Block LEAD_ORE;
    public static Block ALUMINUM_ORE;
    public static Block COPPER_ORE;
    public static Block REINFORCED_CONCRETE;
    public static Block ARMORED_GLASS;
    public static Block PURE_GLASS;
    public static Block BUNKER_DOOR;

    /** Медный FE-кабель: 256 FE/t. */
    public static Block COPPER_CABLE;

    /** Золотой FE-кабель: 1024 FE/t. */
    public static Block GOLD_CABLE;

    /** Розово-золотой FE-кабель: 4096 FE/t. */
    public static Block ROSE_GOLD_CABLE;

    /**
     * Регистрирует блоки.
     */
    @SubscribeEvent
    public static void registerBlocks(
            RegistryEvent.Register<Block> event) {

        LEAD_ORE = new ModOreBlock(
                3.0F, 15.0F, 2,
                () -> ModItems.LEAD_INGOT, 1, 1
        );

        ALUMINUM_ORE = new ModOreBlock(
                3.0F, 15.0F, 1,
                () -> ModItems.ALUMINUM_INGOT, 1, 1
        );

        COPPER_ORE = new ModOreBlock(
                3.0F, 15.0F, 1,
                () -> ModItems.COPPER_INGOT, 1, 1
        );

        REINFORCED_CONCRETE =
                new ModReinforcedConcrete();

        ARMORED_GLASS =
                new ModArmoredGlass();

        PURE_GLASS =
                new ModPureGlass();

        BUNKER_DOOR = new ModSimpleBlock(
                Material.IRON,
                5.0F,
                1000.0F
        );

        COPPER_CABLE =
                new ModEnergyCableBlock(CableTier.COPPER);

        GOLD_CABLE =
                new ModEnergyCableBlock(CableTier.GOLD);

        ROSE_GOLD_CABLE =
                new ModEnergyCableBlock(CableTier.ROSE_GOLD);

        setup(LEAD_ORE, "lead_ore", true);
        setup(ALUMINUM_ORE, "aluminum_ore", true);
        setup(COPPER_ORE, "copper_ore", true);

        setup(REINFORCED_CONCRETE,
                "reinforced_concrete", true);

        setup(ARMORED_GLASS,
                "armored_glass", true);

        setup(PURE_GLASS,
                "pure_glass", true);

        setup(BUNKER_DOOR,
                "bunker_door", true);

        setup(COPPER_CABLE,
                "copper_cable", true);

        setup(GOLD_CABLE,
                "gold_cable", true);

        setup(ROSE_GOLD_CABLE,
                "rose_gold_cable", true);

        event.getRegistry().register(LEAD_ORE);
        event.getRegistry().register(ALUMINUM_ORE);
        event.getRegistry().register(COPPER_ORE);

        event.getRegistry().register(REINFORCED_CONCRETE);
        event.getRegistry().register(ARMORED_GLASS);
        event.getRegistry().register(PURE_GLASS);
        event.getRegistry().register(BUNKER_DOOR);

        event.getRegistry().register(COPPER_CABLE);
        event.getRegistry().register(GOLD_CABLE);
        event.getRegistry().register(ROSE_GOLD_CABLE);
    }

    /**
     * Регистрирует ItemBlock для всех размещаемых блоков.
     */
    @SubscribeEvent
    public static void registerItemBlocks(
            RegistryEvent.Register<Item> event) {

        event.getRegistry().register(itemBlock(LEAD_ORE));
        event.getRegistry().register(itemBlock(ALUMINUM_ORE));
        event.getRegistry().register(itemBlock(COPPER_ORE));
        event.getRegistry().register(itemBlock(REINFORCED_CONCRETE));
        event.getRegistry().register(itemBlock(ARMORED_GLASS));
        event.getRegistry().register(itemBlock(PURE_GLASS));
        event.getRegistry().register(itemBlock(BUNKER_DOOR));
        event.getRegistry().register(itemBlock(COPPER_CABLE));
        event.getRegistry().register(itemBlock(GOLD_CABLE));
        event.getRegistry().register(itemBlock(ROSE_GOLD_CABLE));
    }

    /**
     * Привязывает registry/unlocalized name к блоку.
     */
    private static void setup(
            Block block,
            String name,
            boolean creative) {

        block.setRegistryName(
                LorComp.MOD_ID,
                name
        );

        block.setUnlocalizedName(
                LorComp.MOD_ID + "." + name
        );

        if (creative) {
            block.setCreativeTab(
                    ModCreativeTab.INSTANCE
            );
        }
    }

    /**
     * Создаёт ItemBlock с тем же ResourceLocation, что и Block.
     */
    private static ItemBlock itemBlock(Block block) {
        return new ItemBlock(block)
                .setRegistryName(block.getRegistryName())
                .setUnlocalizedName(block.getUnlocalizedName())
                .setCreativeTab(ModCreativeTab.INSTANCE);
    }

}
