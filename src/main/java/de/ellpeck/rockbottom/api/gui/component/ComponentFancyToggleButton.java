/*
 * This file ("ComponentFancyToggleButton.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.util.function.Supplier;

public class ComponentFancyToggleButton extends ComponentFancyButton{

    private final IResourceName texToggled;
    private boolean isToggled;

    public ComponentFancyToggleButton(Gui gui, int x, int y, int sizeX, int sizeY, boolean defaultState, Supplier<Boolean> supplier, IResourceName texture, String... hover){
        super(gui, x, y, sizeX, sizeY, supplier, texture, hover);
        this.isToggled = defaultState;
        this.texToggled = this.texture.addSuffix("_toggled");
    }

    @Override
    protected IResourceName getTexture(){
        return this.isToggled ? this.texToggled : this.texture;
    }

    @Override
    public boolean onPressed(IGameInstance game){
        this.isToggled = !this.isToggled;
        return false;
    }

    @Override
    public IResourceName getName(){
        return RockBottomAPI.createInternalRes("fancy_toggle_button");
    }
}
