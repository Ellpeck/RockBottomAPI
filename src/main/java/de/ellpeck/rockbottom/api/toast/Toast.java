/*
 * This file ("Toast.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.toast;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.font.IFont;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.assets.ITexture;
import de.ellpeck.rockbottom.api.gui.component.GuiComponent;
import de.ellpeck.rockbottom.api.net.chat.component.ChatComponent;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class Toast{

    private final IResourceName icon;
    private final ChatComponent title;
    private final ChatComponent description;
    private final int displayTime;

    public Toast(ChatComponent title, ChatComponent description, int displayTime){
        this(null, title, description, displayTime);
    }

    public Toast(IResourceName icon, ChatComponent title, ChatComponent description, int displayTime){
        this.icon = icon;
        this.title = title;
        this.description = description;
        this.displayTime = displayTime;
    }

    public void render(IGameInstance game, IAssetManager manager, IGraphics g, float x, float y){
        float width = this.getWidth();
        float height = this.getHeight();

        g.fillRect(x, y, width, height, GuiComponent.getElementColor());
        g.drawRect(x, y, width, height, GuiComponent.getElementOutlineColor());

        float textX = x;
        float textWidth = width;

        if(this.icon != null){
            float size = height-2;

            textX += size+1;
            textWidth -= size+1;

            ITexture tex = manager.getTexture(this.icon);
            tex.draw(x+1, y+1, size, ((float)tex.getWidth()/(float)tex.getHeight())*size);
        }

        IFont font = manager.getFont();
        font.drawString(textX+1, y+1, this.getTitle().getDisplayWithChildren(game, manager), 0.3F);
        font.drawSplitString(textX+1, y+8, FormattingCode.LIGHT_GRAY+this.getDescription().getDisplayWithChildren(game, manager), 0.25F, (int)textWidth-2);
    }

    public ChatComponent getTitle(){
        return this.title;
    }

    public ChatComponent getDescription(){
        return this.description;
    }

    public int getDisplayTime(){
        return this.displayTime;
    }

    public float getHeight(){
        return 20F;
    }

    public float getWidth(){
        return 90F;
    }
}
