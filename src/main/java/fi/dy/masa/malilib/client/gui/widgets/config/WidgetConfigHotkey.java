package fi.dy.masa.malilib.client.gui.widgets.config;

import fi.dy.masa.malilib.client.config.options.ConfigHotkey;
import fi.dy.masa.malilib.client.event.InputEventHandler;
import fi.dy.masa.malilib.client.gui.DrawContext;
import fi.dy.masa.malilib.client.gui.GuiBase;
import fi.dy.masa.malilib.client.gui.button.ButtonBase;
import fi.dy.masa.malilib.client.gui.config.ConfigDisplayApi;
import fi.dy.masa.malilib.client.gui.layer.KeySettingsLayer;
import fi.dy.masa.malilib.client.gui.screen.LayeredScreen;
import fi.dy.masa.malilib.client.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.client.input.IHotkey;
import fi.dy.masa.malilib.client.input.IKeybind;
import fi.dy.masa.malilib.client.input.KeybindCategory;
import fi.dy.masa.malilib.client.util.RenderUtils;
import fi.dy.masa.malilib.localization.KeybindSettingsText;

import java.util.ArrayList;
import java.util.List;

public class WidgetConfigHotkey extends WidgetConfig<ConfigHotkey> {
    protected final List<String> overlapInfo = new ArrayList<>();
    boolean editing;
    final IKeybind keybind;

    final ButtonBase hotkeyButton;
    final ButtonBase keySettingButton;

    public WidgetConfigHotkey(ConfigHotkey config) {
        super(config);
        this.keybind = config.getKeybind();

        this.hotkeyButton = ScreenConstants.getHotkeyButton(this.config, button -> {
            this.editing = true;
            this.keybind.clearKeys();
        });
        this.hotkeyButton.setHoverInfoRequiresShift(true);
        this.addWidget(this.hotkeyButton);

        this.keySettingButton = ScreenConstants.getKeySettingButton(
                button -> ((LayeredScreen) this.mc.currentScreen).toggleLayer(
                        layer -> layer instanceof KeySettingsLayer,
                        () -> new KeySettingsLayer(this.mc.currentScreen,
                                this.keybind)
                )
        );
        this.addWidget(this.keySettingButton);
    }

    @Override
    public void init() {
        super.init();

        ScreenConstants.placeHotkeyButton(this, this.hotkeyButton);
        ScreenConstants.placeKeySettingButton(this, this.keySettingButton);
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        this.updateDisplayStringByKeybind();
        super.render(mouseX, mouseY, selected, drawContext);
    }

    @Override
    public void postRenderHovered(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.postRenderHovered(mouseX, mouseY, selected, drawContext);
        if (this.hotkeyButton.isMouseOver() && this.hotkeyButton.hasHoverText()) {
            RenderUtils.renderTooltip(mouseX, mouseY, this.hotkeyButton.getHoverStrings(), drawContext);
        }
        if (this.keySettingButton.isMouseOver()) {
            List<String> strings = new ArrayList<>();
            strings.add(KeybindSettingsText.SETTINGS.translate() + ":");
            strings.addAll(this.keybind.getSettings().toStringList());
            RenderUtils.renderTooltip(mouseX, mouseY, strings, drawContext);
        }
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
        if (super.onMouseClickedImpl(mouseX, mouseY, mouseButton)) return true;
        if (this.editing && !this.hotkeyButton.isMouseOver()) {
            this.editing = false;
            return true;
        }
        return false;
    }

    @Override
    protected boolean onCharTypedImpl(char charIn, int modifiers) {
        if (!this.editing) return false;
        if (modifiers == 1 || modifiers == 28 || modifiers == 156) {// esc and two enters
            this.editing = false;
            return true;
        }
        this.keybind.addKey(modifiers);
        return true;
    }

    @Override
    protected void onMouseReleasedImpl(int mouseX, int mouseY, int mouseButton) {
        super.onMouseReleasedImpl(mouseX, mouseY, mouseButton);
        if (!this.hotkeyButton.isMouseOver()) {
            this.editing = false;
        }
    }

    private void updateDisplayStringByKeybind() {
        this.updateConflicts();
        String string = this.keybind.getKeysDisplayString();
        if (string.isEmpty()) {
            string = "NONE";
        }
        if (this.editing) {
            string = GuiBase.TXT_YELLOW + "> " + string + " <";
        } else {
            if (!this.overlapInfo.isEmpty()) {
                string = GuiBase.TXT_GOLD + string;
            }
        }
        this.hotkeyButton.setDisplayString(string);
    }

    protected void updateConflicts() {
        List<KeybindCategory> categories = InputEventHandler.getKeybindManager().getKeybindCategories();
        List<IHotkey> overlaps = new ArrayList<>();
        this.overlapInfo.clear();

        for (KeybindCategory category : categories) {
            List<? extends IHotkey> hotkeys = category.getHotkeys();

            for (IHotkey hotkey : hotkeys) {
                if (this.keybind.overlaps(hotkey.getKeybind())) {
                    overlaps.add(hotkey);
                }
            }

            if (overlaps.size() > 0) {
                if (this.overlapInfo.size() > 0) {
                    this.overlapInfo.add("-----");
                }

                this.overlapInfo.add(category.getModName());
                this.overlapInfo.add(" > " + category.getCategory());

                for (IHotkey overlap : overlaps) {
                    String key = " [ " + GuiBase.TXT_GOLD + overlap.getKeybind().getKeysDisplayString() + GuiBase.TXT_RST + " ]";
                    this.overlapInfo.add("    - " + ConfigDisplayApi.getConfigDisplayName(overlap) + key);
                }

                overlaps.clear();
            }
        }

        this.hotkeyButton.setHoverStrings(this.overlapInfo);

    }
}
