package com.lorcomp.mod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import java.util.Random;
import java.util.function.Supplier;

/**
 * Общая реализация металлической руды.
 *
 * <p>В отличие от обычного ItemBlock, при разрушении руда может
 * отдавать перерабатываемый ресурс.</p>
 */
public class ModOreBlock extends Block {

    /** Предмет, выпадающий из руды. */
    private final Supplier<Item> dropItem;

    /** Минимальное количество дропа. */
    private final int minDrop;

    /** Максимальное количество дропа. */
    private final int maxDrop;

    public ModOreBlock(float hardness, float resistance, int harvestLevel,
                       Supplier<Item> dropItem, int minDrop, int maxDrop) {
        super(Material.ROCK);
        this.dropItem = dropItem;
        this.minDrop = minDrop;
        this.maxDrop = maxDrop;
        setHardness(hardness);
        setResistance(resistance);
        setHarvestLevel("pickaxe", harvestLevel);
    }

    /** Возвращает ресурс, добываемый из руды. */
    @Override
    public Item getItemDropped(IBlockState state, Random random, int fortune) {
        return dropItem.get();
    }

    /**
     * Равномерно выбирает число предметов из диапазона [minDrop; maxDrop].
     */
    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        if (minDrop >= maxDrop) {
            return minDrop;
        }
        return minDrop + random.nextInt(maxDrop - minDrop + 1);
    }
}
