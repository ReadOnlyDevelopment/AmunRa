/*
 * ReadOnlyCore
 * Copyright (C) 2020 ROMVoid95
 *
 * This library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package _net.rom.stellar.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.io.*;

public class JsonLoader {
    private static final Gson GSON = (new GsonBuilder()).create();

    public static String getAssetsPath(ResourceLocation name, String subfolder) {
        return "assets/" + name.getResourceDomain() + "/" + subfolder + "/" + name.getResourcePath() + ".json";
    }

    @Nullable
    public static JsonObject fromAssetsFile(ResourceLocation name, String subfolder) throws UnsupportedEncodingException, FileNotFoundException {
        return fromFile(getAssetsPath(name, subfolder));
    }

    @Nullable
    public static JsonObject fromFile(String filePath) throws UnsupportedEncodingException, FileNotFoundException {
        if (!filePath.endsWith(".json"))
            filePath += ".json";

        InputStream resourceAsStream = JsonLoader.class.getClassLoader().getResourceAsStream(filePath);
        if (resourceAsStream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream, "UTF-8"));
            JsonElement jsonElement = GSON.fromJson(reader, JsonElement.class);
            return jsonElement.getAsJsonObject();
        } else {
            throw new FileNotFoundException("Could not find file " + filePath);
        }
    }
}
