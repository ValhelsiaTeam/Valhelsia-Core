package net.valhelsia.valhelsia_core.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.client.Cosmetic;
import net.valhelsia.valhelsia_core.client.CosmeticsCategory;
import net.valhelsia.valhelsia_core.client.CosmeticsManager;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Cosmetics Wardrobe Screen <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.screen.CosmeticsWardrobeScreen
 *
 * @author Valhelsia Team
 * @version 16.0.13
 * @since 2021-10-26
 */
public class CosmeticsWardrobeScreen extends Screen {

    public static final ResourceLocation TEXTURE = new ResourceLocation(ValhelsiaCore.MOD_ID, "textures/gui/cosmetics_wardrobe.png");
    public static final ResourceLocation BG_IMAGE = new ResourceLocation(ValhelsiaCore.MOD_ID, "textures/gui/cosmetics_wardrobe_bg.png");

    public static final int BG_COLOR = ColorHelper.PackedColor.packColor(255, 3, 11, 20);
    public static final int LINE_COLOR = ColorHelper.PackedColor.packColor(Math.round(0.5F * 255.0F), 255, 255, 255);

    private final Screen parentScreen;
    private CosmeticsCategory activeCategory;
    private final List<CosmeticsCategoryButton> categoryButtons = new ArrayList<>();

    private CosmeticsList cosmeticsList;

    private final Map<CosmeticsCategory, Cosmetic> selectedCosmetics = new HashMap<>();

    public CosmeticsWardrobeScreen(Screen parentScreen) {
        super(new TranslationTextComponent("gui.valhelsia_core.cosmeticsWardrobe"));
        this.parentScreen = parentScreen;
        for (CosmeticsCategory category : CosmeticsCategory.values()) {
            if (category.getActiveCosmetic() != null) {
                this.getSelectedCosmetics().put(category, category.getActiveCosmetic());
            }
        }
    }

    @Override
    protected void init() {
        this.getMinecraft().keyboardListener.enableRepeatEvents(true);

        this.cosmeticsList = new CosmeticsList(this.getMinecraft(), this, this.width, this.height,75, this.height);

        int y = 75;

        if (this.activeCategory != null) {
            this.setActiveCategory(this.activeCategory);
        }

        for (CosmeticsCategory category : CosmeticsCategory.values()) {
            if (this.activeCategory == null) {
                this.setActiveCategory(category);
            }
            this.categoryButtons.add(this.addButton(new CosmeticsCategoryButton(category, this.font, (int) (this.width / 24.0F), y,
                    button -> this.setActiveCategory(((CosmeticsCategoryButton) button).getCategory()),
                    this.activeCategory == category)));
            y += 15;
        }

        int i = (int) (this.width / 1.882F) + (this.width - (int) (this.width / 1.882F)) / 2;

        this.addButton(new CloseCosmeticsWardrobeButton(i - 7 - 100, this.height - 50, 100, 35, 100, 0, TEXTURE, new TranslationTextComponent("gui.valhelsia_core.cosmeticsWardrobe.save"), button -> {
            CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();
            UUID uuid = this.getMinecraft().getSession().getProfile().getId();
            CompoundNBT tag = cosmeticsManager.getActiveCosmeticsForPlayer(uuid);

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

            this.getMinecraft().displayGuiScreen(this.parentScreen);
        }));

        this.addButton(new CloseCosmeticsWardrobeButton(i + 7, this.height - 50, 100, 35, 0, 0, TEXTURE, new TranslationTextComponent("gui.valhelsia_core.cosmeticsWardrobe.cancel"), button -> {
            this.getMinecraft().displayGuiScreen(this.parentScreen);
        }));

        this.children.add(this.cosmeticsList);
    }

    private void setActiveCategory(CosmeticsCategory category) {
        this.activeCategory = category;
        this.categoryButtons.forEach(cosmeticsCategoryButton -> cosmeticsCategoryButton.setSelected(cosmeticsCategoryButton.getCategory() == this.activeCategory));

        this.cosmeticsList.update(category);
    }

    @Override
    public void render(@Nonnull MatrixStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);

        int y = 51;

        // Lines
        AbstractGui.fill(poseStack, (int) (this.width / 6.3F), y, (int) (this.width / 6.3F) + 1, this.height - y, LINE_COLOR);

        AbstractGui.drawCenteredString(poseStack, this.font, this.getTitle(), (int) (this.width / 3.55F), 20, 16777215);

        y = 58;

        this.font.func_238407_a_ (poseStack, new TranslationTextComponent("gui.valhelsia_core.categories").func_241878_f(), (int) (this.width / 32.0F), y, 16777215);
        this.font.func_238407_a_(poseStack, this.activeCategory.getComponent().func_241878_f(), (int) (this.width / 5.73F), y, 16777215);

        this.getMinecraft().getTextureManager().bindTexture(BG_IMAGE);
        AbstractGui.blit(poseStack, (int) (this.width / 1.882F), 0, this.width - (int) (this.width / 1.882F), this.height, 0, 0, 560, 670, 560, 670);

        this.cosmeticsList.render(poseStack, mouseX, mouseY, partialTicks);

        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void renderBackground(@Nonnull MatrixStack poseStack) {
        AbstractGui.fill(poseStack, 0, 0, this.width, this.height, BG_COLOR);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button) || this.cosmeticsList.mouseClicked(mouseX, mouseY, button);
    }

    public Map<CosmeticsCategory, Cosmetic> getSelectedCosmetics() {
        return selectedCosmetics;
    }

    @Override
    public void closeScreen() {
        this.getMinecraft().displayGuiScreen(this.parentScreen);
    }

    @Override
    public void onClose() {
        this.getMinecraft().keyboardListener.enableRepeatEvents(false);
    }
}
