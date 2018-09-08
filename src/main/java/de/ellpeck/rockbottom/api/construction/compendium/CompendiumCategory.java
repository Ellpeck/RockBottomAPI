/*
 * This file ("CompendiumCategory.java") is part of the RockBottomAPI by Ellpeck.
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
 * © 2018 Ellpeck
 */

package de.ellpeck.rockbottom.api.construction.compendium;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.ComponentMenu;
import de.ellpeck.rockbottom.api.gui.component.construction.ComponentConstruct;
import de.ellpeck.rockbottom.api.gui.component.construction.ComponentIngredient;
import de.ellpeck.rockbottom.api.gui.component.construction.ComponentPolaroid;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.List;
import java.util.Set;

public abstract class CompendiumCategory {

    protected final ResourceName name;

    public CompendiumCategory(ResourceName name) {
        this.name = name;
    }

    public Gui getGuiOverride(Gui parent) {
        return null;
    }

    public void onGuiOrganized(Gui gui, ComponentMenu menu, List<ComponentPolaroid> polaroids, List<ComponentIngredient> ingredients, ComponentConstruct construct) {

    }

    public int getMaxIngredientAmount(Gui gui, List<ComponentIngredient> ingredients) {
        return 8;
    }

    public Pos2 getIngredientPosition(Gui gui, ComponentIngredient ingredient, int index) {
        return new Pos2(78 + (index % 4) * 16, 51 + (index / 4) * 19);
    }

    public ResourceName getBackgroundPicture(Gui gui, IAssetManager manager) {
        return ResourceName.intern("gui.construction.page_recipes");
    }

    public boolean shouldDisplay(AbstractEntityPlayer player) {
        return true;
    }

    public String getDisplayText(IAssetManager manager) {
        return manager.localize(this.getName().addPrefix("info.compendium_category."));
    }

    public abstract ResourceName getIcon(IGameInstance game, IAssetManager assetManager, IRenderer g);

    public abstract Set<? extends ICompendiumRecipe> getRecipes();

    public ResourceName getName() {
        return this.name;
    }

    public CompendiumCategory register() {
        Registries.COMPENDIUM_CATEGORY_REGISTRY.register(this.getName(), this);
        return this;
    }
}