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
package _net.rom.stellar.advancements;

import java.util.*;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import de.katzenpapst.amunra.AmunRa;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class DefaultTrigger implements ICriterionTrigger<DefaultTrigger.Instance> {
    private static final ResourceLocation ID = new ResourceLocation(AmunRa.MODID, "generic_int");
    private final Map<PlayerAdvancements, DefaultTrigger.Listeners> listeners = new HashMap<>();

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void addListener(PlayerAdvancements playerAdvancementsIn, Listener<DefaultTrigger.Instance> listenerIn) {
        Listeners triggerListeners = this.listeners.get(playerAdvancementsIn);
        if (triggerListeners == null) {
            triggerListeners = new Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, triggerListeners);
        }
        triggerListeners.add(listenerIn);
    }

    @Override
    public void removeListener(PlayerAdvancements playerAdvancementsIn, Listener<DefaultTrigger.Instance> listenerIn) {
        Listeners triggerListeners = this.listeners.get(playerAdvancementsIn);
        if (triggerListeners != null) {
            triggerListeners.remove(listenerIn);
            if (triggerListeners.isEmpty())
                this.listeners.remove(playerAdvancementsIn);
        }
    }

    @Override
    public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
        this.listeners.remove(playerAdvancementsIn);
    }

    @Override
    public DefaultTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        String type = JsonUtils.getString(json, "type", "unknown");
        int value = JsonUtils.getInt(json, "value", 0);
        return new Instance(type, value);
    }

    public static class Instance extends AbstractCriterionInstance {
        String type;
        int value;

        Instance(String type, int value) {
            super(DefaultTrigger.ID);
            this.type = type;
            this.value = value;
        }

        public boolean test(String typeIn, int valueIn) {
            return this.type.equals(typeIn) && this.value <= valueIn;
        }
    }

    public void trigger(EntityPlayerMP player, ResourceLocation type, int value) {
    	DefaultTrigger.Listeners triggerListeners = this.listeners.get(player.getAdvancements());
        if (triggerListeners != null)
            triggerListeners.trigger(type.toString(), value);
    }

    static class Listeners {
        private final PlayerAdvancements playerAdvancements;
        private final Set<Listener<Instance>> listeners = new HashSet<>();

        Listeners(PlayerAdvancements playerAdvancementsIn) {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty() {
            return this.listeners.isEmpty();
        }

        public void add(Listener<Instance> listener) {
            this.listeners.add(listener);
        }

        public void remove(Listener<Instance> listener) {
            this.listeners.remove(listener);
        }

        public void trigger(String typeIn, int valueIn) {
            List<Listener<Instance>> list = new ArrayList<>();

            for (Listener<Instance> listener : this.listeners)
                if (listener.getCriterionInstance().test(typeIn, valueIn))
                    list.add(listener);

            for (Listener<Instance> listener : list)
                listener.grantCriterion(this.playerAdvancements);
        }
    }
}
