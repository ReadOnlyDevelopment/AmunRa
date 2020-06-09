package de.katzenpapst.amunra.proxy.stellar;

import _net.rom.stellar.event.CommonEvents;
import _net.rom.stellar.proxies.IStellarProxy;
import _net.rom.stellar.registry.StellarRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy implements IStellarProxy {

	@Override
	public void preInit(StellarRegistry registry, FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new CommonEvents());
	}

	@Override
	public void init(StellarRegistry registry, FMLInitializationEvent event) {
		
	}

	@Override
	public void postInit(StellarRegistry registry, FMLPostInitializationEvent event) {
		
	}

	@Override
	public EntityPlayer getClientPlayer() {
		return null;
	}

	@Override
	public int getParticleSettings() {
		return 0;
	} 

}
 