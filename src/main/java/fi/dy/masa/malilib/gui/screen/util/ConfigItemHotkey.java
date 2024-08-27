package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.config.options.ConfigEnum;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.ButtonWidget;
import fi.dy.masa.malilib.gui.button.PeriodicButton;
import fi.dy.masa.malilib.gui.button.interfaces.IButtonPeriodic;
import fi.dy.masa.malilib.gui.screen.DefaultConfigScreen;
import fi.dy.masa.malilib.hotkeys.EnumKeybindSettingsPreSet;
import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeybindCategory;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.GuiScreen;

import java.util.ArrayList;
import java.util.List;

class ConfigItemHotkey extends ConfigItem<ConfigHotkey> {
    ButtonWidget hotkeyButton;
    PeriodicButton<?> keyActionButton;
    ConfigEnum<EnumKeybindSettingsPreSet> dummy;
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
        EnumKeybindSettingsPreSet mapped = EnumKeybindSettingsPreSet.mapToEnum(this.keybind.getSettings());
        if (mapped == null) mapped = EnumKeybindSettingsPreSet.DEFAULT;
        this.dummy = new ConfigEnum<>("dummy", mapped);
        this.keyActionButton = ScreenConstants.getKeySettingButton(index, this.dummy, screen, button -> {
            ((IButtonPeriodic) button).next();
            this.keybind.setSettings(this.dummy.getEnumValue().keybindSettings);
        });
        this.buttons.add(this.keyActionButton);
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
    public void customDraw(GuiScreen guiScreen, int x, int y) {
    }

    @Override
    public void tryDrawComment(GuiScreen guiScreen, int x, int y) {
        super.tryDrawComment(guiScreen, x, y);
        if (this.overlapInfo.isEmpty() == false && this.hotkeyButton.func_82252_a()) {
            RenderUtils.drawTextList(guiScreen, this.overlapInfo, x, y);
        }
        if (this.keyActionButton.func_82252_a()) {
            List<String> strings = new ArrayList<>();
            strings.add(StringUtils.translate("manyLib.keybind.settings") + ":");
            strings.addAll(this.dummy.getEnumValue().keybindSettings.toStringList());
            RenderUtils.drawTextList(guiScreen, strings, x, y);
        }
    }

    @Override
    public void customMouseClicked(GuiScreen guiScreen, int mouseX, int mouseY, int click) {
        if (this.editing && !this.hotkeyButton.func_82252_a()) {
            this.save();
        }
    }

    @Override
    public void resetButtonClicked() {
        this.updateDisplayStringByKeybind();
        this.setDummyByKeyBind();
        this.keyActionButton.updateString();
    }

    private void setDummyByKeyBind() {
        EnumKeybindSettingsPreSet mapped = EnumKeybindSettingsPreSet.mapToEnum(this.keybind.getSettings());
        if (mapped == null) mapped = EnumKeybindSettingsPreSet.DEFAULT;
        this.dummy.setEnumValue(mapped);
    }

    @Override
    public void customSetVisible(boolean visible) {

    }

    @Override
    public void keyTyped(char c, int i) {
        super.keyTyped(c, i);
        if (!this.editing) return;
        if (i == 1 || i == 28 || i == 156) {// esc and two enters
            this.save();
            if (i == 1) {
                ((DefaultConfigScreen) this.screen).markCancelKeyType();
            }
            return;
        }
        this.keybind.addKey(i);
        this.updateDisplayStringByKeybind();
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
            if (this.overlapInfo.isEmpty() == false) {
                string = GuiBase.TXT_GOLD + string;
            }
        }
        this.hotkeyButton.displayString = string;
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
                    this.overlapInfo.add("    - " + overlap.getConfigGuiDisplayName() + key);
                }

                overlaps.clear();
            }
        }
    }
}
