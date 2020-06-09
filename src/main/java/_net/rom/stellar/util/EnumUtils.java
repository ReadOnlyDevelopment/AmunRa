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

import java.util.Optional;
import java.util.function.Function;

/**
 * Some generic utility methods for working with enums.
 *
 */
public final class EnumUtils {
    private EnumUtils() {throw new IllegalAccessError("Utility class");}

    public static <T extends Enum<T>> Optional<T> fromString(Class<T> enumClass, String value) {
        return fromString(enumClass, value, true);
    }

    public static <T extends Enum<T>> Optional<T> fromString(Class<T> enumClass, String value, boolean ignoreCase) {
        for (T t : enumClass.getEnumConstants())
            if ((ignoreCase && t.name().equalsIgnoreCase(value)) || t.name().equals(value))
                return Optional.of(t);

        return Optional.empty();
    }

    public static <T extends Enum<T>> Optional<T> fromIndex(Class<T> enumClass, int value, Function<T, Integer> getter) {
        for (T t : enumClass.getEnumConstants())
            if (getter.apply(t) == value)
                return Optional.of(t);

        return Optional.empty();
    }

    public static <T extends Enum<T>> Optional<T> fromOrdinal(Class<T> enumClass, int value) {
        if (value < 0 || value >= enumClass.getEnumConstants().length)
            return Optional.empty();
        return Optional.of(enumClass.getEnumConstants()[value]);
    }
}
