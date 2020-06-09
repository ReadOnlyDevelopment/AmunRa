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
package _net.rom.stellar.client;

import _net.rom.stellar.item.ILeftClickItem;
import _net.rom.stellar.network.internal.MessageLeftClick;
import de.katzenpapst.amunra.AmunRa;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public final class ClientEvents {
	  @SubscribeEvent
	  public void onLeftClickEmpty(LeftClickEmpty event) {

	    ItemStack stack = event.getItemStack();
	    if (!stack.isEmpty() && stack.getItem() instanceof ILeftClickItem) {
	      // Client-side call
	      ActionResult<ItemStack> result = ((ILeftClickItem) stack.getItem())
	          .onItemLeftClickBlockStellar(event.getWorld(), event.getEntityPlayer(), event.getHand());
	      // Server-side call
	      if (result.getType() == EnumActionResult.SUCCESS) {
	        AmunRa.network.wrapper
	            .sendToServer(new MessageLeftClick(MessageLeftClick.Type.EMPTY, event.getHand()));
	      }
	    }
	  }

	  @SubscribeEvent
	  public void onLeftClickBlock(LeftClickBlock event) {

	    ItemStack stack = event.getItemStack();
	    if (!stack.isEmpty() && stack.getItem() instanceof ILeftClickItem) {
	      // Client-side call
	      ActionResult<ItemStack> result = ((ILeftClickItem) stack.getItem())
	          .onItemLeftClickBlockStellar(event.getWorld(), event.getEntityPlayer(), event.getHand());
	      // Server-side call
	      if (result.getType() == EnumActionResult.SUCCESS) {
	    	  AmunRa.network.wrapper
	            .sendToServer(new MessageLeftClick(MessageLeftClick.Type.BLOCK, event.getHand()));
	      }
	    }
	  }
}
