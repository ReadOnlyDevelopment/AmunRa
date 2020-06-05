package de.katzenpapst.amunra.world.horus;

import java.util.ArrayList;
import java.util.List;

import de.katzenpapst.amunra.old.block.ARBlocks;
import de.katzenpapst.amunra.world.AmunraChunkProvider;
import de.katzenpapst.amunra.world.mapgen.pyramid.BossRoom;
import de.katzenpapst.amunra.world.mapgen.pyramid.ChestRoom;
import de.katzenpapst.amunra.world.mapgen.pyramid.PitRoom;
import de.katzenpapst.amunra.world.mapgen.pyramid.PyramidGenerator;
import de.katzenpapst.amunra.world.mapgen.pyramid.PyramidRoom;
import de.katzenpapst.amunra.world.mapgen.volcano.VolcanoGenerator;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedCreeper;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedZombie;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;

public class HorusChunkProvider extends AmunraChunkProvider {

	protected final BlockMetaPair	stoneBlock	= new BlockMetaPair(Blocks.obsidian, (byte) 0);
	protected PyramidGenerator		pyramid		= new PyramidGenerator();
	private VolcanoGenerator		volcanoGen;

	public HorusChunkProvider(World par1World, long seed, boolean mapFeaturesEnabled) {
		super(par1World, seed, mapFeaturesEnabled);
		pyramid.setFillMaterial(ARBlocks.blockBasaltBrick);
		pyramid.setFloorMaterial(ARBlocks.blockSmoothBasalt);
		pyramid.setWallMaterial(ARBlocks.blockObsidianBrick);
		pyramid.addComponentType(ChestRoom.class, 0.25F);
		pyramid.addComponentType(PitRoom.class, 0.25F);
		pyramid.addComponentType(PyramidRoom.class, 0.5F);
		pyramid.addMainRoomType(BossRoom.class, 1.0F);

		volcanoGen = new VolcanoGenerator(new BlockMetaPair(Blocks.lava, (byte) 0), stoneBlock, stoneBlock, 15, true);
	}

	@Override
	protected BiomeDecoratorSpace getBiomeGenerator() {
		return new HorusBiomeDecorator();
	}

	@Override
	protected BiomeGenBase[] getBiomesForGeneration() {
		return new BiomeGenBase[] { BiomeGenBase.desert };
	}

	@Override
	protected SpawnListEntry[] getCreatures() {
		return new SpawnListEntry[] {};
	}

	@Override
	protected BlockMetaPair getDirtBlock() {
		return ARBlocks.blockObsidiGravel;
	}

	@Override
	protected BlockMetaPair getGrassBlock() {
		return ARBlocks.blockObsidiSand;
	}

	@Override
	public double getHeightModifier() {
		return 20;
	}

	@Override
	protected SpawnListEntry[] getMonsters() {
		SpawnListEntry skele = new SpawnListEntry(EntityEvolvedSkeleton.class, 100, 4, 4);
		SpawnListEntry creeper = new SpawnListEntry(EntityEvolvedCreeper.class, 100, 4, 4);
		SpawnListEntry zombie = new SpawnListEntry(EntityEvolvedZombie.class, 100, 4, 4);

		return new SpawnListEntry[] { skele, creeper, zombie };
	}

	@Override
	public double getMountainHeightModifier() {
		return 60;
	}

	@Override
	protected int getSeaLevel() {
		return 64;
	}

	@Override
	public double getSmallFeatureHeightModifier() {
		return 40;
	}

	@Override
	protected BlockMetaPair getStoneBlock() {
		return stoneBlock;
	}

	@Override
	public double getValleyHeightModifier() {
		// TODO Auto-generated method stub
		return 60;
	}

	@Override
	protected List<MapGenBaseMeta> getWorldGenerators() {
		ArrayList<MapGenBaseMeta> list = new ArrayList<MapGenBaseMeta>();
		list.add(pyramid);
		list.add(volcanoGen);
		return list;
	}

	@Override
	public void onChunkProvide(int cX, int cZ, Block[] blocks, byte[] metadata) {

	}

	@Override
	public void onPopulate(IChunkProvider provider, int cX, int cZ) {

	}

	@Override
	public void populate(IChunkProvider par1IChunkProvider, int chunkX, int chunkZ) {
		super.populate(par1IChunkProvider, chunkX, chunkZ);

		this.pyramid.populate(this, world, chunkX, chunkZ);
		// this.pyramid.populate(this, world, chunkX, chunkZ);

		// this.villageTest.generateStructuresInChunk(this.world, this.rand, par2, par3);
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onChunkProvide(int cX, int cZ, ChunkPrimer primer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPopulate(int cX, int cZ) {
		// TODO Auto-generated method stub

	}

}
