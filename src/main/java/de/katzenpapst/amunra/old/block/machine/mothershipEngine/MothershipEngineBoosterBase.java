package de.katzenpapst.amunra.old.block.machine.mothershipEngine;

import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.GuiIds;
import de.katzenpapst.amunra.old.block.machine.AbstractBlockMothershipRestricted;
import de.katzenpapst.amunra.old.tile.TileEntityMothershipEngineAbstract;
import de.katzenpapst.amunra.old.tile.TileEntityMothershipEngineBooster;
import de.katzenpapst.amunra.vec.Vector3int;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MothershipEngineBoosterBase extends AbstractBlockMothershipRestricted {

	protected String	activeTextureName;
	protected IIcon		activeBlockIcon;

	public MothershipEngineBoosterBase(String name, String texture, String activeTexture) {
		super(name, texture);
		activeTextureName = activeTexture;
	}

	public MothershipEngineBoosterBase(String name, String texture, String activeTexture, String tool, int harvestLevel) {
		super(name, texture, tool, harvestLevel);
		activeTextureName = activeTexture;
	}

	public MothershipEngineBoosterBase(String name, String texture, String activeTexture, String tool, int harvestLevel, float hardness, float resistance) {
		super(name, texture, tool, harvestLevel, hardness, resistance);
		activeTextureName = activeTexture;
	}

	@Override
	public boolean canBeMoved(World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te == null || !(te instanceof TileEntityMothershipEngineBooster))
			return true;
		TileEntityMothershipEngineAbstract master = ((TileEntityMothershipEngineBooster) te).getMasterTile();
		return master == null || !master.isInUse();
	}

	// TileEntityMothershipEngineBooster.java
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileEntityMothershipEngineBooster();
	}

	public ResourceLocation getBoosterTexture() {
		return new ResourceLocation(AmunRa.ASSETPREFIX, "textures/blocks/jet-base.png");
	}

	/**
	 * Gets the block's texture. Args: side, meta
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (side <= 1)
			return this.blockIcon;
		return activeBlockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return AmunRa.msBoosterRendererId;
	}

	@Override
	public String getShiftDescription(int meta) {
		return GCCoreUtil.translate("tile.mothershipEngineRocket.description");
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public boolean isBlockNormalCube() {
		return true;
	}

	@Override
	public boolean isNormalCube() {
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return true;
	}

	@Override
	public boolean onMachineActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ) {
		TileEntity leTile = world.getTileEntity(x, y, z);
		if (leTile == null || !(leTile instanceof TileEntityMothershipEngineBooster))
			return false;
		TileEntityMothershipEngineBooster tile = (TileEntityMothershipEngineBooster) leTile;

		if (tile.hasMaster())
			return super.onMachineActivated(world, x, y, z, entityPlayer, side, hitX, hitY, hitZ);
		return false;
	}

	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block block) {
		// these are MY coords
		TileEntity leTile = w.getTileEntity(x, y, z);
		if (leTile == null)
			return;

		if (leTile instanceof TileEntityMothershipEngineAbstract) {
			((TileEntityMothershipEngineAbstract) leTile).scheduleUpdate();
		} else if (leTile instanceof TileEntityMothershipEngineBooster) {
			((TileEntityMothershipEngineBooster) leTile).updateMaster(false);
			// attept to continue the process
			// find next
			Vector3int pos = ((TileEntityMothershipEngineBooster) leTile).getPossibleNextBooster();
			if (pos != null) {
				w.notifyBlockOfNeighborChange(pos.x, pos.y, pos.z, ((TileEntityMothershipEngineBooster) leTile).blockType);
			}
		}
	}

	@Override
	protected void openGui(World world, int x, int y, int z, EntityPlayer entityPlayer) {
		// try this
		if (world.isRemote)
			return;
		TileEntity leTile = world.getTileEntity(x, y, z);
		if (leTile != null) {
			TileEntityMothershipEngineBooster tile = (TileEntityMothershipEngineBooster) leTile;
			Vector3int pos = tile.getMasterPosition();

			entityPlayer.openGui(AmunRa.instance, GuiIds.GUI_MS_ROCKET_ENGINE, world, pos.x, pos.y, pos.z);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		this.activeBlockIcon = reg.registerIcon(this.activeTextureName);
	}

	@Override
	@Deprecated
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z) {
		if (this.canBeMoved(world, x, y, z))
			return super.removedByPlayer(world, player, x, y, z);
		return false;
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
		return removedByPlayer(world, player, x, y, z);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

}
