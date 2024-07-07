package fi.dy.masa.malilib.gui.screen;

import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.config.interfaces.IConfigResettable;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.gui.button.PageButton;
import fi.dy.masa.malilib.gui.button.ResetButton;
import fi.dy.masa.malilib.gui.button.interfaces.GuiButtonCommented;
import fi.dy.masa.malilib.gui.button.interfaces.ICommentedElement;
import fi.dy.masa.malilib.gui.screen.interfaces.GuiScreenPaged;
import fi.dy.masa.malilib.util.KeyCodes;
import net.minecraft.*;
import org.lwjgl.input.Keyboard;

import java.util.List;
import java.util.Objects;

public final class HotKeyScreen extends GuiScreenPaged {
    private int buttonIDToChangeKey = -1;
    private final List<ConfigHotkey> hotkeys;
    private final IConfigHandler configInstance;
    public static final int buttonWidth = 50;
    private ResetButton resetAllButton;

    public HotKeyScreen(GuiScreen parentScreen, String screenTitle, IConfigHandler configInstance, List<ConfigHotkey> hotkeys) {
        super(parentScreen, screenTitle, 7, 2);
        Objects.requireNonNull(hotkeys);
        this.configInstance = configInstance;
        if (hotkeys.size() > this.pageCapacity) {
            this.columns = 3;
            this.pageCapacity = this.rows * this.columns;
        }
        this.updatePageCount(hotkeys.size());
        this.hotkeys = hotkeys;
    }

    @Override
    protected int getLeftBorder() {
        return this.columns == 3 ? this.width / 2 - 165 : this.width / 2 - 125;
    }

    @Override
    protected int getButtonPosX(int index) {
        index %= pageCapacity;
        return this.getLeftBorder() + index % columns * (this.columns == 3 ? 100 : 160);
    }

    @Override
    public void initGui() {
        for (int index = 0; index < this.hotkeys.size(); ++index) {
            this.buttonList.add(this.getHotKeyButton(index, this.getButtonPosX(index), this.getButtonPosY(index), this.hotkeys.get(index)));
        }
        this.setVisibilities();
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.getString("gui.done")));
        this.resetAllButton = new ResetButton(201, this.width / 2 + 110, this.height / 6 + 168);
        this.resetAllButton.setComment(I18n.getString("manyLib.gui.button.reset_all"));
        this.buttonList.add(resetAllButton);
        if (this.pageCount > 0) {
            this.buttonList.add(new PageButton(202, this.width / 2 + 132, this.height / 6 + 168, false));
            this.buttonList.add(new PageButton(203, this.width / 2 + 154, this.height / 6 + 168, true));
        }
    }

    @Override
    public void setVisibilities() {
        for (int i = 0; i < hotkeys.size(); ++i) {
            ((GuiButton) this.buttonList.get(i)).drawButton = this.isVisible(i);
        }
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton) {
        int id = par1GuiButton.id;
        switch (id) {
            case 200 -> this.leaveThisScreen();
            case 201 -> {
                String question = I18n.getString("manyLib.gui.reset_hotkeys_question");
                String yes = I18n.getString("gui.yes");
                String no = I18n.getString("gui.no");
                GuiYesNoMITE var3 = new GuiYesNoMITE(this, question, this.configInstance.getName(), yes, no, 1);
                this.mc.displayGuiScreen(var3);
            }
            case 202 -> this.scroll(false);
            case 203 -> this.scroll(true);
            default -> {
                this.buttonIDToChangeKey = id;
                par1GuiButton.displayString = "> " + getOptionDisplayString(id) + " <";
            }
        }
    }

    @Override
    public void updateScreen() {
        this.resetAllButton.enabled = this.hotkeys.stream().anyMatch(ConfigHotkey::isModified);
    }

    @Override
    public void confirmClicked(boolean par1, int par2) {
        if (par1) {
            if (par2 == 1) {
                this.hotkeys.forEach(IConfigResettable::resetToDefault);
                this.configInstance.save();
            }
        }
        this.mc.displayGuiScreen(this);
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        if (this.buttonIDToChangeKey >= 0) {
            setKeyBinding(this.buttonIDToChangeKey, -100 + par3);
            ((GuiButton) this.buttonList.get(this.buttonIDToChangeKey)).displayString = getOptionDisplayString(this.buttonIDToChangeKey);
            this.buttonIDToChangeKey = -1;
        } else {
            super.mouseClicked(par1, par2, par3);
        }
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        if (this.buttonIDToChangeKey >= 0) {
            setKeyBinding(this.buttonIDToChangeKey, par2);
            ((GuiButton) this.buttonList.get(this.buttonIDToChangeKey)).displayString = getOptionDisplayString(this.buttonIDToChangeKey);
            this.buttonIDToChangeKey = -1;
        } else {
            super.keyTyped(par1, par2);
        }
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 14, 16777215);

        for (int index = 0; index < this.hotkeys.size(); index++) {
            if (!this.isVisible(index)) continue;
            GuiButton hotKeyButton = (GuiButton) this.buttonList.get(index);
            if (this.buttonIDToChangeKey == index) {
                hotKeyButton.displayString = EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + "??? " + EnumChatFormatting.WHITE + "<";
            } else if (this.checkDuplicate(index)) {
                hotKeyButton.displayString = EnumChatFormatting.RED + getOptionDisplayString(index);
            } else {
                hotKeyButton.displayString = getOptionDisplayString(index);
            }
            this.drawName(index);
        }
        super.drawDefaultScreen(i, j, f);
        this.buttonList.stream().filter(x -> x instanceof ICommentedElement)
                .anyMatch(x -> ((ICommentedElement) x).tryDrawComment(this, i, j));// TODO
    }

    private boolean checkDuplicate(final int index) {
        final int keyCode = this.hotkeys.get(index).getKeyCode();
        if (keyCode == KeyCodes.KEY_NONE) return false;
        for (int var7 = 0; var7 < hotkeys.size() && var7 != index; ++var7) {
            if (keyCode == hotkeys.get(var7).getKeyCode()) return true;
        }
        for (int var7 = 0; var7 < this.mc.gameSettings.keyBindings.length; ++var7) {
            if (keyCode == this.mc.gameSettings.keyBindings[var7].keyCode) return true;
        }
        return false;
    }

    private void setKeyBinding(int index, int keyCode) {
        hotkeys.get(index).setHotKeyWithoutHash(keyCode < 0 ? KeyCodes.KEY_NONE : keyCode);
    }

    private String getOptionDisplayString(int par1) {
        int var2 = hotkeys.get(par1).getKeyCode();
        return Keyboard.getKeyName(var2);
    }

    private void drawName(int index) {
        this.drawString(this.fontRenderer, hotkeys.get(index).getConfigGuiDisplayName(), this.getButtonPosX(index) + buttonWidth + 2, this.getButtonPosY(index) + 7, -1);
    }

    @Override
    public void onGuiClosed() {
        KeyBinding.resetKeyBindingArrayAndHash();
        this.configInstance.save();
    }

    private GuiButton getHotKeyButton(int index, int x, int y, ConfigHotkey configHotkey) {
        if (configHotkey.getComment() == null) {
            return new GuiButton(index, x, y, buttonWidth, 20, "");
        } else {
            return new GuiButtonCommented(index, x, y, buttonWidth, 20, "", configHotkey.getConfigGuiDisplayComment());
        }
    }
}

