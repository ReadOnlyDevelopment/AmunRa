package de.katzenpapst.amunra.astronomy;

import java.util.ArrayList;

import javax.annotation.Nullable;

import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Moon;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.Satellite;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.galaxies.Star;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.AtmosphereInfo;
import micdoodle8.mods.galacticraft.api.world.EnumAtmosphericGas;
import micdoodle8.mods.galacticraft.api.world.ITeleportType;
import micdoodle8.mods.galacticraft.core.world.gen.BiomeOrbit;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;

public class AstroBuilder {
	
	private static String modId;


	/**
	 * Instantiates a new astro builder.
	 */
	public AstroBuilder(String modId) {
		this.setModId(modId);
	}


	/**
	 * builds solar system.
	 *
	 * @param name     the name
	 * @param galaxy   the galaxy
	 * @param pos      the pos
	 * @param starname the starname
	 * @param size     the size
	 * @return the solar system
	 */
	public static SolarSystem buildSolarSystem( String name, String galaxy, Vector3 pos, String starname,
			float size) {
		SolarSystem body = new SolarSystem(name, galaxy);
		body.setMapPosition(pos);
		Star main = new Star(starname);
		main.setParentSolarSystem(body);
		main.setTierRequired(-1);
		main.setRelativeSize(size);
		main.setBodyIcon(
				new ResourceLocation(getModId(), "textures/celestialbodies/" + name + "/" + starname + ".png"));
		body.setMainStar(main);
		return body;
	}

