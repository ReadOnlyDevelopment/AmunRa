package de.katzenpapst.amunra.world.maahes;

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

public class MaahesWorldProvider extends AmunraWorldProvider {

	@Override
	public boolean canRainOrSnow() {
		return false;
	}

	@Override
	public CelestialBody getCelestialBody() {
		return AmunRa.instance.moonMaahes;
	}

	@Override
	public Class<? extends IChunkProvider> getChunkProviderClass() {
		return MaahesChunkProvider.class;
	}

	@Override
	public long getDayLength() {
		return 16000L;
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
		return new Vector3(0.5569, 1, 0.851);
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
		return 0.7F;
	}

	@Override
	public Vector3 getSkyColor() {
		return new Vector3(109.0F / 255.0F, 196.0F / 255.0F, 167.0F / 255.0F);
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
