package de.katzenpapst.amunra.proxy.stellar;

import _net.rom.stellar.registry.StellarRegistry;
import de.katzenpapst.amunra.AmunRa;
import micdoodle8.mods.galacticraft.core.util.GCLog;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy extends CommonProxy {

	private static MinecraftServer serverCached;

	@Override
	public void preInit(StellarRegistry registry, FMLPreInitializationEvent event) {
		try {
			MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
			if (server.isDedicatedServer() && !server.isServerInOnlineMode() && AmunRa.config.mothershipUserMatchUUID) {
				GCLog.info("Server running in offline mode. Setting \"matchUsersByUUID\" to false");
				AmunRa.config.mothershipUserMatchUUID = false;
			}
		} catch (NullPointerException e) {
			GCLog.info("Could not detect whenever server is in online mode. If it's not, please manually set \"matchUsersByUUID\" to false");
		}
	}

	@SuppressWarnings("unused")
	private static MinecraftServer getServer() {
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		if (server == null) {
			return serverCached;
		}
		return server;
	}

	public static void notifyStarted(MinecraftServer server) {
		serverCached = server;
	}
}
