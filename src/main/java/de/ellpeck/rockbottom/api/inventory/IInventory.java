/*
 * This file ("IInventory.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.inventory;

import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.item.ItemInstance;

import java.util.function.BiConsumer;

public interface IInventory{

    void set(int id, ItemInstance instance);

    ItemInstance add(int id, int amount);

    ItemInstance remove(int id, int amount);

    ItemInstance get(int id);

    int getSlotAmount();

    void notifyChange(int slot);

    void addChangeCallback(BiConsumer<IInventory, Integer> callback);

    void removeChangeCallback(BiConsumer<IInventory, Integer> callback);

    ItemInstance addToSlot(int slot, ItemInstance instance, boolean simulate);

    default boolean containsResource(IUseInfo info){
        for(ItemInstance inst : info.getItems()){
            if(this.containsItem(inst)){
                return true;
            }
        }
        return false;
    }

    default boolean containsItem(ItemInstance inst){
        return this.getItemIndex(inst) >= 0;
    }

    default int getItemIndex(ItemInstance inst){
        for(int i = 0; i < this.getSlotAmount(); i++){
            ItemInstance instance = this.get(i);
            if(instance != null && instance.getAmount() >= inst.getAmount() && instance.isEffectivelyEqual(inst)){
                return i;
            }
        }
        return -1;
    }
}
