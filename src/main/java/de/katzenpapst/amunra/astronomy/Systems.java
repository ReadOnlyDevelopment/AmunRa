package de.katzenpapst.amunra.astronomy;

import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;

public class Systems {
	
	
	public static SolarSystem systemAmunRa;
	
	public static void init() {
		register();
	}
	
	private static void register() {
		
		systemAmunRa = AstroBuilder.buildSolarSystem("systemAmunRa", GalacticraftCore.solarSystemSol.getName(), new Vector3(1.5F, -1.15F, 0.0F), "starRa", 1.0f);
		AstroBuilder.registerSolarSystem(systemAmunRa);
		
	}

}
