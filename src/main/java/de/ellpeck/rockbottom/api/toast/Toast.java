package de.ellpeck.rockbottom.api.toast;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.font.Font;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.assets.tex.Texture;
import de.ellpeck.rockbottom.api.net.chat.component.ChatComponent;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Toast{

    private final Color guiColor = new Color(RockBottomAPI.getGame().getSettings().guiColor);
    private final Color color = this.guiColor.multiply(new Color(1F, 1F, 1F, 0.5F));
    private final Color colorOutline = this.guiColor.darker(0.25F);

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

    public void render(IGameInstance game, IAssetManager manager, Graphics g, float x, float y){
        float width = this.getWidth();
        float height = this.getHeight();

        g.setColor(this.color);
        g.fillRect(x, y, width, height);

        g.setColor(this.colorOutline);
        g.drawRect(x, y, width, height);

        float textX = x;
        float textWidth = width;

        if(this.icon != null){
            float size = height-2;

            textX += size+1;
            textWidth -= size+1;

            Texture tex = manager.getTexture(this.icon);
            tex.draw(x+1, y+1, size, ((float)tex.getWidth()/(float)tex.getHeight())*size);
        }

        Font font = manager.getFont();
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
