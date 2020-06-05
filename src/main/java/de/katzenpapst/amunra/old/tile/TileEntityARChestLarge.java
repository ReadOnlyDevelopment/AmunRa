package de.katzenpapst.amunra.old.tile;

import net.minecraft.item.ItemStack;

public class TileEntityARChestLarge extends TileEntityARChest {

	public TileEntityARChestLarge() {
		chestContents = new ItemStack[this.getSizeInventory()];
	}

	@Override
	protected boolean canDoublechest() {
		return false;
	}

	/**
	 * Returns the number of slots in the inventory. mj
	 */
	@Override
	public int getSizeInventory() {
		return 54;
	}
}
