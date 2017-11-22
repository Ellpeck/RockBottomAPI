/*
 * This file ("ComponentFormatSelector.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.util.ArrayList;
import java.util.List;

public class ComponentFormatSelector extends ComponentButton{

    private final ComponentInputField inputField;
    private ComponentSelectorMenu menu;

    public ComponentFormatSelector(Gui gui, int x, int y, ComponentInputField inputField){
        super(gui, x, y, 16, 16, null, "Aa");
        this.inputField = inputField;
    }

    public void openMenu(){
        int width = 86;
        int height = 58;

        this.menu = new ComponentSelectorMenu(this.gui, this.x+this.width/2-width/2, this.y-height-2, width, height, this.inputField);
        this.gui.getComponents().add(this.menu);

        this.gui.sortComponents();
    }

    public void closeMenu(){
        this.gui.getComponents().remove(this.menu);
        this.menu.onRemoved();
        this.menu = null;

        this.gui.sortComponents();
    }

    public boolean isMenuOpen(){
        return this.menu != null;
    }

    @Override
    public boolean onKeyboardAction(IGameInstance game, int button, char character){
        if(this.menu != null){
            if(Settings.KEY_MENU.isKey(button)){
                this.closeMenu();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onMouseAction(IGameInstance game, int button, float x, float y){
        if(this.isMouseOver(game)){
            if(Settings.KEY_GUI_ACTION_1.isKey(button)){
                if(this.menu == null){
                    this.openMenu();
                    return true;
                }
            }
        }

        if(this.menu != null && !this.menu.isMouseOver(game)){
            this.closeMenu();
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public IResourceName getName(){
        return RockBottomAPI.createInternalRes("format_selector_button");
    }

    @Override
    public int getPriority(){
        return this.menu != null ? 1000 : super.getPriority();
    }

    private static class ComponentSelectorMenu extends GuiComponent{

        private final List<GuiComponent> subComponents = new ArrayList<>();

        public ComponentSelectorMenu(Gui gui, int x, int y, int width, int height, ComponentInputField inputField){
            super(gui, x, y, width, height);


            int buttonX = 2;
            int buttonY = 2;
            for(FormattingCode code : FormattingCode.getDefaultCodes().values()){
                this.subComponents.add(new ComponentButton(gui, x+buttonX, y+buttonY, 12, 12, () -> {
                    String codeStrg = code.toString();
                    if(!inputField.getText().endsWith(codeStrg)){
                        inputField.append(codeStrg);
                        return true;
                    }
                    else{
                        return false;
                    }
                }, code+"Aa", "Code: "+code.toString().replaceAll("&", "<&>")){
                    @Override
                    public int getPriority(){
                        return 2000;
                    }
                });

                buttonX += 14;
                if(buttonX >= width){
                    buttonX = 2;
                    buttonY += 14;
                }
            }

            this.gui.getComponents().addAll(this.subComponents);
        }

        public void onRemoved(){
            this.gui.getComponents().removeAll(this.subComponents);
        }

        @Override
        public IResourceName getName(){
            return RockBottomAPI.createInternalRes("format_selector_menu");
        }

        @Override
        public void render(IGameInstance game, IAssetManager manager, IGraphics g, int x, int y){
            g.fillRect(x, y, this.width, this.height, Colors.setA(Colors.BLACK, 0.65F));
            g.drawRect(x, y, this.width, this.height, Colors.BLACK);
        }
    }
}
