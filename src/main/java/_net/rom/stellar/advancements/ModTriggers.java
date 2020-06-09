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

import net.minecraft.advancements.CriteriaTriggers;

public class ModTriggers {
    /**
     * Represents an item being used (right-clicking a block, entity, or nothing). No need to
     * trigger this yourself, it's handled in {@link _net.rom.stellar.event.CommonEvents}.
     */
    public static final ItemTrigger USE_ITEM = CriteriaTriggers.register(new ItemTrigger());
    /**
     * Can be used if you just need a trigger with a single int. Requires a ResourceLocation to to
     * distinguish between different uses.
     */
    public static final DefaultTrigger GENERIC_INT = CriteriaTriggers.register(new DefaultTrigger());

    public static void init() {
        // NO-OP, just needed to initialize static fields in time.
    }
}
