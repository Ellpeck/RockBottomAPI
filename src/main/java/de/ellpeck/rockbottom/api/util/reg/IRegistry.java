/*
 * This file ("IRegistry.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/RockBottomGame/>.
 * View information on the project at <https://rockbottom.ellpeck.de/>.
 *
 * The RockBottomAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The RockBottomAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the RockBottomAPI. If not, see <http://www.gnu.org/licenses/>.
 *
 * © 2017 Ellpeck
 */

package de.ellpeck.rockbottom.api.util.reg;

import com.google.common.collect.BiMap;
import de.ellpeck.rockbottom.api.RockBottomAPI;

import java.util.Map;
import java.util.Set;

public interface IRegistry<T, U>{

    void register(T id, U value);

    U get(T id);

    T getId(U value);

    int getSize();

    void unregister(T id);

    BiMap<T, U> getUnmodifiable();

    Set<T> keySet();

    Set<U> values();

    Set<Map.Entry<T, U>> entrySet();

    default <V extends IRegistry> V register(){
        RockBottomAPI.registerRegistry(this);
        return (V)this;
    }
}
