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

import java.util.ArrayDeque;
import java.util.Queue;

import de.katzenpapst.amunra.AmunRa;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Can schedule actions to run during {@link TickEvent.ServerTickEvent}, which is mainly useful for
 * handling packets.
 *
 * 
 */
@Mod.EventBusSubscriber(modid = AmunRa.MODID)
public class ServerTicking {
    private static final int QUEUE_OVERFLOW_LIMIT = 30;
    private static volatile Queue<Runnable> scheduledActions = new ArrayDeque<>();

    private ServerTicking() {}

    public static void scheduleAction(Runnable action) {
        // In SSP, this is still considered client side, so we can't check the side?
        scheduledActions.add(action);

        if (scheduledActions.size() > QUEUE_OVERFLOW_LIMIT)
            AmunRa.LOGGER.warn("Too many server tick actions queued! Currently at {} items.", scheduledActions.size());
    }

    @SubscribeEvent
    public static void serverTicks(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START)
            runScheduledActions();
    }

    private static void runScheduledActions() {
        Runnable action = scheduledActions.poll();
        while (action != null) {
            action.run();
            action = scheduledActions.poll();
        }
    }
}
