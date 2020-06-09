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
package _net.rom.stellar.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public interface ILeftClickItem {
	  /**
     * Networked left-click handler. Called in the mods EventHandlers implementation on both the client- 
     * andserver-side (via packet) when a player left-clicks on nothing (in the air).
     *
     * @return If this returns SUCCESS on the client-side, a packet will be sent to the server.
     */
    default ActionResult<ItemStack> onItemLeftClickStellar(World world, EntityPlayer player, EnumHand hand) {
        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    /**
     * Called when the player left-clicks on a block. Defaults to the same behavior as an empty
     * click (onItemLeftClickStellar).
     *
     * @return If this returns SUCCESS on the client-side, a packet will be sent to the server.
     */
    default ActionResult<ItemStack> onItemLeftClickBlockStellar(World world, EntityPlayer player, EnumHand hand) {
        return onItemLeftClickStellar(world, player, hand);
    }
}
