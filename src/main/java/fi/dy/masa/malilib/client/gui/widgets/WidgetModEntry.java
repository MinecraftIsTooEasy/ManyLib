package fi.dy.masa.malilib.client.gui.widgets;

import fi.dy.masa.malilib.api.ManyLibApi;
import fi.dy.masa.malilib.client.api.ManyLibClientApi;
import fi.dy.masa.malilib.client.gui.DrawContext;
import fi.dy.masa.malilib.client.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.client.gui.config.ConfigDisplayApi;
import fi.dy.masa.malilib.client.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.client.internal.ManyLibClientConfig;
import fi.dy.masa.malilib.client.unsafe.ModMenuAccess;
import fi.dy.masa.malilib.client.util.RenderUtils;
import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.core.Side;
import fi.dy.masa.malilib.integration.ModReference;
import fi.dy.masa.malilib.localization.TooltipText;
import net.minecraft.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.Map;

public class WidgetModEntry extends WidgetContainer {
    private final String id;

    public WidgetModEntry(String id) {
        super(0, 0, 0, 0);
        this.id = id;
    }

    @Override
    public void init() {
        super.init();

        String id = this.id;

        int x = this.getWidth() + ScreenConstants.scrollBarXFromRight - 10;
        int y = this.getY() + ScreenConstants.listEntryMargin;


        if (ModReference.hasMod(ModReference.MOD_MENU)) {
            ResourceLocation iconTexture = ModMenuAccess.getIconTexture(this.mc, id);
            if (iconTexture != null) {
                WidgetModIcon icon = new WidgetModIcon(20, y, iconTexture);
                this.addWidget(icon);
            }
        }


        WidgetText nameText = WidgetText.of(ConfigDisplayApi.getModName(id)).position(45, ScreenConstants.commentedTextShift);
        nameText.translateTo(this);
        nameText.addTooltip(TooltipText.MOD_ID.translate(id));
        nameText.getTooltipRange().setBounds(0, this.getY(), x - 95, ScreenConstants.listEntryHeight);
        this.addWidget(nameText);

        Map<Side, IConfigHandler> sideMap = ManyLibApi.getSideMap(id);
        assert sideMap != null;


        ButtonGeneric common = ButtonGeneric.builder(
                        "U",
                        button_ -> this.mc.displayGuiScreen(ManyLibClientApi.createConfigScreen(sideMap.get(Side.COMMON), this.mc.currentScreen))
                ).dimensions(x - 95, y, 20, 20)
                .build();
        common.setEnabled(sideMap.containsKey(Side.COMMON));
        common.setTooltip(TooltipText.EDIT_COMMON_CONFIG.getKey());
        this.addWidget(common);

        ButtonGeneric client = ButtonGeneric.builder(
                        "C",
                        button_ -> this.mc.displayGuiScreen(ManyLibClientApi.createConfigScreen(sideMap.get(Side.CLIENT), this.mc.currentScreen))
                ).dimensions(x - 70, y, 20, 20)
                .build();
        client.setEnabled(sideMap.containsKey(Side.CLIENT));
        client.setTooltip(TooltipText.EDIT_CLIENT_CONFIG.getKey());
        this.addWidget(client);

        ButtonGeneric server = ButtonGeneric.builder(
                        "S",
                        button_ -> this.mc.displayGuiScreen(ManyLibClientApi.createConfigScreen(sideMap.get(Side.SERVER), this.mc.currentScreen))
                ).dimensions(x - 45, y, 20, 20)
                .build();
        server.setEnabled(sideMap.containsKey(Side.SERVER));
        server.setTooltip(TooltipText.EDIT_SERVER_CONFIG.getKey());
        this.addWidget(server);

        ButtonGeneric info = ButtonGeneric.builder(
                        "M",
                        button_ -> ModMenuAccess.directToMod(id, this.mc.currentScreen)
                ).dimensions(x - 20, y, 20, 20)
                .build();
        info.setEnabled(ModReference.hasMod(ModReference.MOD_MENU) && ModReference.hasMod(id));
        info.setTooltip(TooltipText.OPEN_IN_MOD_MENU.getKey());
        this.addWidget(info);

    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        if (drawContext.isTopLayer() && this.isMouseOver(mouseX, mouseY)) {
            RenderUtils.drawRect(this.x, this.y, this.width, this.height, ManyLibClientConfig.HighlightColor.getColorInteger());
        }
        super.render(mouseX, mouseY, selected, drawContext);
    }

    private static class WidgetModIcon extends WidgetBase {
        private final ResourceLocation icon;

        public WidgetModIcon(int x, int y, ResourceLocation icon) {
            super(x, y, 20, 20);
            this.icon = icon;
        }

        @Override
        public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
            super.render(mouseX, mouseY, selected, drawContext);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(this.icon);
            ModMenuAccess.drawTexture(this.getX(), this.getY(), 0.0F, 0.0F, this.getWidth(), this.getWidth(), this.getWidth(), this.getHeight());
        }
    }
}
