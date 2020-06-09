/*
 * ReadOnlyCore
 * Copyright (C) 2020 ROMVoid95
 *
 * This library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package _net.rom.stellar.item;

import _net.rom.stellar.block.BlockMetaSubtypes;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

/**
 * This will be removed completely in the next MC update of the Mod.
 * Kept for StellarRegistry utilization
 * 
 * Take care to avoid using this class to extend metadata blocks unless
 * your aware of the implications of future updates to your Mod.
 */
@Deprecated
public class ItemBlockMetaSubtypes extends net.minecraft.item.ItemBlock {
    private final int subtypeCount;

    public ItemBlockMetaSubtypes(BlockMetaSubtypes block) {
        this(block, block.getSubtypeCount());
    }

    public ItemBlockMetaSubtypes(Block block, int subtypeCount) {
        super(block);
        this.subtypeCount = subtypeCount;
        setMaxDamage(0);
        setHasSubtypes(subtypeCount > 1);
    }

    @Override
    public int getMetadata(int damage) {
        return damage & 0xF;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + getMetadata(stack.getItemDamage());
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (!isInCreativeTab(tab)) return;
        for (int i = 0; i < this.subtypeCount; ++i)
            items.add(new ItemStack(this, 1, i));
    }
}
