package com.lorcomp.mod.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.block.material.Material;

/** Прозрачное стекло с высокой устойчивостью к взрывам. */
public class ModArmoredGlass extends ModSimpleBlock {
    public ModArmoredGlass() {
        super(Material.GLASS, 4.0F, 100.0F);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
}
