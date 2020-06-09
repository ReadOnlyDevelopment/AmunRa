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
package _net.rom.stellar.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSL extends Container {

  protected final IInventory tileInventory;

  public ContainerSL(InventoryPlayer playerInventory, IInventory tileInventory) {

    this.tileInventory = tileInventory;
    addTileInventorySlots(tileInventory);
    addPlayerInventorySlots(playerInventory);
  }

  protected void addTileInventorySlots(IInventory inv) {

  }

  protected void addPlayerInventorySlots(InventoryPlayer inv) {

    int i;
    for (i = 0; i < 3; ++i) {
      for (int j = 0; j < 9; ++j) {
        this.addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
      }
    }

    for (i = 0; i < 9; ++i) {
      this.addSlotToContainer(new Slot(inv, i, 8 + i * 18, 142));
    }
  }

  @Override
  public boolean canInteractWith(EntityPlayer player) {

    return tileInventory.isUsableByPlayer(player);
  }

  @Deprecated
  public static void onTakeFromSlot(Slot slot, EntityPlayer player, ItemStack stack) {

    slot.onTake(player, stack);
  }
}
