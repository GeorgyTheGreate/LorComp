package com.lorcomp.mod.init;

import com.lorcomp.mod.LorComp;
import com.lorcomp.mod.tile.TileEnergyCable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModTileEntities {

    private ModTileEntities() {
    }

    public static void register() {
        GameRegistry.registerTileEntity(
                TileEnergyCable.class,
                new ResourceLocation(LorComp.MOD_ID, "energy_cable")
        );
    }
}
