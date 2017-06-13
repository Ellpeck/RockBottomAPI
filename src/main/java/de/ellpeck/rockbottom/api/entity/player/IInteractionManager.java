/*
 * This file ("IInteractionManager.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/Ellpeck/RockBottomAPI>.
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
 */

package de.ellpeck.rockbottom.api.entity.player;

import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.world.TileLayer;

public interface IInteractionManager{

    boolean isToolEffective(AbstractEntityPlayer player, ItemInstance instance, Tile tile, TileLayer layer, int x, int y);

    TileLayer getBreakingLayer();

    int getBreakTileX();

    int getBreakTileY();

    float getBreakProgress();

    int getPlaceCooldown();

    int getMousedTileX();

    int getMousedTileY();
}
