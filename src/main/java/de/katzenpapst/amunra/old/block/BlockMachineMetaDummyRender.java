package de.katzenpapst.amunra.old.block;

import de.katzenpapst.amunra.AmunRa;
import micdoodle8.mods.galacticraft.api.block.IPartialSealableBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMachineMetaDummyRender extends BlockMachineMeta implements IPartialSealableBlock {

	public BlockMachineMetaDummyRender(String name, Material material) {
		super(name, material);
		// TODO Auto-generated constructor stub
	}

	public BlockMachineMetaDummyRender(String name, Material material, int numSubBlocks) {
		super(name, material, numSubBlocks);
		// TODO Auto-generated constructor stub
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return AmunRa.dummyRendererId;
	}

	@Override
	public boolean isBlockNormalCube() {
		return false;
	}

	@Override
	public boolean isNormalCube() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isSealed(World world, BlockPos pos, EnumFacing direction) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSealed(World world, int x, int y, int z, ForgeDirection direction) {
		return true;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

}
