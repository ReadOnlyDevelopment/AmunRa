package de.katzenpapst.amunra.world.mapgen;

import java.util.HashMap;
import java.util.Random;

import de.katzenpapst.amunra.helper.CoordHelper;
import de.katzenpapst.amunra.world.mapgen.populator.AbstractPopulator;
import de.katzenpapst.amunra.world.mapgen.populator.SpawnEntity;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;

abstract public class BaseStructureStart extends BaseStructureComponent {

	public class PopulatorByChunkMap extends HashMap<Long, PopulatorMap> {
	}

	public class PopulatorMap extends HashMap<BlockVec3, AbstractPopulator> {
	}

	protected PopulatorByChunkMap populatorsByChunk;

	protected int	chunkX;
	protected int	chunkZ;

	protected Random rand;

	protected World world;

	// coords relative to the
	protected int startX;
	// protected int startY;
	protected int startZ;

	public BaseStructureStart(World world, int chunkX, int chunkZ, Random rand) {

		this.chunkX = chunkX;
		this.chunkZ = chunkZ;

		this.world = world;

		this.rand = rand;

		this.startX = this.rand.nextInt(16);
		this.startZ = this.rand.nextInt(16);

		// int startBlockX = chunkX*16 + this.startX;
		// int startBlockZ = chunkZ*16 + this.startZ;

		populatorsByChunk = new PopulatorByChunkMap();
	}

	public void addPopulator(AbstractPopulator p) {
		// ok I can't do that
		// this.world.getChunkFromBlockCoords(p_72938_1_, p_72938_2_)
		int chunkX = CoordHelper.blockToChunk(p.getX());// p.getX() >> 4;
		int chunkZ = CoordHelper.blockToChunk(p.getZ());

		// p_72938_1_ >> 4, p_72938_2_ >>
		// 16

		Long chunkKey = Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ));
		if (!populatorsByChunk.containsKey(chunkKey)) {
			FMLLog.info("Cannot add populator for " + chunkX + "/" + chunkZ + ", offender: " + p.getClass().getCanonicalName() + ". Probably it's the wrong chunk");
			return;
		}
		PopulatorMap curMap = populatorsByChunk.get(chunkKey);

		BlockVec3 key = p.getBlockVec3();
		if (curMap.containsKey(key)) {
			FMLLog.info("Cannot add populator for " + key.toString() + ", offender: " + p.getClass().getCanonicalName());
			return;
		}
		// pack the coords
		curMap.put(key, p);
	}

	/**
	 * This should be overridden, but then called before anything else happens
	 */
	@Override
	public boolean generateChunk(int chunkX, int chunkZ, Block[] arrayOfIDs, byte[] arrayOfMeta) {
		preparePopulatorListForChunk(chunkX, chunkZ);

		return true;
	}

	public World getWorld() {
		return world;
	}

	public int getWorldGroundLevel() {
		// ((ChunkProviderSpace)world.getChunkProvider()).g
		// NO IDEA
		return world.provider.getAverageGroundLevel();
	}

	public void populateChunk(World world, int chunkX, int chunkZ) {

		Long chunkKey = Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ));
		if (!populatorsByChunk.containsKey(chunkKey)) {
			FMLLog.info("No populator list for chunk " + chunkX + "/" + chunkZ);
			return;
		}
		PopulatorMap curMap = populatorsByChunk.get(chunkKey);
		populatorsByChunk.remove(chunkKey);// remove it already, at this point, it's too late

		for (AbstractPopulator p : curMap.values()) {
			if (!p.populate(world)) {
				FMLLog.info("Populator " + p.getClass().getCanonicalName() + " failed...");
			}
		}

		curMap.clear();// I hope that's enough of a hint to make java delete this stuff

	}

	protected void preparePopulatorListForChunk(int chunkX, int chunkZ) {
		Long key = Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ));

		if (populatorsByChunk.containsKey(key)) {
			// this is bad, this shouldn't happen
			FMLLog.info("Tried to prepare populator list for chunk " + chunkX + "/" + chunkZ + ". This could mean that the chunk is being generated twice.");
			return;
		}

		populatorsByChunk.put(key, new PopulatorMap());
	}

	public void spawnLater(Entity ent, int x, int y, int z) {
		SpawnEntity p = new SpawnEntity(x, y, z, ent);
		addPopulator(p);
	}

}
