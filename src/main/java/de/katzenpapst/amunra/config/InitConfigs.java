package de.katzenpapst.amunra.config;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class InitConfigs {
	
	public static void init(FMLPreInitializationEvent event) {
		
		registerEventHandler(new ConfigDimensions(new File(event.getModConfigurationDirectory(), "Amunra/dimensions.cfg")));
	}
	
    public static void registerEventHandler(Object handler) {
        MinecraftForge.EVENT_BUS.register(handler);
    }

}
