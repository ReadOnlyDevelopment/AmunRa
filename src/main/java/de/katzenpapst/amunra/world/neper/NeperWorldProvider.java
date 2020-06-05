package de.katzenpapst.amunra.world.neper;

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

public class NeperWorldProvider extends AmunraWorldProvider {

	@Override
	public boolean canRainOrSnow() {
		return false;
	}

	@Override
	public CelestialBody getCelestialBody() {
		return AmunRa.instance.moonNeper;
	}

	@Override
	public Class<? extends IChunkProvider> getChunkProviderClass() {
		return NeperChunkProvider.class;
	}

	@Override
	public long getDayLength() {
		return 18000L;
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
		float f3 = 0.7529412F;
		float f4 = 0.84705883F;
		float f5 = 1.0F;
		/*
		 * f3 *= f2 * 0.94F + 0.06F; f4 *= f2 * 0.94F + 0.06F; f5 *= f2 * 0.91F + 0.09F;
		 */
		// return Vec3.createVectorHelper((double)f3, (double)f4, (double)f5);
		return new Vector3(f3, f4, f5);
	}

	/**
	 * Determines the rate to spawn meteors in this planet. Lower means MORE meteors.
	 * <p/>
	 * Typical value would be about 7. Return 0 for no meteors.
	 *
	 * @return
	 */
	@Override
	public double getMeteorFrequency() {
		return 7;
	}

	@Override
	protected float getRelativeGravity() {
		return 1F;
	}

	@Override
	public Vector3 getSkyColor() {
		return new Vector3(0.5, 0.75, 1);
	}

	@Override
	public float getSoundVolReductionAmount() {
		return 1;
	}

	@Override
	public List<Block> getSurfaceBlocks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getThermalLevelModifier() {
		return 0;
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
		return 800;
	}

	@Override
	public boolean hasSunset() {
		return true;
	}

}
