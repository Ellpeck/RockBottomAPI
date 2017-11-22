/*
 * This file ("IRetroactiveGenerator.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.world.gen;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;

public interface IRetroactiveGenerator extends IWorldGenerator{

    @Override
    default boolean shouldGenerate(IWorld world, IChunk chunk){
        return (!chunk.hasAdditionalData() || !chunk.getAdditionalData().getBoolean(RockBottomAPI.WORLD_GENERATORS.getId(this.getClass()).toString())) && this.shouldGenerateRetroactively(world, chunk);
    }

    @Override
    default void generate(IWorld world, IChunk chunk){
        chunk.getOrCreateAdditionalData().addBoolean(RockBottomAPI.WORLD_GENERATORS.getId(this.getClass()).toString(), true);
        this.generateRetroactively(world, chunk);
    }

    boolean shouldGenerateRetroactively(IWorld world, IChunk chunk);

    void generateRetroactively(IWorld world, IChunk chunk);
}
