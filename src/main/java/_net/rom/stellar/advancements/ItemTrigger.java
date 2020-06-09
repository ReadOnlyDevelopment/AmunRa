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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import de.katzenpapst.amunra.AmunRa;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class ItemTrigger implements ICriterionTrigger<ItemTrigger.Instance>  {
    private static final ResourceLocation ID = new ResourceLocation(AmunRa.MODID, "use_item");
    private final Map<PlayerAdvancements, ItemTrigger.Listeners> listeners = new HashMap<>();

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void addListener(PlayerAdvancements playerAdvancementsIn, Listener<Instance> listenerIn) {
        Listeners triggerListeners = this.listeners.get(playerAdvancementsIn);
        if (triggerListeners == null) {
            triggerListeners = new ItemTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, triggerListeners);
        }
        triggerListeners.add(listenerIn);
    }

    @Override
    public void removeListener(PlayerAdvancements playerAdvancementsIn, Listener<Instance> listenerIn) {
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
    public ItemTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        ItemPredicate itempredicate = ItemPredicate.deserialize(json.get("item"));
        Target target = Target.fromString(JsonUtils.getString(json, "target", "any"));
        return new ItemTrigger.Instance(itempredicate, target);
    }

    public static class Instance extends AbstractCriterionInstance {
        ItemPredicate itempredicate;
        Target target;

        Instance(ItemPredicate itempredicate, Target target) {
            super(ItemTrigger.ID);
            this.itempredicate = itempredicate;
            this.target = target;
        }

        public boolean test(ItemStack stack, Target target) {
            return itempredicate.test(stack) && (this.target == target || this.target == Target.ANY);
        }
    }

    public void trigger(EntityPlayerMP player, ItemStack stack, Target target) {
        ItemTrigger.Listeners triggerListeners = this.listeners.get(player.getAdvancements());
        if (triggerListeners != null)
            triggerListeners.trigger(stack, target);
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

        public void add(ICriterionTrigger.Listener<ItemTrigger.Instance> listener) {
            this.listeners.add(listener);
        }

        public void remove(ICriterionTrigger.Listener<ItemTrigger.Instance> listener) {
            this.listeners.remove(listener);
        }

        public void trigger(ItemStack stack, Target target) {
            List<Listener<Instance>> list = null;

            for (Listener<Instance> listener : this.listeners) {
                if (listener.getCriterionInstance().test(stack, target)) {
                    if (list == null) list = new ArrayList<>();
                    list.add(listener);
                }
            }

            if (list != null) {
                for (ICriterionTrigger.Listener<ItemTrigger.Instance> listener1 : list)
                    listener1.grantCriterion(this.playerAdvancements);
            }
        }
    }

    public enum Target {
        BLOCK, ENTITY, ITEM, ANY;

        static Target fromString(String str) {
            for (Target t : values())
                if (t.name().equalsIgnoreCase(str))
                    return t;
            return ANY;
        }
    }
}
