package net.valhelsia.valhelsia_core.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.valhelsia.valhelsia_core.client.cosmetics.Cosmetic;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsCategory;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;
import net.valhelsia.valhelsia_core.client.gui.component.CloseCosmeticsWardrobeButton;
import net.valhelsia.valhelsia_core.client.gui.component.CosmeticsCategoryButton;
import net.valhelsia.valhelsia_core.client.gui.component.CosmeticsEntry;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Cosmetics Wardrobe Screen <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.gui.screen.CosmeticsWardrobeScreen
 *
 * @author Valhelsia Team
 * @version 1.17.1-0.1.2
 * @since 2021-10-14
 */
public class CosmeticsWardrobeScreen extends Screen {

    public static final ResourceLocation TEXTURE = new ResourceLocation(ValhelsiaCore.MOD_ID, "textures/gui/cosmetics_wardrobe.png");
    public static final ResourceLocation BG_IMAGE = new ResourceLocation(ValhelsiaCore.MOD_ID, "textures/gui/cosmetics_wardrobe_bg.png");

    public static final int BG_COLOR = FastColor.ARGB32.color(255, 3, 11, 20);
    public static final int LINE_COLOR = FastColor.ARGB32.color(Math.round(0.5F * 255.0F), 255, 255, 255);

    private final Screen parentScreen;
    private CosmeticsCategory activeCategory;
    private final List<CosmeticsCategoryButton> categoryButtons = new ArrayList<>();

    private CosmeticsList cosmeticsList;

    private final Map<CosmeticsCategory, Cosmetic> selectedCosmetics = new HashMap<>();

    public CosmeticsWardrobeScreen(Screen parentScreen) {
        super(new TranslatableComponent("gui.valhelsia_core.cosmeticsWardrobe"));
        this.parentScreen = parentScreen;
        for (CosmeticsCategory category : CosmeticsCategory.values()) {
            if (category.getActiveCosmetic() != null) {
                this.getSelectedCosmetics().put(category, category.getActiveCosmetic());
            }
        }
    }

    @Override
    protected void init() {
        this.getMinecraft().keyboardHandler.setSendRepeatsToGui(true);

        this.cosmeticsList = new CosmeticsList(this.getMinecraft(), this, this.width, this.height,75, this.height);

        int y = 75;

        if (this.activeCategory != null) {
            this.setActiveCategory(this.activeCategory);
        }

        for (CosmeticsCategory category : CosmeticsCategory.values()) {
            if (this.activeCategory == null) {
                this.setActiveCategory(category);
            }
            this.categoryButtons.add(this.addRenderableWidget(new CosmeticsCategoryButton(category, this.font, (int) (this.width / 24.0F), y,
                    button -> this.setActiveCategory(((CosmeticsCategoryButton) button).getCategory()),
                    this.activeCategory == category)));
            y += 15;
        }

        int i = (int) (this.width / 1.882F) + (this.width - (int) (this.width / 1.882F)) / 2;

        this.addRenderableWidget(new CloseCosmeticsWardrobeButton(i - 7 - 100, this.height - 50, 100, 35, 100, 0, TEXTURE, new TranslatableComponent("gui.valhelsia_core.cosmeticsWardrobe.save"), button -> {
            CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();
            UUID uuid = this.getMinecraft().getUser().getGameProfile().getId();
            CompoundTag tag = cosmeticsManager.getActiveCosmeticsForPlayer(uuid);

            this.getSelectedCosmetics().forEach((category, cosmetic) -> {
                cosmeticsManager.loadCosmeticTexture(cosmetic, cosmetic.getCategory());

                cosmetic.save(tag);
                cosmetic.getCategory().setActiveCosmetic(cosmetic.getName());
            });

            for (CosmeticsCategory category : CosmeticsCategory.values()) {
                if (!this.getSelectedCosmetics().containsKey(category)) {
                    tag.remove(category.getName());

                    category.setActiveCosmetic("");
                }
            }

            cosmeticsManager.setActiveCosmeticsForPlayer(uuid, tag);

            this.getMinecraft().setScreen(this.parentScreen);
        }));

        this.addRenderableWidget(new CloseCosmeticsWardrobeButton(i + 7, this.height - 50, 100, 35, 0, 0, TEXTURE, new TranslatableComponent("gui.valhelsia_core.cosmeticsWardrobe.cancel"), button -> {
            this.getMinecraft().setScreen(this.parentScreen);
        }));

        this.addWidget(this.cosmeticsList);
    }

    private void setActiveCategory(CosmeticsCategory category) {
        this.activeCategory = category;
        this.categoryButtons.forEach(cosmeticsCategoryButton -> cosmeticsCategoryButton.setSelected(cosmeticsCategoryButton.getCategory() == this.activeCategory));

        this.cosmeticsList.update(category);
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);

        int y = 51;

        // Lines
        GuiComponent.fill(poseStack, (int) (this.width / 6.3F), y, (int) (this.width / 6.3F) + 1, this.height - y, LINE_COLOR);

        //poseStack.scale(2 - this.scaleX, 2 - this.scaleY, 1.0F);
        GuiComponent.drawCenteredString(poseStack, this.font, this.getTitle(), (int) (this.width / 3.55F), 20, 16777215);

        int x = 78;
        y = 58;

        this.font.draw(poseStack, new TranslatableComponent("gui.valhelsia_core.categories"), (int) (this.width / 32.0F), y, 16777215);
        this.font.draw(poseStack, this.activeCategory.getComponent(), (int) (this.width / 5.73F), y, 16777215);

        RenderSystem.setShaderTexture(0, BG_IMAGE);

        GuiComponent.blit(poseStack, (int) (this.width / 1.882F), 0, this.width - (int) (this.width / 1.882F), this.height, 0, 0, 560, 670, 560, 670);

        this.cosmeticsList.render(poseStack, mouseX, mouseY, partialTicks);

        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void renderBackground(@Nonnull PoseStack poseStack) {
        GuiComponent.fill(poseStack, 0, 0, this.width, this.height, BG_COLOR);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button) || this.cosmeticsList.mouseClicked(mouseX, mouseY, button);
    }

    public Map<CosmeticsCategory, Cosmetic> getSelectedCosmetics() {
        return selectedCosmetics;
    }

    @Override
    public void removed() {
        this.getMinecraft().keyboardHandler.setSendRepeatsToGui(false);
    }

    @Override
    public void onClose() {
        this.getMinecraft().setScreen(this.parentScreen);
    }
}
