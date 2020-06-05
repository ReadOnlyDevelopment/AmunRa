package de.katzenpapst.amunra.inventory.schematic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

public class InventorySchematicShuttle implements IInventory {
	protected final ItemStack[]	stackList;
	protected final int			inventoryWidth;
	protected final Container	eventHandler;

	public InventorySchematicShuttle(int numSlots, Container par1Container) {
		this.stackList = new ItemStack[numSlots];
		this.eventHandler = par1Container;
		this.inventoryWidth = 5; // what for?
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeInventory() {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		// TODO Auto-generated method stub

	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (this.stackList[slot] != null) {
			ItemStack var3;

			if (this.stackList[slot].stackSize <= amount) {
				var3 = this.stackList[slot];
				this.stackList[slot] = null;
				this.eventHandler.onCraftMatrixChanged(this);
				return var3;
			} else {
				var3 = this.stackList[slot].splitStack(amount);

				if (this.stackList[slot].stackSize == 0) {
					this.stackList[slot] = null;
				}

				this.eventHandler.onCraftMatrixChanged(this);
				return var3;
			}
		} else
			return null;
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getInventoryName() {
		return "container.crafting";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSizeInventory() {
		return this.stackList.length;
	}

	public ItemStack getStackInRowAndColumn(int x, int y) {
		if (x >= 0 && x < this.inventoryWidth) {
			final int stackNr = x + y * this.inventoryWidth;
			if (stackNr >= 22)
				return null;
			return this.getStackInSlot(stackNr);
		} else
			return null;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return slot >= this.getSizeInventory() ? null : this.stackList[slot];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (this.stackList[slot] != null) {
			final ItemStack curStack = this.stackList[slot];
			this.stackList[slot] = null;
			return curStack;
		} else
			return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public boolean hasCustomName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false; // but why?
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		return true;
	}

	@Override
	public void markDirty() {
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void openInventory(EntityPlayer player) {
		// TODO Auto-generated method stub

	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack par2ItemStack) {
		this.stackList[slot] = par2ItemStack;
		this.eventHandler.onCraftMatrixChanged(this);
	}
}
