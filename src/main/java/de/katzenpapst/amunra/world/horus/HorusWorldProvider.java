package de.katzenpapst.amunra.world.horus;

import java.util.List;

import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.world.AmunraWorldChunkManager;
import de.katzenpapst.amunra.world.AmunraWorldProvider;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

public class HorusWorldProvider extends AmunraWorldProvider {

	@Override
	public boolean canRainOrSnow() {
		return false;
	}

	@Override
	public CelestialBody getCelestialBody() {
		return AmunRa.instance.planetHorus;
	}

	@Override
	public Class<? extends IChunkProvider> getChunkProviderClass() {
		return HorusChunkProvider.class;
	}

	@Override
	public long getDayLength() {
		return 52000L;
	}

	@Override
	public DimensionType getDimensionType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResourceLocation getDungeonChestType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDungeonSpacing() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Vector3 getFogColor() {
		return new Vector3(0, 0, 0);
	}

	@Override
	public double getMeteorFrequency() {
		return 1.5;
	}

	@Override
	protected float getRelativeGravity() {
		return 1.2F;
	}

	@Override
	public Vector3 getSkyColor() {

		return new Vector3(0, 0, 0);
	}

	@Override
	public float getSoundVolReductionAmount() {
		return 20;
	}

	@Override
	public List<Block> getSurfaceBlocks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getThermalLevelModifier() {
		// asteroids has a thermal modifier of -1.5
		return 3;
	}

	@Override
	public float getWindLevel() {
		return 0;
	}

	@Override
	public Class<? extends WorldChunkManager> getWorldChunkManagerClass() {
		return AmunraWorldChunkManager.class;
	}

	@Override
	public double getYCoordinateToTeleport() {

		return 1000;
	}

	@Override
	public boolean hasSunset() {
		return false;
	}

}
