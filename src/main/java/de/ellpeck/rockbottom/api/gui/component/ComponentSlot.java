/*
 * This file ("ComponentSlot.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.gui.component;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.container.ContainerSlot;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class ComponentSlot extends GuiComponent{

    public final ContainerSlot slot;
    public final int componentId;
    public final GuiContainer container;

    public ComponentSlot(GuiContainer container, ContainerSlot slot, int componentId, int x, int y){
        super(container, x, y, 18, 18);
        this.container = container;
        this.slot = slot;
        this.componentId = componentId;
    }

    @Override
    public boolean onMouseAction(IGameInstance game, int button, float x, float y){
        return RockBottomAPI.getApiHandler().doDefaultSlotMovement(game, button, x, y, this);
    }

    @Override
    public IResourceName getName(){
        return RockBottomAPI.createInternalRes("slot");
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IGraphics g, int x, int y){
        g.renderSlotInGui(game, manager, this.slot.get(), x, y, 1F, this.isMouseOver(game));
    }

    @Override
    public void renderOverlay(IGameInstance game, IAssetManager manager, IGraphics g, int x, int y){
        if(this.container.holdingInst == null && this.isMouseOverPrioritized(game)){
            ItemInstance instance = this.slot.get();
            if(instance != null){
                g.describeItem(game, manager, instance);
            }
        }
    }
}
