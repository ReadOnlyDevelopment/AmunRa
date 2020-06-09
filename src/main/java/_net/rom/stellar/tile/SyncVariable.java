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
package _net.rom.stellar.tile;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import javax.activation.UnsupportedDataTypeException;

import _net.rom.stellar.util.NBTSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Marks variables that should be automatically synced with the client. Currently, this is used just
 * for tile entities, but could have other uses I guess? See {@link TileEntitySL} for usage.
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface SyncVariable {
    /**
     * The name to read/write to NBT.
     */
    String name();

    /**
     * Should the variable be loaded in {@link TileEntity#readFromNBT}?
     */
    boolean onRead() default true;

    /**
     * Should the variable be saved in {@link TileEntity#writeToNBT}?
     */
    boolean onWrite() default true;

    /**
     * Should the variable be saved in {@link TileEntity#getUpdatePacket} and {@link
     * TileEntity#getUpdateTag}?
     */
    boolean onPacket() default true;

    /**
     * Used together with onRead, onWrite, and onPacket to determine when a variable should be
     * saved/loaded. In most cases, you should probably just sync everything at all times.
     */
    enum Type {
        READ, WRITE, PACKET
    }

    /**
     * Reads/writes sync variables for any object. Used by TileEntitySL in Lib. Gems uses this in
     * PlayerDataHandler.
     *
     */
    final class Helper {
        static final Map<Class, Function<NBTTagCompound, ?>> READERS = new HashMap<>();
        static final Map<Class, Function<?, NBTTagCompound>> WRITERS = new HashMap<>();
        static final Map<Class, NBTSerializer> SERIALIZERS = new HashMap<>();

        private Helper() {}

        public static <T> void registerSerializer(Class<T> clazz,
                                                  Function<NBTTagCompound, T> reader,
                                                  BiConsumer<NBTTagCompound, T> writer) {
            SERIALIZERS.put(clazz, new NBTSerializer<T>() {
                @Override
                public T read(NBTTagCompound tags) {
                    return reader.apply(tags);
                }

                @Override
                public void write(NBTTagCompound tags, T obj) {
                    writer.accept(tags, obj);
                }
            });
        }

        /**
         * Reads sync variables for the object. This method will attempt to read a value from NBT
         * and assign that value for any field marked with the SyncVariable annotation.
         *
         * @param obj  The object with SyncVariable fields.
         * @param tags The NBT to read values from.
         */
        public static void readSyncVars(Object obj, NBTTagCompound tags) {

            // Try to read from NBT for fields marked with SyncVariable.
            for (Field field : obj.getClass().getDeclaredFields()) {
                for (Annotation annotation : field.getDeclaredAnnotations()) {
                    if (annotation instanceof SyncVariable) {
                        SyncVariable sync = (SyncVariable) annotation;

                        try {
                            // Set fields accessible if necessary.
                            if (!field.isAccessible())
                                field.setAccessible(true);
                            String name = sync.name();

                            //noinspection ChainOfInstanceofChecks
                            if (field.getType() == int.class)
                                field.setInt(obj, tags.getInteger(name));
                            else if (field.getType() == float.class)
                                field.setFloat(obj, tags.getFloat(name));
                            else if (field.getType() == String.class)
                                field.set(obj, tags.getString(name));
                            else if (field.getType() == boolean.class)
                                field.setBoolean(obj, tags.getBoolean(name));
                            else if (field.getType() == double.class)
                                field.setDouble(obj, tags.getDouble(name));
                            else if (field.getType() == long.class)
                                field.setLong(obj, tags.getLong(name));
                            else if (field.getType() == short.class)
                                field.setShort(obj, tags.getShort(name));
                            else if (field.getType() == byte.class)
                                field.setByte(obj, tags.getByte(name));
                            else if (SERIALIZERS.containsKey(field.getType())) {
                                NBTSerializer serializer = SERIALIZERS.get(field.getType());
                                NBTTagCompound compound = tags.getCompoundTag(name);
                                field.set(obj, serializer.read(compound));
                            } else
                                throw new UnsupportedDataTypeException(
                                        "Don't know how to read type " + field.getType() + " from NBT!");
                        } catch (IllegalAccessException | UnsupportedDataTypeException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }

        /**
         * Writes sync variables for the object. This method will take the values in all fields
         * marked with the SyncVariable annotation and save them to NBT.
         *
         * @param obj      The object with SyncVariable fields.
         * @param tags     The NBT to save values to.
         * @param syncType The sync type (WRITE or PACKET).
         * @return The modified tags.
         */
        @SuppressWarnings("unchecked") // from serializer
        public static NBTTagCompound writeSyncVars(Object obj, NBTTagCompound tags, Type syncType) {

            // Try to write to NBT for fields marked with SyncVariable.
            for (Field field : obj.getClass().getDeclaredFields()) {
                for (Annotation annotation : field.getDeclaredAnnotations()) {
                    if (annotation instanceof SyncVariable) {
                        SyncVariable sync = (SyncVariable) annotation;

                        // Does variable allow writing in this case?
                        if (syncType == SyncVariable.Type.WRITE && sync.onWrite()
                                || syncType == SyncVariable.Type.PACKET && sync.onPacket()) {
                            try {
                                // Set fields accessible if necessary.
                                if (!field.isAccessible())
                                    field.setAccessible(true);
                                String name = sync.name();

                                //noinspection ChainOfInstanceofChecks
                                if (field.getType() == int.class)
                                    tags.setInteger(name, field.getInt(obj));
                                else if (field.getType() == float.class)
                                    tags.setFloat(name, field.getFloat(obj));
                                else if (field.getType() == String.class)
                                    tags.setString(name, (String) field.get(obj));
                                else if (field.getType() == boolean.class)
                                    tags.setBoolean(name, field.getBoolean(obj));
                                else if (field.getType() == double.class)
                                    tags.setDouble(name, field.getDouble(obj));
                                else if (field.getType() == long.class)
                                    tags.setLong(name, field.getLong(obj));
                                else if (field.getType() == short.class)
                                    tags.setShort(name, field.getShort(obj));
                                else if (field.getType() == byte.class)
                                    tags.setByte(name, field.getByte(obj));
                                else if (SERIALIZERS.containsKey(field.getType())) {
                                    NBTTagCompound compound = new NBTTagCompound();
                                    NBTSerializer serializer = SERIALIZERS.get(field.getType());
                                    serializer.write(compound, field.get(obj));
                                    tags.setTag(name, compound);
                                } else
                                    throw new UnsupportedDataTypeException(
                                            "Don't know how to write type " + field.getType() + " to NBT!");
                            } catch (IllegalAccessException | UnsupportedDataTypeException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }

            return tags;
        }
    }
}
