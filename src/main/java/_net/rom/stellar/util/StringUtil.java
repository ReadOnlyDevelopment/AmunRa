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

import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class StringUtil {

  @SideOnly(Side.CLIENT)
  public static void drawSplitString(FontRenderer renderer, String strg, int x, int y, int width,
      int color, boolean shadow) {

    List list = renderer.listFormattedStringToWidth(strg, width);
    for (int i = 0; i < list.size(); i++) {
      String s1 = (String) list.get(i);
      renderer.drawString(s1, x, y + (i * renderer.FONT_HEIGHT), color, shadow);
    }
  }

  @SideOnly(Side.CLIENT)
  public static void renderScaledAsciiString(FontRenderer font, String text, int x, int y,
      int color, boolean shadow, float scale) {

    GlStateManager.pushMatrix();
    GlStateManager.scale(scale, scale, scale);
    boolean oldUnicode = font.getUnicodeFlag();
    font.setUnicodeFlag(false);

    font.drawString(text, x / scale, y / scale, color, shadow);

    font.setUnicodeFlag(oldUnicode);
    GlStateManager.popMatrix();
  }

  @SideOnly(Side.CLIENT)
  public static void renderSplitScaledAsciiString(FontRenderer font, String text, int x, int y,
      int color, boolean shadow, float scale, int length) {

    List<String> lines = font.listFormattedStringToWidth(text, (int) (length / scale));
    for (int i = 0; i < lines.size(); i++) {
      renderScaledAsciiString(font, lines.get(i), x, y + (i * (int) (font.FONT_HEIGHT * scale + 3)),
          color, shadow, scale);
    }
  }
}
