package com.lorcomp.mod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Базовый простой блок без TileEntity.
 */
public class ModSimpleBlock extends Block {

    /**
     * Создаёт блок с физическими параметрами.
     *
     * @param material материал Minecraft
     * @param hardness скорость/прочность разрушения
     * @param resistance устойчивость к взрывам
     */
    public ModSimpleBlock(Material material, float hardness, float resistance) {
        super(material);
        setHardness(hardness);
        setResistance(resistance);
    }
}
