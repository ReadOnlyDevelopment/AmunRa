package de.katzenpapst.amunra.old.block.machine.mothershipEngine;

import java.util.Random;

import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.GuiIds;
import de.katzenpapst.amunra.old.item.ARItems;
import de.katzenpapst.amunra.old.item.ItemDamagePair;
import de.katzenpapst.amunra.old.tile.TileEntityMothershipEngineAbstract;
import de.katzenpapst.amunra.old.tile.TileEntityMothershipEngineIon;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MothershipEngineJetIon extends MothershipEngineJetBase {

	protected ItemDamagePair item = null;

	public MothershipEngineJetIon(String name, String texture, String iconTexture) {
		super(name, texture, iconTexture);
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileEntityMothershipEngineIon();
	}

	@Override
	public int damageDropped(int meta) {
		return item.getDamage();
	}

	@Override
	protected ItemDamagePair getItem() {
		if (item == null) {
			item = ARItems.jetItemIon;
		}
		return item;
	}

	@Override
	public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
		return item.getItem();
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		/**
		 * Returns whether or not this bed block is the head of the bed.
		 */
		return item.getItem();
	}

	@Override
	protected TileEntityMothershipEngineIon getMyTileEntity(World world, int x, int y, int z) {
		TileEntity t = world.getTileEntity(x, y, z);
		if (t == null || !(t instanceof TileEntityMothershipEngineIon))
			return null;
		return (TileEntityMothershipEngineIon) t;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return AmunRa.dummyRendererId;
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	/**
	 * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
	 */
	@Override
	public int onBlockPlaced(World w, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		return meta;
	}

	@Override
	public boolean onMachineActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ) {
		// do the isRemote thing here, too?
		entityPlayer.openGui(AmunRa.instance, GuiIds.GUI_MS_ION_ENGINE, world, x, y, z);
		return true;
		// return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		TileEntity leTile = world.getTileEntity(x, y, z);
		if (leTile instanceof TileEntityMothershipEngineAbstract) {
			((TileEntityMothershipEngineAbstract) leTile).scheduleUpdate();
			// world.markBlockForUpdate(x, y, z);
		}
	}

	@Override
	public boolean onUseWrench(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ) {
		// TODO rotate the tile entity
		return false;
	}

}
