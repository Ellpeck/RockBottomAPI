/*
 * This file ("ComponentInputField.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.IInputHandler;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.assets.font.IFont;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.util.Arrays;
import java.util.function.Consumer;

public class ComponentInputField extends GuiComponent{

    private final boolean renderBox;
    private final boolean selectable;
    private final int maxLength;
    private final boolean displaxMaxLength;
    private final Consumer<String> consumer;
    private String text = "";
    private boolean isSelected;
    private int counter;
    private boolean censored;

    public ComponentInputField(Gui gui, int x, int y, int sizeX, int sizeY, boolean renderBox, boolean selectable, boolean defaultActive, int maxLength, boolean displayMaxLength){
        this(gui, x, y, sizeX, sizeY, renderBox, selectable, defaultActive, maxLength, displayMaxLength, null);
    }

    public ComponentInputField(Gui gui, int x, int y, int sizeX, int sizeY, boolean renderBox, boolean selectable, boolean defaultActive, int maxLength, boolean displayMaxLength, Consumer<String> consumer){
        super(gui, x, y, sizeX, sizeY);
        this.renderBox = renderBox;
        this.selectable = selectable;
        this.isSelected = defaultActive;
        this.maxLength = maxLength;
        this.displaxMaxLength = displayMaxLength;
        this.consumer = consumer;
    }

    public boolean isSelected(){
        return this.isSelected;
    }

    public void setSelected(boolean selected){
        this.isSelected = selected;
    }

    public ComponentInputField setCensored(boolean censored){
        this.censored = censored;
        return this;
    }

    @Override
    public boolean onKeyPressed(IGameInstance game, int button){
        if(this.isSelected){
            if(button == GLFW.GLFW_KEY_BACKSPACE){
                if(!this.text.isEmpty()){
                    this.text = this.text.substring(0, this.text.length()-1);
                    if(this.consumer != null){
                        this.consumer.accept(this.text);
                    }
                }
                return true;
            }
            else if(button == GLFW.GLFW_KEY_ESCAPE){
                if(this.selectable){
                    this.isSelected = false;
                    return true;
                }
            }
            else{
                IInputHandler input = game.getInput();
                if(input.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL) || input.isKeyDown(GLFW.GLFW_KEY_RIGHT_CONTROL)){
                    if(button == GLFW.GLFW_KEY_V){
                        if(this.text.length() < this.maxLength){
                            try{
                                this.text += (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                            }
                            catch(Exception ignored){
                            }

                            if(this.text.length() > this.maxLength){
                                this.text = this.text.substring(0, this.maxLength);
                            }

                            if(this.consumer != null){
                                this.consumer.accept(this.text);
                            }
                            return true;
                        }
                        return false;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean onCharInput(IGameInstance game, int codePoint, char[] characters){
        boolean did = false;
        if(this.isSelected){
            for(char character : characters){
                if(character >= 32 && character <= 254){
                    if(this.text.length() < this.maxLength){
                        this.text += character;
                        if(this.consumer != null){
                            this.consumer.accept(this.text);
                        }
                        did = true;
                    }
                }
            }
        }
        return did;
    }

    @Override
    public IResourceName getName(){
        return RockBottomAPI.createInternalRes("input_field");
    }

    @Override
    public void update(IGameInstance game){
        this.counter++;
    }

    public String getText(){
        return this.text;
    }

    public String getDisplayText(){
        return this.getText();
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y){
        if(this.renderBox){
            g.addFilledRect(x, y, this.width, this.height, this.isMouseOverPrioritized(game) ? getElementColor() : getUnselectedElementColor());
            g.addEmptyRect(x, y, this.width, this.height, getElementOutlineColor());
        }

        IFont font = manager.getFont();

        String text = this.getDisplayText();
        if(this.censored){
            char[] chars = new char[text.length()];
            Arrays.fill(chars, '*');
            text = new String(chars);
        }

        String display = text+(this.isSelected ? ((this.counter/15)%2 == 0 ? "|" : " ") : "");
        font.drawCutOffString(x+3, y+this.height/2F-font.getHeight(0.35F)/2F, display, 0.35F, this.width-6, true, false);

        if(this.displaxMaxLength){
            String unformattedText = font.removeFormatting(text);
            int diff = this.maxLength-unformattedText.length();
            FormattingCode format = diff <= 0 ? FormattingCode.RED : (diff <= this.maxLength/8 ? FormattingCode.ORANGE : (diff <= this.maxLength/4 ? FormattingCode.YELLOW : FormattingCode.NONE));
            font.drawStringFromRight(x+this.width-1, y+this.height-font.getHeight(0.2F), format.toString()+unformattedText.length()+"/"+this.maxLength, 0.2F);
        }
    }

    public void setText(String text){
        this.text = text;
        if(this.consumer != null){
            this.consumer.accept(text);
        }
    }

    public void append(String text){
        if(this.text.length()+text.length() <= this.maxLength){
            this.setText(this.text+text);
        }
    }

    @Override
    public boolean onMouseAction(IGameInstance game, int button, float x, float y){
        if(Settings.KEY_GUI_ACTION_1.isKey(button)){
            if(this.selectable){
                this.isSelected = this.isMouseOverPrioritized(game);
            }
        }
        else if(Settings.KEY_GUI_ACTION_2.isKey(button)){
            if(this.isMouseOverPrioritized(game)){
                this.text = "";
                if(this.consumer != null){
                    this.consumer.accept(this.text);
                }

                if(this.selectable){
                    this.isSelected = true;
                }
            }
            else{
                this.isSelected = false;
            }
        }
        return false;
    }

    @Override
    public boolean canCloseWithInvKey(){
        return !this.isSelected || !this.isActive();
    }
}
