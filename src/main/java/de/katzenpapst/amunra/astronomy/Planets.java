package de.katzenpapst.amunra.astronomy;

import de.katzenpapst.amunra.config.ConfigDimensions;
import de.katzenpapst.amunra.world.horus.HorusWorldProvider;
import de.katzenpapst.amunra.world.mehen.MehenWorldProvider;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody.ScalableDistance;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;

public class Planets {
	
	public static Planet planetOsiris;
	public static Planet planetHorus;
	public static Planet planetBaal;
	public static Planet planetAnubis;
	public static Planet asteroidBeltMehen;
	public static Planet planetSekhmet;
	
	private static SolarSystem system = Systems.systemAmunRa;
	
	public static void init() {
		build();
		register();
	}
	
	
	private static void build() {
		planetOsiris = AstroBuilder.buildUnreachablePlanet("osiris", system);
		planetOsiris.setRelativeSize(1.0f);
		planetOsiris.setPhaseShift((float) (Math.PI * 0.1));
		planetOsiris.setRelativeDistanceFromCenter(new ScalableDistance(0.34F, 0.34F));
		planetOsiris.setRelativeOrbitTime(0.4F);
		
		planetBaal = AstroBuilder.buildUnreachablePlanet("baal", system);
		planetBaal.setRelativeSize(1.0f);
		planetBaal.setPhaseShift((float) (Math.PI * 1.9));
		planetBaal.setRelativeDistanceFromCenter(new ScalableDistance(1.2F, 1.2F));
		planetBaal.setRelativeOrbitTime(1.4F);
		
		planetSekhmet = AstroBuilder.buildUnreachablePlanet("maahes", system);
		planetSekhmet.setRelativeSize(1.0f);
		planetSekhmet.setPhaseShift((float) (Math.PI * 0.6));
		planetSekhmet.setRelativeDistanceFromCenter(new ScalableDistance(1.6F, 1.6F));
		planetSekhmet.setRelativeOrbitTime(1.8F);
		
		planetHorus = AstroBuilder.buildPlanet(system, "horus", HorusWorldProvider.class, ConfigDimensions.dimHorus, 3, (float) (Math.PI * 0.1), 1.0f, 0.55F, 0.458F, null);
		asteroidBeltMehen = AstroBuilder.buildPlanet(system, "asteroidBeltMehen", MehenWorldProvider.class, ConfigDimensions.dimMehen, 3, (float) (Math.PI * 0.19), 1.0F, 1.4F, 1.6F, null);
	}
	
	private static void register() {
		
		GalaxyRegistry.registerPlanet(planetOsiris);
		GalaxyRegistry.registerPlanet(planetBaal);
		GalaxyRegistry.registerPlanet(planetSekhmet);
		GalaxyRegistry.registerPlanet(planetHorus);
		GalaxyRegistry.registerPlanet(asteroidBeltMehen);
		
	}

}
