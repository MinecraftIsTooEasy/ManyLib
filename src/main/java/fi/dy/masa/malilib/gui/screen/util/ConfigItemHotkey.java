package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.screen.KeySettingsScreen;
import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeybindCategory;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.GuiScreen;

import java.util.ArrayList;
import java.util.List;

class ConfigItemHotkey extends ConfigItem<ConfigHotkey> {
    ButtonBase hotkeyButton;
    ButtonBase jumpToSettings;
    protected final List<String> overlapInfo = new ArrayList<>();
    boolean editing;
    IKeybind keybind;

    public ConfigItemHotkey(int index, ConfigHotkey config, GuiScreen screen) {
        super(index, config, screen);
        this.keybind = config.getKeybind();
        this.addHotKeyButton(index);
        this.addKeybindSettingsButton(index);
        this.updateDisplayStringByKeybind();
    }

    private void addKeybindSettingsButton(int index) {
        this.jumpToSettings = ScreenConstants.getJumpButton(index, this.screen, button -> this.screen.mc.displayGuiScreen(new KeySettingsScreen(this.screen, this.config.getConfigGuiDisplayName(), this.keybind)));
        this.buttons.add(this.jumpToSettings);
    }

    protected void addHotKeyButton(int index) {
        this.hotkeyButton = ScreenConstants.getHotkeyButton(index, this.config, this.screen, button -> {
            this.editing = true;
            this.keybind.clearKeys();
            this.updateDisplayStringByKeybind();
        });
        this.buttons.add(this.hotkeyButton);
    }

    @Override
    public void postRenderHovered(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.postRenderHovered(mouseX, mouseY, selected, drawContext);
        if (this.overlapInfo != null && !this.overlapInfo.isEmpty() && this.hotkeyButton.isMouseOver()) {
            RenderUtils.drawTextList(GuiScreen.isShiftKeyDown() ? this.overlapInfo : List.of(StringUtils.translate("manyLib.gui.button.hover.hold_shift_for_info")), mouseX, mouseY, drawContext);
        }
        if (this.jumpToSettings.isMouseOver()) {
            List<String> strings = new ArrayList<>();
            strings.add(StringUtils.translate("manyLib.keybind.settings") + ":");
            strings.addAll(this.keybind.getSettings().toStringList());
            RenderUtils.drawTextList(strings, mouseX, mouseY, drawContext);
        }
    }

    @Override
    public void update() {
        super.update();
        this.updateConflicts();
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
        if (super.onMouseClickedImpl(mouseX, mouseY, mouseButton)) return true;
        if (this.editing && !this.hotkeyButton.isMouseOver()) {
            this.save();
            return true;
        }
        return false;
    }

    @Override
    public void resetButtonClicked() {
        this.updateDisplayStringByKeybind();
    }

    @Override
    protected boolean onCharTypedImpl(char charIn, int modifiers) {
        if (!this.editing) return false;
        if (modifiers == 1 || modifiers == 28 || modifiers == 156) {// esc and two enters
            this.save();
            return true;
        }
        this.keybind.addKey(modifiers);
        this.updateDisplayStringByKeybind();
        return true;
    }

    private void save() {
        this.editing = false;
        this.updateDisplayStringByKeybind();
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
            if (this.overlapInfo != null && !this.overlapInfo.isEmpty()) {
                string = GuiBase.TXT_GOLD + string;
            }
        }
        this.hotkeyButton.setDisplayString(string);
    }

    protected void updateConflicts() {
        List<KeybindCategory> categories = InputEventHandler.getKeybindManager().getKeybindCategories();
        List<IHotkey> overlaps = new ArrayList<>();
        if (this.overlapInfo == null) {
            return;
        }
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
                    this.overlapInfo.add("    - " + overlap.getConfigGuiDisplayName() + key);
                }

                overlaps.clear();
            }
        }
    }
}
