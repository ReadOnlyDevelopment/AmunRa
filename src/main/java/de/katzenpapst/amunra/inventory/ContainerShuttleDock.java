package de.katzenpapst.amunra.inventory;

import de.katzenpapst.amunra.old.item.ItemShuttle;
import de.katzenpapst.amunra.old.tile.TileEntityShuttleDock;
import micdoodle8.mods.galacticraft.core.inventory.SlotSpecific;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerShuttleDock extends ContainerWithPlayerInventory {

	public ContainerShuttleDock(InventoryPlayer player, TileEntityShuttleDock tile) {
		super(tile);

		this.addSlotToContainer(new SlotSpecific(tile, 0, 137, 59, ItemShuttle.class));

		initPlayerInventorySlots(player, 9);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return ((TileEntityShuttleDock) this.tileEntity).isUseableByPlayer(player);
	}

}
