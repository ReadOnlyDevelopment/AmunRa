package de.katzenpapst.amunra.old.block.machine;

import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.GuiIds;
import de.katzenpapst.amunra.helper.CoordHelper;
import de.katzenpapst.amunra.old.block.BlockMachineMeta;
import de.katzenpapst.amunra.old.tile.TileEntityMothershipController;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMothershipController extends AbstractBlockMothershipRestricted {

	/**
	 * @param side
	 * @return
	 */
	public static boolean isSideEnergyOutput(int side) {
		// wait, wat?
		return false;
	}

	protected final String frontTexture;

	private IIcon iconFront = null;

	public BlockMothershipController(String name, String frontTexture, String sideTexture) {
		super(name, sideTexture);
		this.frontTexture = frontTexture;
	}

	/**
	 * Called throughout the code as a replacement for ITileEntityProvider.createNewTileEntity Return the same thing you would from that function. This will fall back to ITileEntityProvider.createNewTileEntity(World) if this block is a ITileEntityProvider
	 *
	 * @param metadata The Metadata of the current block
	 * @return A instance of a class extending TileEntity
	 */
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileEntityMothershipController();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		int realMeta = ((BlockMachineMeta) this.parent).getRotationMeta(meta);
		// we have the front thingy at front.. but what is front?
		// east is the output
		// I think front is south
		ForgeDirection front = CoordHelper.rotateForgeDirection(ForgeDirection.SOUTH, realMeta);
		// ForgeDirection output = CoordHelper.rotateForgeDirection(ForgeDirection.EAST, realMeta);

		if (side == front.ordinal())
			return this.iconFront;

		return this.blockIcon;

	}

	@Override
	protected void openGui(World world, int x, int y, int z, EntityPlayer entityPlayer) {
		entityPlayer.openGui(AmunRa.instance, GuiIds.GUI_MOTHERSHIPCONTROLLER, world, x, y, z);
	}

	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		super.registerBlockIcons(par1IconRegister);
		iconFront = par1IconRegister.registerIcon(frontTexture);
		// this.blockIcon = iconFront;
	}
}
