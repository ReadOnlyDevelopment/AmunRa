package de.katzenpapst.amunra.old.tile;

import java.util.EnumSet;

import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.vec.Vector3int;
import micdoodle8.mods.galacticraft.api.transmission.NetworkType;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseUniversalElectrical;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.miccore.Annotations.RuntimeInterface;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

/**
 * This is supposed to be an universal booster TileEntity, used by all booster blocks
 * 
 * @author katzenpapst
 */
public class TileEntityMothershipEngineBooster extends TileBaseUniversalElectrical implements IFluidHandler, ISidedInventory, IInventory {

	public static ResourceLocation topFallback = new ResourceLocation(Constants.ASSET_PREFIX, "textures/blocks/machine.png");

	public static ResourceLocation	sideFallback	= new ResourceLocation(Constants.ASSET_PREFIX, "textures/blocks/machine_side.png");
	protected final String			assetPrefix		= AmunRa.ASSETPREFIX;

	protected final String	assetPath		= "textures/blocks/";
	protected boolean		masterPresent	= false;

	protected int	masterX;
	protected int	masterY;
	protected int	masterZ;
	protected Class	masterType;

	public TileEntityMothershipEngineBooster(String tileName) {
		super(tileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canConnect(ForgeDirection direction, NetworkType type) {
		TileEntityMothershipEngineAbstract tile = this.getMasterTile();
		if (tile == null)
			return false;
		return tile.canConnect(direction, type);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		TileEntityMothershipEngineAbstract tile = this.getMasterTile();
		if (tile == null)
			return false;
		return tile.canDrain(from, fluid);
	}

	@Override
	public boolean canExtractItem(int slotID, ItemStack itemstack, int side) {
		TileEntityMothershipEngineAbstract tile = this.getMasterTile();
		if (tile == null)
			return false;
		return tile.canExtractItem(slotID, itemstack, side);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		TileEntityMothershipEngineAbstract tile = this.getMasterTile();
		if (tile == null)
			return false;
		return tile.canFill(from, fluid);
	}

	@Override
	public boolean canInsertItem(int slotID, ItemStack itemstack, int side) {
		TileEntityMothershipEngineAbstract tile = this.getMasterTile();
		if (tile == null)
			return false;
		return tile.canInsertItem(slotID, itemstack, side);
	}

	public void clearMaster() {
		masterPresent = false;
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		TileEntityMothershipEngineAbstract tile = this.getMasterTile();
		if (tile == null)
			return null;
		return tile.decrStackSize(slot, amount);
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		TileEntityMothershipEngineAbstract tile = this.getMasterTile();
		if (tile == null)
			return null;
		return tile.drain(from, resource, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		TileEntityMothershipEngineAbstract tile = this.getMasterTile();
		if (tile == null)
			return null;
		return tile.drain(from, maxDrain, doDrain);
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		TileEntityMothershipEngineAbstract tile = this.getMasterTile();
		if (tile == null)
			return 0;
		return tile.fill(from, resource, doFill);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		TileEntityMothershipEngineAbstract tile = this.getMasterTile();
		if (tile == null)
			return new int[] {};
		return tile.getAccessibleSlotsFromSide(side);
	}

	public ResourceLocation getBlockIconFromSide(int side) {

		// fallback
		if (side > 1)
			return sideFallback;
		else
			return topFallback;

	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound var1 = new NBTTagCompound();
		writeToNBT(var1);

		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, var1);
		// return new Packet132TileEntityDat(this.xCoord, this.yCoord, this.zCoord, 1, var1);
	}

	@Override
	public EnumSet<ForgeDirection> getElectricalInputDirections() {
		TileEntityMothershipEngineAbstract tile = this.getMasterTile();
		if (tile == null)
			return EnumSet.noneOf(ForgeDirection.class);
		// EnumSet.
		return tile.getElectricalInputDirections();
	}

	@Override
	public String getInventoryName() {
		// I'm not sure if it's even needed to do this, but...
		return GCCoreUtil.translate("tile.mothership.rocketJetEngine.name");
	}

	@Override
	public int getInventoryStackLimit() {
		TileEntityMothershipEngineAbstract tile = this.getMasterTile();
		if (tile == null)
			return 0;
		return tile.getInventoryStackLimit();
	}

	public Vector3int getMasterPosition() {
		return new Vector3int(masterX, masterY, masterZ);
	}

	public TileEntityMothershipEngineAbstract getMasterTile() {
		if (!this.masterPresent)
			return null;
		TileEntity tile = this.world.getTileEntity(masterX, masterY, masterZ);
		if (tile == null || !(tile instanceof TileEntityMothershipEngineAbstract)) {
			// oops
			this.masterPresent = false;
			return null;
		}
		return (TileEntityMothershipEngineAbstract) tile;
	}

	public int getMasterX() {
		return masterX;
	}

	public int getMasterY() {
		return masterY;
	}

	public int getMasterZ() {
		return masterZ;
	}

	/**
	 * Using the master coordinates, get a position where the next booster could be
	 * 
	 * @return
	 */
	public Vector3int getPossibleNextBooster() {
		if (!hasMaster())
			return null;
		if (this.xCoord == this.masterX) {
			if (this.zCoord < this.masterZ)
				return new Vector3int(xCoord, yCoord, zCoord - 1);
			else if (this.zCoord > this.masterZ)
				return new Vector3int(xCoord, yCoord, zCoord + 1);
			else
				return null;
		} else if (this.zCoord == this.masterZ) {
			if (this.xCoord < this.masterX)
				return new Vector3int(xCoord - 1, yCoord, zCoord);
			else if (this.xCoord > this.masterX)
				return new Vector3int(xCoord + 1, yCoord, zCoord);
			else
				return null;
		}
		return null;
	}

	@Override
	public float getProvide(ForgeDirection direction) {
		return 0;
	}

	@Override
	public float getRequest(ForgeDirection direction) {
		// not sure what this does
		TileEntityMothershipEngineAbstract tile = this.getMasterTile();
		if (tile == null)
			return 0F;
		return tile.getRequest(direction);
	}

	@Override
	public int getSizeInventory() {
		TileEntityMothershipEngineAbstract tile = this.getMasterTile();
		if (tile == null)
			return 0;
		return tile.getSizeInventory();
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		TileEntityMothershipEngineAbstract tile = this.getMasterTile();
		if (tile == null)
			return null;
		return tile.getStackInSlot(slot);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int wat) {
		TileEntityMothershipEngineAbstract tile = this.getMasterTile();
		if (tile == null)
			return null;
		return tile.getStackInSlotOnClosing(wat);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		TileEntityMothershipEngineAbstract tile = this.getMasterTile();
		if (tile == null)
			return null;
		return tile.getTankInfo(from);
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTierGC() {
		return this.tierGC;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	public boolean hasMaster() {
		// meh
		TileEntityMothershipEngineAbstract tile = this.getMasterTile();
		return tile != null;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		TileEntityMothershipEngineAbstract tile = this.getMasterTile();
		if (tile == null)
			return false;
		return tile.isItemValidForSlot(slot, stack);
	}

	public boolean isMaster(int x, int y, int z) {
		return masterPresent && x == masterX && y == masterY && z == masterZ;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		TileEntityMothershipEngineAbstract tile = this.getMasterTile();
		if (tile == null)
			return false;
		// I think it's better to calculate this here
		return this.world.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}

	public boolean isValidMaster(TileEntity tile) {
		if (!(tile instanceof TileEntityMothershipEngineAbstract))
			return false;
		return tile.getClass() == this.masterType;
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		readFromNBT(packet.func_148857_g());
	}

	@Override
	public void openInventory() {
	}

	@Override
	public float provideElectricity(ForgeDirection from, float request, boolean doProvide) {
		return 0.F;// do not provide
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		masterPresent = nbt.getBoolean("hasMaster");
		masterX = nbt.getInteger("masterX");
		masterY = nbt.getInteger("masterY");
		masterZ = nbt.getInteger("masterZ");
	}

	// Five methods for compatibility with basic electricity
	@Override
	public float receiveElectricity(ForgeDirection from, float receive, int tier, boolean doReceive) {
		TileEntityMothershipEngineAbstract tile = this.getMasterTile();
		if (tile == null)
			return 0F;
		return tile.receiveElectricity(from, receive, tier, doReceive);
	}

	@Override
	@RuntimeInterface(clazz = "cofh.api.energy.IEnergyReceiver", modID = "")
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		// forward this to the master, too
		TileEntityMothershipEngineAbstract tile = this.getMasterTile();
		if (tile == null)
			return 0;
		return tile.receiveEnergy(from, maxReceive, simulate);
	}

	public void reset() {
		masterPresent = false;
		this.markDirty();
		this.world.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		TileEntityMothershipEngineAbstract tile = this.getMasterTile();
		if (tile == null)
			return;
		tile.setInventorySlotContents(slot, stack);
	}

	public void setMaster(int x, int y, int z) {
		masterX = x;
		masterY = y;
		masterZ = z;
		masterPresent = true;
	}

	@Override
	public void setTierGC(int newTier) {
		this.tierGC = newTier;
	}

	/**
	 * Reset and update the master, if I have any
	 */
	public void updateMaster(boolean rightNow) {
		if (!masterPresent)
			return;

		TileEntity masterTile = world.getTileEntity(masterX, masterY, masterZ);
		if (masterTile == null || !(masterTile instanceof TileEntityMothershipEngineAbstract)) {
			// apparently we just lost our master?
			this.reset();
			return;
		}
		TileEntityMothershipEngineAbstract jetTile = (TileEntityMothershipEngineAbstract) masterTile;
		if (!jetTile.isPartOfMultiBlock(xCoord, yCoord, zCoord)) {
			this.reset();
			return;
		}

		if (rightNow) {
			jetTile.updateMultiblock();
		} else {
			jetTile.scheduleUpdate();
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("hasMaster", masterPresent);
		nbt.setInteger("masterX", masterX);
		nbt.setInteger("masterY", masterY);
		nbt.setInteger("masterZ", masterZ);
	}
}
