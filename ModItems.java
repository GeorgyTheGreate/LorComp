package com.lorcomp.mod.init;

import com.lorcomp.mod.LorComp;
import com.lorcomp.mod.items.ModCreativeTab;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Центральный Registry предметов.
 */
@Mod.EventBusSubscriber(modid = LorComp.MOD_ID)
public final class ModItems {

    private ModItems() {
    }

    public static Item LEAD_INGOT;
    public static Item ALUMINUM_INGOT;
    public static Item COPPER_INGOT;
    public static Item STEEL_INGOT;
    public static Item BRONZE_INGOT;
    public static Item ROSE_GOLD_INGOT;

    /**
     * Регистрирует все базовые материалы.
     */
    @SubscribeEvent
    public static void registerItems(
            RegistryEvent.Register<Item> event) {

        LEAD_INGOT = create("lead_ingot");
        ALUMINUM_INGOT = create("aluminum_ingot");
        COPPER_INGOT = create("copper_ingot");
        STEEL_INGOT = create("steel_ingot");
        BRONZE_INGOT = create("bronze_ingot");
        ROSE_GOLD_INGOT = create("rose_gold_ingot");

        event.getRegistry().register(LEAD_INGOT);
        event.getRegistry().register(ALUMINUM_INGOT);
        event.getRegistry().register(COPPER_INGOT);
        event.getRegistry().register(STEEL_INGOT);
        event.getRegistry().register(BRONZE_INGOT);
        event.getRegistry().register(ROSE_GOLD_INGOT);
    }

    /**
     * Создаёт простой material-item.
     */
    private static Item create(String name) {
        return new Item()
                .setRegistryName(LorComp.MOD_ID, name)
                .setUnlocalizedName(LorComp.MOD_ID + "." + name)
                .setCreativeTab(ModCreativeTab.INSTANCE);
    }
}
