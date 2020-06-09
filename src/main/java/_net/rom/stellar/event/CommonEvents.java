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
package _net.rom.stellar.event;

import _net.rom.stellar.advancements.ItemTrigger;
import _net.rom.stellar.advancements.ModTriggers;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class CommonEvents {
	
    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {

    }

    @SubscribeEvent
    public void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getEntityPlayer() instanceof EntityPlayerMP) {
        	ModTriggers.USE_ITEM.trigger((EntityPlayerMP) event.getEntityPlayer(), event.getItemStack(), ItemTrigger.Target.BLOCK);
        }
    }

    @SubscribeEvent
    public void onPlayerRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        if (event.getEntityPlayer() instanceof EntityPlayerMP) {
        	ModTriggers.USE_ITEM.trigger((EntityPlayerMP) event.getEntityPlayer(), event.getItemStack(), ItemTrigger.Target.ENTITY);
        }
    }

    @SubscribeEvent
    public void onPlayerRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (event.getEntityPlayer() instanceof EntityPlayerMP) {
        	ModTriggers.USE_ITEM.trigger((EntityPlayerMP) event.getEntityPlayer(), event.getItemStack(), ItemTrigger.Target.ITEM);
        }
    }

}
