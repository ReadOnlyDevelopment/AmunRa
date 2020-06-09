/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020, ROMVoid95 <rom.readonlydev@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package de.katzenpapst.amunra.config;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.katzenpapst.amunra.AmunRa;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.RangeDouble;
import net.minecraftforge.common.config.Config.RangeInt;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigCore {
	
	private static int configVersion = 1;

	static Configuration config;

	public ConfigCore(File file) {
		ConfigCore.config = new Configuration((file), Integer.toString(configVersion));
		ConfigCore.syncConfig(true);
	}
	
	public static HashMap<String, Boolean> manual_bool = new HashMap<String, Boolean>();
	public static HashMap<String, Integer> manual_int = new HashMap<String, Integer>();
	public static HashMap<String, int[]> manual_intA = new HashMap<String, int[]>();
	public static HashMap<String, Double> manual_double = new HashMap<String, Double>();
	public static HashMap<String, double[]> manual_doubleA = new HashMap<String, double[]>();

	public static boolean enableCheckVersion;
	public static boolean enableOverworldOres;
	public static boolean enableDebug;
	
	public static String retrogen_key;

	private static String CATEGORY_GENERAL_MAIN = "Core Settings for Amun-Ra";
	private static String CATEGORY_WORLDGEN = "WorldGen Config";

	public static void syncConfig(boolean load) {
		try {
			
			config.load();

			
			config.addCustomCategoryComment(CATEGORY_GENERAL_MAIN, "Core Settings");
			config.setCategoryLanguageKey(CATEGORY_GENERAL_MAIN, "amunra.configgui.category.core");
			config.setCategoryRequiresMcRestart(CATEGORY_GENERAL_MAIN, true);

			
			config.addCustomCategoryComment(CATEGORY_WORLDGEN, "Settings that affect WorldGen");
			config.setCategoryLanguageKey(CATEGORY_GENERAL_MAIN, "amunra.configgui.category.worldgen");
			

			enableCheckVersion = config.getBoolean("enableCheckVersion", CATEGORY_GENERAL_MAIN, true, "Enable/Disable Check Version", "amunra.configgui.versioncheck");
			enableOverworldOres = config.getBoolean("enableDebug", CATEGORY_GENERAL_MAIN, false, "Enable/Disable Generation Ores on Overworld", "amunra.configgui.overworldores");
			enableDebug = config.getBoolean("enableOverworldOres", CATEGORY_GENERAL_MAIN, false, "Enable/Disable Debug mode", "amunra.configgui.debug");
			
			retrogen_key = config.getString("retrogenKey", CATEGORY_WORLDGEN, "DEFAULT", "The retrogeneration key");
			
			@Comment({"Set this to true to allow retro-generation of Copper Ore."})
			@Mapped(mapClass = IEWorldGen.class, mapName = "retrogenMap")
			public static boolean retrogen_copper = false;
			
			if (config.hasChanged()) {
				config.save();
			}
		} catch (final Exception e) {
			AmunRa.LOGGER.error("Intersteller Core Config had an issue loading the config file!");
		}
	}

	public static List<IConfigElement> getConfigElements() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		
		ConfigCategory configGeneral = config.getCategory(CATEGORY_GENERAL_MAIN);
		configGeneral.setComment("Core Settings");
		list.add(new ConfigElement(configGeneral));
		


		return list;
	}

    @SubscribeEvent
    public void onConfigChangedEvent(OnConfigChangedEvent event)
    {
        if (event.getModID().equals(AmunRa.MODID))
        {
            ConfigManager.sync(AmunRa.MODID, Type.INSTANCE);
        }
    }
    
	public static void validateAndMapValues(Class confClass)
	{
		for(Field f : confClass.getDeclaredFields())
		{
			if(!Modifier.isStatic(f.getModifiers()))
				continue;
			Mapped mapped = f.getAnnotation(Mapped.class);
			if(mapped!=null)
				try
				{
					Class c = mapped.mapClass();
					if(c!=null)
					{
						Field mapField = c.getDeclaredField(mapped.mapName());
						if(mapField!=null)
						{
							Map map = (Map)mapField.get(null);
							if(map!=null)
								map.put(f.getName(), f.get(null));
						}
					}
				} catch(Exception e)
				{
					e.printStackTrace();
				}
			else if(f.getAnnotation(SubConfig.class)!=null)
				validateAndMapValues(f.getType());
			else if(f.getAnnotation(RangeDouble.class)!=null)
				try
				{
					RangeDouble range = f.getAnnotation(RangeDouble.class);
					Object valObj = f.get(null);
					double val;
					if(valObj instanceof Double)
						val = (double)valObj;
					else
						val = (float)valObj;
					if(val < range.min())
						f.set(null, range.min());
					else if(val > range.max())
						f.set(null, range.max());
				} catch(IllegalAccessException e)
				{
					e.printStackTrace();
				}
			else if(f.getAnnotation(RangeInt.class)!=null)
				try
				{
					RangeInt range = f.getAnnotation(RangeInt.class);
					int val = (int)f.get(null);
					if(val < range.min())
						f.set(null, range.min());
					else if(val > range.max())
						f.set(null, range.max());
				} catch(IllegalAccessException e)
				{
					e.printStackTrace();
				}
		}
	}

	/**
	 * @return the configversion
	 */
	public static double getConfigversion() {
		return configVersion;
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface Mapped
	{
		Class mapClass();

		String mapName();
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface SubConfig
	{
	}

}
