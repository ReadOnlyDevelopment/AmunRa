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
import java.util.ArrayList;
import java.util.List;

import de.katzenpapst.amunra.AmunRa;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigDimensions {

	static Configuration config;

	public ConfigDimensions(File file) {
		ConfigDimensions.config = new Configuration((file), "1.0");
		ConfigDimensions.syncConfig(true);
	}

	// ** dimension IDs **
	public static int dimNeper;
	public static int dimMaahes;
	public static int dimAnubis;
	public static int dimHorus;
	public static int dimSeth;
	public static int dimMehen;
	
	private static String CATEGORY_DIMENSION_IDS = "Dimension ID's";

	public static void syncConfig(boolean load) {
		try {

			config.load();
			
			config.addCustomCategoryComment(CATEGORY_DIMENSION_IDS,
					"Change the Dimension IDs if Conflicts Arise");
			config.setCategoryLanguageKey(CATEGORY_DIMENSION_IDS, "amunra.configgui.category.dimensionid");
			config.setCategoryRequiresMcRestart(CATEGORY_DIMENSION_IDS, true);
			
			dimNeper = config.getInt("dimNeper", CATEGORY_DIMENSION_IDS, -750, -2147483647, 2147483647, "Neper Dimension ID", "amunra.configgui.neper");
			dimMaahes = config.getInt("dimMaahes", CATEGORY_DIMENSION_IDS, -751, -2147483647, 2147483647, "Maahes Dimension ID", "amunra.configgui.maahes");
			dimAnubis = config.getInt("dimAnubis", CATEGORY_DIMENSION_IDS, -752, -2147483647, 2147483647, "Anubis Dimension ID", "amunra.configgui.anubis");
			dimHorus = config.getInt("dimHorus", CATEGORY_DIMENSION_IDS, -753, -2147483647, 2147483647, "Horus Dimension ID", "amunra.configgui.horus");
			dimSeth = config.getInt("dimSeth", CATEGORY_DIMENSION_IDS, -744, -2147483647, 2147483647, "Seth Dimension ID", "amunra.configgui.seth");
			dimMehen = config.getInt("dimMehen", CATEGORY_DIMENSION_IDS, -745, -2147483647, 2147483647, "Mehen Dimension ID", "amunra.configgui.mehen");

			if (config.hasChanged()) {
				config.save();
			}
		} catch (final Exception e) {
			AmunRa.LOGGER.error("Amunra Config had an issue loading the config file!");
		}
	}

	public static List<IConfigElement> getConfigElements() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		
		ConfigCategory configYzId = config.getCategory(CATEGORY_DIMENSION_IDS);
		configYzId.setComment("Yz Ceti Planet Dimension ID's");
		list.add(new ConfigElement(configYzId));


		return list;
	}
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if (eventArgs.getModID().equals(AmunRa.MODID)) {
			config.save();
		}
	}


}
