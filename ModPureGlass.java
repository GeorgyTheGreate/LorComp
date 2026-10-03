package com.lorcomp.mod.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;

/**
 * Чистое стекло.
 *
 * <p>Связка с multipart/моделями и прозрачным render layer
 * убирает внутренние непрозрачные кубы. В 1.12.2 полное
 * connected-glass поведение определяется также моделью.</p>
 */
public class ModPureGlass extends ModSimpleBlock {
    public ModPureGlass() {
        super(Material.GLASS, 0.3F, 10.0F);
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
