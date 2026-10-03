package com.lorcomp.mod;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Общий прокси. Здесь запрещён клиентский Rendering API.
 */
public class CommonProxy {

    /** Общий preInit. */
    public void preInit(FMLPreInitializationEvent event) {
    }

    /** Общий init. */
    public void init(FMLInitializationEvent event) {
    }

    /** Общий postInit. */
    public void postInit(FMLPostInitializationEvent event) {
    }
}
