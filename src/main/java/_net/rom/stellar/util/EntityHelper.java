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
import java.util.Queue;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;

import de.katzenpapst.amunra.AmunRa;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber(modid = AmunRa.MODID)
public final class EntityHelper {
    private EntityHelper() {
        throw new IllegalAccessError("Utility class");
    }

    @Deprecated
    public static void moveSelf(Entity entity, double x, double y, double z) {
        entity.move(MoverType.SELF, x, y, z);
    }

    /**
     * Simulates the 1.10.2 behavior of EntityList#getEntityNameList.
     *
     */
    @Deprecated
    public static List<String> getEntityNameList() {
        List<String> list = Lists.newArrayList();
        EntityList.getEntityNameList().forEach(res -> list.add(EntityList.getTranslationName(res)));
        return list;
    }

    private static volatile Queue<Entity> entitiesToSpawn = Queues.newArrayDeque();

    /**
     * Thread-safe spawning of entities. The queued entities will be spawned in the START (pre)
     * phase of WorldTickEvent.
     *
     */
    public static void safeSpawn(Entity entity) {
        entitiesToSpawn.add(entity);
    }

    private static void handleSpawns() {
        Entity entity;
        while ((entity = entitiesToSpawn.poll()) != null) {
            entity.world.spawnEntity(entity);
        }
    }

    /**
     * Heals the player by the given amount. If cancelable is true, this calls the heal method
     * (which fires a cancelable event). If cancelable is false, this uses setHealth instead.
     *
     */
    public static void heal(EntityLivingBase entityLiving, float healAmount, boolean cancelable) {
        if (cancelable) {
            entityLiving.heal(healAmount);
        } else {
            entityLiving.setHealth(entityLiving.getHealth() + healAmount);
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            handleSpawns();
        }
    }
}