	/**
	 * builds planet.
	 *
	 * @param system             the system
	 * @param name               the name
	 * @param prefix             the prefix
	 * @param provider           the provider
	 * @param dimID              the dim ID
	 * @param tier               the tier
	 * @param phase              the phase
	 * @param size               the size
	 * @param distancefromcenter the distancefromcenter
	 * @param relativetime       the relativetime
	 * @param biome              the biome
	 * @return the planet
	 */
	public static Planet buildPlanet(SolarSystem system, String name,
			Class<? extends WorldProvider> provider, int dimID, int tier, float phase, float size,
			float distancefromcenter, float relativetime, ArrayList<Biome> biome) {
		Planet body = (new Planet(name)).setParentSolarSystem(system);
		body.setRingColorRGB(0.1F, 0.9F, 0.6F);
		body.setPhaseShift(phase);
		body.setRelativeSize(size);
		body.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(distancefromcenter, distancefromcenter));
		body.setRelativeOrbitTime(relativetime);
		body.setBodyIcon(new ResourceLocation(getModId(), "textures/celestialbodies/" + name + ".png"));
		if (provider != null) {
			body.setTierRequired(tier);
			body.setDimensionInfo(dimID, provider);
			if (biome != null)
				for(Biome b : biome) {
					body.setBiomeInfo(b);
				}
		}
		return body;
	}

	/**
	 * builds moon.
	 *
	 * @param parent             the parent
	 * @param name               the name
	 * @param prefix             the prefix
	 * @param provider           the provider
	 * @param dimID              the dim ID
	 * @param tier               the tier
	 * @param phase              the phase
	 * @param size               the size
	 * @param distancefromcenter the distancefromcenter
	 * @param relativetime       the relativetime
	 * @param biome              the biome
	 * @return the moon
	 */
	public static Moon buildMoon(Planet parent, String name, Class<? extends WorldProvider> provider,
			int dimID, int tier, float phase, float size, float distancefromcenter, float relativetime,
			ArrayList<Biome> biome) {
		Moon body = (new Moon(name)).setParentPlanet(parent);
		body.setRingColorRGB(0.1F, 0.9F, 0.6F);
		body.setPhaseShift(phase);
		body.setRelativeSize(size);
		body.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(distancefromcenter, distancefromcenter));
		body.setRelativeOrbitTime(relativetime);
		body.setBodyIcon(
				new ResourceLocation(getModId(), "textures/celestialbodies/moons/" + name + ".png"));
		if (provider != null) {
			body.setTierRequired(tier);
			body.setDimensionInfo(dimID, provider);
			if (biome != null)
				for(Biome b : biome) {
					body.setBiomeInfo(b);
				}
		}
		return body;
	}

	/**
	 * builds SpaceStation.
	 *
	 * @param parent             the parent
	 * @param prefix             the prefix
	 * @param provider           the provider
	 * @param dimID              the dim ID
	 * @param dimIDStatic        the dim ID static
	 * @param phase              the phase
	 * @param size               the size
	 * @param distancefromcenter the distancefromcenter
	 * @param relativetime       the relativetime
	 * @return the satellite
	 */
	public static Satellite buildSpaceStation(Planet parent, Class<? extends WorldProvider> provider, int dimID,
			int dimIDStatic, float phase, float size, float distancefromcenter, float relativetime, boolean customStationIcon, @Nullable String bodyIcon) {
		Satellite body = new Satellite("spacestation." + parent.getUnlocalizedName().replace("planet.", ""));
		body.setParentBody(parent);
		body.setRelativeOrbitTime(relativetime);
		body.setPhaseShift(phase);
		body.setRelativeSize(size);
		body.setRingColorRGB(0.1F, 0.9F, 0.6F);
		if(customStationIcon) {
			body.setBodyIcon(new ResourceLocation(getModId(), "textures/celestialbodies/spacestations/"+ bodyIcon +".png"));
		} else {
			body.setBodyIcon(new ResourceLocation("galacticraftcore:textures/gui/celestialbodies/space_station.png"));
		}
		if (provider != null) {
			body.setTierRequired(parent.getTierRequirement());
			body.setDimensionInfo(dimID, dimIDStatic, provider);
			body.setBiomeInfo(new Biome[] { BiomeOrbit.space });
		}
		return body;
	}
	
	/**
	 * Builds unreachable planet.
	 *
	 * @param planetName the planet name
	 * @param solarSystem the solar system
	 * @return the planet
	 */
	public static Planet buildUnreachablePlanet(String planetName, SolarSystem solarSystem) {
		Planet unreachable = new Planet(planetName).setParentSolarSystem(solarSystem);
		unreachable.setBodyIcon(new ResourceLocation(getModId(), "textures/gui/celestialbodies/" + planetName + ".png"));
		GalaxyRegistry.registerPlanet(unreachable);
		return unreachable;
	}

	/**
	 * Calculate gravity.
	 *
	 * @param Si the si
	 * @return the float
	 */
	public float calculateGravity(float Si) {
		float i = (9.81F - Si) * 0.008664628F;
		float k = Math.round(i * 1000.0F);
		return k / 1000.0F;
	}

	/**
	 * Sets the atmosphere component list.
	 *
	 * @param celestial the celestial
	 * @param gasList   the gas list
	 */
	public static void setAtmosphereComponentList(CelestialBody celestial, EnumAtmosphericGas... gasList) {
		for (EnumAtmosphericGas gas : gasList) {
			celestial.atmosphereComponent(gas);
		}
	}

	/**
	 * Sets the atmosphere.
	 *
	 * @param celestial           the celestial
	 * @param breathable          the breathable
	 * @param precipitation       the precipitation
	 * @param corrosive           the corrosive
	 * @param relativeTemperature the relative temperature
	 * @param windLevel           the wind level
	 * @param density             the density
	 */
	public static void setAtmosphere(CelestialBody celestial, Boolean breathable, boolean precipitation, boolean corrosive,
			float relativeTemperature, float windLevel, float density) {
		celestial.setAtmosphere(
				new AtmosphereInfo(breathable, precipitation, corrosive, relativeTemperature, windLevel, density));
	}

	/**
	 * Sets the checklist keys.
	 *
	 * @param celestial the celestial
	 * @param keys      the keys
	 */
	public static void setChecklistKeys(CelestialBody celestial, String... keys) {
		celestial.addChecklistKeys(keys);
	}

	/**
	 * Register solar system.
	 *
	 * @param solarSystem the solar system
	 */
	public static void registerSolarSystem(SolarSystem solarSystem) {
		GalaxyRegistry.registerSolarSystem(solarSystem);
	}

	/**
	 * Register planet.
	 *
	 * @param planet the planet
	 */
	public static void registerPlanet(Planet planet) {
		GalaxyRegistry.registerPlanet(planet);
	}

	/**
	 * Register moon.
	 *
	 * @param moon the moon
	 */
	public static void registerMoon(Moon moon) {
		GalaxyRegistry.registerMoon(moon);
	}

	/**
	 * Register teleport type.
	 *
	 * @param clazz the clazz
	 * @param type  the type
	 */
	public static void registerTeleportType(Class<? extends WorldProvider> clazz, ITeleportType type) {
		GalacticraftRegistry.registerTeleportType(clazz, type);
	}

	/**
	 * Register rocket gui.
	 *
	 * @param clazz    the clazz
	 * @param resource the resource
	 */
	public static void registerRocketGui(String modid, Class<? extends WorldProvider> clazz, String resource) {
		GalacticraftRegistry.registerRocketGui(clazz,
				new ResourceLocation(modid + ":textures/gui/rocket/" + resource + ".png"));
	}

	public static String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		AstroBuilder.modId = modId;
	}
}
