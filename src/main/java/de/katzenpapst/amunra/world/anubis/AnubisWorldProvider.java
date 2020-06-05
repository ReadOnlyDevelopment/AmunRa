package de.katzenpapst.amunra.world.anubis;

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

public class AnubisWorldProvider extends AmunraWorldProvider {

	@Override
	public boolean canRainOrSnow() {
		return false;
	}

	@Override
	public CelestialBody getCelestialBody() {
		return AmunRa.instance.planetAnubis;
	}

	@Override
	public Class<? extends IChunkProvider> getChunkProviderClass() {
		return AnubisChunkProvider.class;
	}

	@Override
	public long getDayLength() {
		return 32000L;
	}

	@Override
	public Vector3 getFogColor() {
		return new Vector3(0, 0, 0);
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
		return 2;
	}

	@Override
	protected float getRelativeGravity() {
		return 0.25F;
	}

	@Override
	public Vector3 getSkyColor() {
		return new Vector3(0, 0, 0);
	}

	/**
	 * Changes volume of sounds on this planet. You should be using higher values for thin atmospheres and high values for dense atmospheres
	 *
	 * @return Sound reduction divisor. Value of 10 will make sounds ten times more quiet. Value of 0.1 will make sounds 10 times louder. Be careful with the values you choose!
	 */
	@Override
	public float getSoundVolReductionAmount() {
		return 20;
	}

	/**
	 * This value will affect player's thermal level, damaging them if it reaches too high or too low.
	 *
	 * @return Positive integer for hot celestial bodies, negative for cold. Zero for neutral
	 */
	@Override
	public float getThermalLevelModifier() {
		return -10;
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
		return false;
	}

	@Override
	public boolean isSkyColored() {
		return false;
	}

	@Override
	public int getDungeonSpacing() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ResourceLocation getDungeonChestType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Block> getSurfaceBlocks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DimensionType getDimensionType() {
		// TODO Auto-generated method stub
		return null;
	}

}
