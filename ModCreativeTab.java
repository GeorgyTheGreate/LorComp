package com.lorcomp.mod.items;

import com.lorcomp.mod.LorComp;
import com.lorcomp.mod.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

/**
 * Единая вкладка всех объектов LorComp.
 */
public final class ModCreativeTab extends CreativeTabs {

    /** Единственный экземпляр вкладки. */
    public static final ModCreativeTab INSTANCE =
            new ModCreativeTab();

    private ModCreativeTab() {
        super(LorComp.MOD_ID);
    }

    /**
     * Иконкой вкладки служит слиток свинца.
     */
    @Override
    public ItemStack getTabIconItem() {
        return ModItems.LEAD_INGOT == null
                ? ItemStack.EMPTY
                : new ItemStack(ModItems.LEAD_INGOT);
    }
}
