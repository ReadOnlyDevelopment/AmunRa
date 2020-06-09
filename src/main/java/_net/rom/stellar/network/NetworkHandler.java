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
package _net.rom.stellar.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Network handler class
 *
 */
public class NetworkHandler {
    public final SimpleNetworkWrapper wrapper;

    private int lastMessageId = -1;

    public NetworkHandler(String modId) {
        wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(modId);
    }

    public void register(Class clazz, Side handlerSide) {
        wrapper.registerMessage(clazz, clazz, ++lastMessageId, handlerSide);
    }
}
