package de.katzenpapst.amunra.old.block;

import micdoodle8.mods.galacticraft.api.block.IDetectableResource;
import micdoodle8.mods.galacticraft.api.block.IPlantableBlock;
import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SubBlock extends Block implements IDetectableResource, IPlantableBlock, ITerraformableBlock {
	/*
	 * public float hardness = 1.5F; public float resistance = 10.0F; public String name = null; public String texture = null; public String tool = "pickaxe"; /** Wood: 0 Stone: 1 Iron: 2 Diamond: 3 Gold: 0 / public int miningLevel = 0; public boolean isOpaque = false; public int lightOpacity = 0; public int lightValue = 0; public Material material = Material.rock; public SoundType soundType = Block.soundTypeStone;
	 */

	protected int		sbHarvestLevel	= -1;
	protected String	sbHarvestTool	= "";

	// because blockName is private without getters...
	protected String blockNameFU;

	protected IMetaBlock parent = null;

	public SubBlock(String name, String texture) {
		super(Material.ROCK);
		init(name, texture, "pickaxe", 1, 1.5F, 10.0F);
	}

	public SubBlock(String name, String texture, String tool, int harvestLevel) {
		super(Material.ROCK);
		init(name, texture, tool, harvestLevel, 1.5F, 10.0F);
	}

	public SubBlock(String name, String texture, String tool, int harvestLevel, float hardness, float resistance) {
		super(Material.ROCK);
		init(name, texture, tool, harvestLevel, hardness, resistance);
	}

	/**
	 * Return false to prevent wrench rotation, or even mining
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public boolean canBeMoved(World world, int x, int y, int z) {
		return true;
	}

	public TileEntity createTileEntity(World world, int metadata) {
		return null;
	}

	/**
	 * if true, multiblock does the stuff itself
	 * 
	 * @return
	 */
	public boolean dropsSelf() {
		return true;
	}

	/**
	 * Gets the localized name of this block. Used for the statistics page.
	 */
	@Override
	public String getLocalizedName() {
		return blockNameFU; // multiblock does that
	}

	public IMetaBlock getParent() {
		return this.parent;
	}

	/**
	 * Returns the unlocalized name of the block WITHOUT "tile." appended to the front.
	 */
	@Override
	public String getUnlocalizedName() {
		return blockNameFU;
	}

	protected void init(String name, String texture, String harvestTool, int havestLevel, float hardness, float resistance) {
		blockNameFU = name;
		setHarvestLevel(harvestTool, havestLevel);
		setHardness(hardness);
		setResistance(resistance);
		// this.name = name;
		// this.texture = texture;
	}

	@Override
	public boolean isPlantable(IBlockState state) {
		return false;
	}

	@Override
	public boolean isTerraformable(World world, BlockPos pos) {
		return false;
	}

	@Override
	public boolean isValueable(IBlockState metadata) {
		return false;
	}

//    /**
//     * Queries the class of tool required to harvest this block, if null is returned
//     * we assume that anything can harvest this block.
//     *
//     * @param metadata
//     * @return
//     */
//    @Override
//    public String getHarvestTool(int metadata)
//    {
//        return sbHarvestTool;
//    }

//    /**
//     * Queries the harvest level of this item stack for the specifred tool class,
//     * Returns -1 if this tool is not of the specified type
//     *
//     * @param stack This item stack instance
//     * @return Harvest level, or -1 if not the specified tool type.
//     */
//    @Override
//    public int getHarvestLevel(int metadata)
//    {
//        return sbHarvestLevel;
//    }

//    /**
//     * Sets or removes the tool and level required to harvest this block.
//     *
//     * @param toolClass Class
//     * @param level Harvest level:
//     *     Wood:    0
//     *     Stone:   1
//     *     Iron:    2
//     *     Diamond: 3
//     *     Gold:    0
//     * @param metadata The specific metadata to set
//     */
//    @Override
//    public void setHarvestLevel(String toolClass, int level, int metadata)
//    {
//        setHarvestLevel(toolClass, level);
//    }

	@Override
	public int requiredLiquidBlocksNearby() {
		return 4;
	}

	public SubBlock setHarvestInfo(String toolClass, int level) {
		this.setHarvestLevel(toolClass, level);
		return this;
	}

	/**
	 * Sets or removes the tool and level required to harvest this block.
	 *
	 * @param toolClass Class
	 * @param level     Harvest level: Wood: 0 Stone: 1 Iron: 2 Diamond: 3 Gold: 0
	 */
	@Override
	public void setHarvestLevel(String toolClass, int level) {
		this.sbHarvestLevel = level;
		this.sbHarvestTool = toolClass;
	}

	public void setParent(IMetaBlock parent) {
		if (parent instanceof Block) {
			this.parent = parent;
		}
		// else throw some shit?
	}

//    @Override
//    public void breakBlock(World world, int x0, int y0, int z0, Block var5, int var6)
//    {
//        // NOT calling super here, the metablock is doing that part
//    }
}
