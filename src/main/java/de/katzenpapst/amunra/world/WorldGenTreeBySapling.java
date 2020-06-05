package de.katzenpapst.amunra.world;

import java.util.Random;

import de.katzenpapst.amunra.old.block.BlockBasicMeta;
import de.katzenpapst.amunra.old.block.bush.AbstractSapling;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenTreeBySapling extends WorldGenAbstractTree {
	/** True if this tree should grow Vines. */
	// private final boolean vinesGrow;
	/** The metadata value of the wood to use in tree generation. */
	// private final int metaWood;
	/** The metadata value of the leaves to use in tree generation. */
	// private final int metaLeaves;

	protected AbstractSapling sapling;

	// protected final BlockMetaPair wood;
	// protected final BlockMetaPair leaves;
	// protected final BlockMetaPair vines;
	// protected final Block

	public WorldGenTreeBySapling(boolean doBlockNotify, int minTreeHeight, AbstractSapling sapling) {
		super(doBlockNotify);
		this.sapling = sapling;
		// this.wood = wood;
		// this.leaves = leaves;
		// this.vines = vines;
		// this.vinesGrow = vines != null;
	}

	public WorldGenTreeBySapling(boolean doBlockNotify, int minTreeHeight, BlockMetaPair sapling) {
		super(doBlockNotify);
		this.sapling = (AbstractSapling) ((BlockBasicMeta) sapling.getBlock()).getSubBlock(sapling.getMetadata());
		// this.wood = wood;
		// this.leaves = leaves;
		// this.vines = vines;
		// this.vinesGrow = vines != null;
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		return this.sapling.generate(world, rand, x, y, z, false);
	}
}
