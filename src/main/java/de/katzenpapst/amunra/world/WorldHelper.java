package de.katzenpapst.amunra.world;

import de.katzenpapst.amunra.vec.Vector3int;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class WorldHelper {

	public static void dropItemInWorld(World world, ItemStack stack, double x, double y, double z) {
		dropItemInWorld(world, stack, x, y, z, 0, 0, 0);
	}

	/**
	 * Drop entity in world, copy over tag compound, too
	 * 
	 * @param world
	 * @param stack
	 * @param x
	 * @param y
	 * @param z
	 * @param motionX
	 * @param motionY
	 * @param motionZ
	 */
	public static void dropItemInWorld(World world, ItemStack stack, double x, double y, double z, double motionX, double motionY, double motionZ) {
		EntityItem itemEntity = new EntityItem(world, x, y, z, new ItemStack(stack.getItem(), stack.stackSize, stack.getItemDamage()));

		if (stack.hasTagCompound()) {
			itemEntity.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
		}

		itemEntity.motionX = motionX;
		itemEntity.motionY = motionY;
		itemEntity.motionZ = motionZ;
		world.spawnEntityInWorld(itemEntity);
	}

	public static void dropItemInWorld(World world, ItemStack stack, Entity atEntity) {
		dropItemInWorld(world, stack, atEntity.posX, atEntity.posY, atEntity.posZ, 0, 0, 0);
	}

	public static BlockMetaPair getBlockMetaPair(World world, int x, int y, int z) {
		return new BlockMetaPair(world.getBlock(x, y, z), (byte) world.getBlockMetadata(x, y, z));
	}

	public static Vector3int getHighestNonEmptyBlock(World world, int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {

		for (int y = maxY; y >= minY; y--) {
			for (int x = minX; x <= maxX; x++) {
				for (int z = minZ; z <= maxZ; z++) {
					if (!isNonSolid(world, x, y, z))
						return new Vector3int(x, y, z);
				}
			}
		}

		return null;
	}

	public static boolean isBlockMetaPair(World world, int x, int y, int z, BlockMetaPair bmp) {
		return world.getBlock(x, y, z) == bmp.getBlock() && world.getBlockMetadata(x, y, z) == bmp.getMetadata();
	}

	/**
	 * Checks if given block is safe to place the player
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static boolean isNonSolid(World world, int x, int y, int z) {
		Block b = world.getBlock(x, y, z);

		// so apparently block.getBlocksMovement does the opposite of what one might expect...

		return b.isAir(world, x, y, z) || b.getBlocksMovement(world, x, y, z) && !b.getMaterial().isLiquid() && !b.getMaterial().isSolid();
	}

	/**
	 * Returns true if the given block can be walked through
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static boolean isSolid(World world, int x, int y, int z) {
		return isSolid(world, x, y, z, false);
	}

	/**
	 * Returns true if the given block can be walked through. Will probably return false for fluids, too
	 *
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param checkTop
	 * @return
	 */
	public static boolean isSolid(World world, int x, int y, int z, boolean checkTop) {
		Block b = world.getBlock(x, y, z);
		if (checkTop)
			return world.doesBlockHaveSolidTopSurface(world, x, y, z);
		// getBlocksMovement returns true when the block does NOT block movement...
		return !b.getBlocksMovement(world, x, y, z) && b.getMaterial().isSolid();
	}

	public static void setBlockIfFree(World world, int x, int y, int z, Block block, int meta) {
		Block old = world.getBlock(x, y, z);
		if (old == Blocks.air) {
			// System.out.println("setting "+x+"/"+y+"/"+z+" on fire");
			world.setBlock(x, y, z, block, meta, 3);
		}
	}

	/**
	 * Attempts to ignite the block at the given position from the given direction
	 *
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param fromX
	 * @param fromY
	 * @param fromZ
	 */
	public static void setFireToBlock(World world, int x, int y, int z, double fromX, double fromY, double fromZ) {
		double deltaX = x + 0.5 - fromX;
		double deltaY = y + 0.5 - fromY;
		double deltaZ = z + 0.5 - fromZ;

		double deltaXabs = Math.abs(deltaX);
		double deltaYabs = Math.abs(deltaY);
		double deltaZabs = Math.abs(deltaZ);

		if (deltaXabs > deltaYabs) {
			if (deltaXabs > deltaZabs) {
				if (deltaX < 0) {
					setBlockIfFree(world, x + 1, y, z, Blocks.fire, 0);
				} else {
					setBlockIfFree(world, x - 1, y, z, Blocks.fire, 0);
				}
			} else {
				if (deltaZ < 0) {
					setBlockIfFree(world, x, y, z + 1, Blocks.fire, 0);
				} else {
					setBlockIfFree(world, x, y, z - 1, Blocks.fire, 0);
				}
			}
		} else {
			if (deltaYabs > deltaZabs) {
				if (deltaY < 0) {
					setBlockIfFree(world, x, y + 1, z, Blocks.fire, 0);
				} else {
					setBlockIfFree(world, x, y - 1, z, Blocks.fire, 0);
				}
			} else {
				if (deltaZ < 0) {
					setBlockIfFree(world, x, y, z + 1, Blocks.fire, 0);
				} else {
					setBlockIfFree(world, x, y, z - 1, Blocks.fire, 0);
				}
			}
		}
	}
}
