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
package _net.rom.stellar.client.renderer.te;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class TileEntityVipRenderer<T extends TileEntity> extends TileEntitySpecialRenderer<T> {
	@Override
	public void render(T te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

		clRender(te, x, y, z, partialTicks, destroyStage, alpha);
	}

	public void clRender(T te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
	}
}
