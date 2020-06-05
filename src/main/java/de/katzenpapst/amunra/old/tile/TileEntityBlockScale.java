package de.katzenpapst.amunra.old.tile;

import de.katzenpapst.amunra.helper.BlockMassHelper;
import de.katzenpapst.amunra.old.block.IMetaBlock;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBlockScale extends TileEntity {

	protected long			ticks			= 0;
	protected float			massToDisplay	= 0;
	protected BlockMetaPair	lastFoundBlock	= null;

	public TileEntityBlockScale() {

	}

	public void doUpdate() {
		Block b = this.world.getBlock(xCoord, yCoord + 1, zCoord);
		int meta = this.world.getBlockMetadata(xCoord, yCoord + 1, zCoord);

		if (lastFoundBlock != null && lastFoundBlock.getBlock() == b && lastFoundBlock.getMetadata() == meta)
			// nothing changed
			return;

		lastFoundBlock = new BlockMetaPair(b, (byte) meta);

		// mass
		massToDisplay = BlockMassHelper.getBlockMass(world, b, meta, xCoord, yCoord + 1, zCoord);
		this.world.markBlockForUpdate(xCoord, yCoord, zCoord);
		// this.markDirty();
	}

	public float getCurrentMass() {
		return massToDisplay;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound data = new NBTTagCompound();
		this.writeToNBT(data);
		return new SPacketEntity(this.xCoord, this.yCoord, this.zCoord, 2, data);
	}

	public int getRotationMeta() {
		Block b = world.getBlock(xCoord, yCoord, zCoord);
		int meta = world.getBlockMetadata(xCoord, yCoord, zCoord);
		if (b instanceof IMetaBlock)
			return ((IMetaBlock) b).getRotationMeta(meta);
		return 0;
	}

	@Override
	public void onDataPacket(NetworkManager netManager, S35PacketUpdateTileEntity packet) {
		readFromNBT(packet.func_148857_g());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.massToDisplay = nbt.getFloat("mass");
	}

	@Override
	public void updateEntity() {
		if (this.world.isRemote)
			return;
		this.ticks++;

		if (ticks % 80 == 0) {
			doUpdate();
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setFloat("mass", this.massToDisplay);
		return nbt;
	}

}
