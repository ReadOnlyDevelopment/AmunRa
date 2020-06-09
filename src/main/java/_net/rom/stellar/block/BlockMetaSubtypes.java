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
package _net.rom.stellar.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

/**
 * This will be removed completely in the next MC update of the Mod.
 * Kept for StellarRegistry utilization
 * 
 * Take care to avoid using this class to extend metadata blocks unless
 * your aware of the implications of future updates to your Mod.
 */
@Deprecated
public class BlockMetaSubtypes extends Block{
	
	private final int subtypeCount;

	public BlockMetaSubtypes(Material materialIn, int subtypeCount) {
		super(materialIn);
		this.subtypeCount = subtypeCount;
	}
	
    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

	public int getSubtypeCount() {
		return subtypeCount;
	}

}
