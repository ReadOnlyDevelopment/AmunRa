package de.katzenpapst.amunra.util;

import net.minecraft.client.Minecraft;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;

public final class MCUtil {

	private static MinecraftServer serverCached;

	public static Minecraft getClient() {
		return FMLClientHandler.instance().getClient();
	}

	public static MinecraftServer getServer() {
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		if (server == null)
			return serverCached;
		return server;
	}

	/**
	 * Check if this is the client side.
	 *
	 * @return True if and only if we are on the client side
	 */
	public static boolean isClient() {
		return FMLCommonHandler.instance().getSide().isClient();
	}

	/**
	 * Check if this is a deobfuscated (development) environment.
	 *
	 * @return True if and only if we are running in a deobfuscated environment
	 */
	public static boolean isDeobfuscated() {
		return (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
	}

	/**
	 * Check if this is the server side.
	 *
	 * @return True if and only if we are on the server side
	 */
	public static boolean isServer() {
		return FMLCommonHandler.instance().getSide().isServer();
	}

	private MCUtil() {
		throw new IllegalAccessError("Util Class");
	}

}
