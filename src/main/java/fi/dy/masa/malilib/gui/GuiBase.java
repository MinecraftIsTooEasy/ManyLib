package fi.dy.masa.malilib.gui;

import fi.dy.masa.malilib.config.interfaces.IConfigBase;
import fi.dy.masa.malilib.gui.Message.MessageType;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.interfaces.IMessageConsumer;
import fi.dy.masa.malilib.gui.interfaces.ITextFieldListener;
import fi.dy.masa.malilib.gui.widgets.WidgetBase;
import fi.dy.masa.malilib.gui.widgets.WidgetLabel;
import fi.dy.masa.malilib.gui.widgets.WidgetTextField;
import fi.dy.masa.malilib.gui.wrappers.TextFieldWrapper;
import fi.dy.masa.malilib.interfaces.IStringConsumer;
import fi.dy.masa.malilib.render.MessageRenderer;
import fi.dy.masa.malilib.render.RenderUtils;
import net.minecraft.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class GuiBase extends GuiScreen implements IMessageConsumer, IStringConsumer {
    public static final String TXT_AQUA = EnumChatFormatting.AQUA.toString();
    public static final String TXT_BLACK = EnumChatFormatting.BLACK.toString();
    public static final String TXT_BLUE = EnumChatFormatting.BLUE.toString();
    public static final String TXT_GOLD = EnumChatFormatting.GOLD.toString();
    public static final String TXT_GRAY = EnumChatFormatting.GRAY.toString();
    public static final String TXT_GREEN = EnumChatFormatting.GREEN.toString();
    public static final String TXT_RED = EnumChatFormatting.RED.toString();
    public static final String TXT_WHITE = EnumChatFormatting.WHITE.toString();
    public static final String TXT_YELLOW = EnumChatFormatting.YELLOW.toString();

    public static final String TXT_BOLD = EnumChatFormatting.BOLD.toString();
    public static final String TXT_ITALIC = EnumChatFormatting.ITALIC.toString();
    public static final String TXT_RST = EnumChatFormatting.RESET.toString();
    public static final String TXT_STRIKETHROUGH = EnumChatFormatting.STRIKETHROUGH.toString();
    public static final String TXT_UNDERLINE = EnumChatFormatting.UNDERLINE.toString();

    public static final String TXT_DARK_AQUA = EnumChatFormatting.DARK_AQUA.toString();
    public static final String TXT_DARK_BLUE = EnumChatFormatting.DARK_BLUE.toString();
    public static final String TXT_DARK_GRAY = EnumChatFormatting.DARK_GRAY.toString();
    public static final String TXT_DARK_GREEN = EnumChatFormatting.DARK_GREEN.toString();
    public static final String TXT_DARK_PURPLE = EnumChatFormatting.DARK_PURPLE.toString();
    public static final String TXT_DARK_RED = EnumChatFormatting.DARK_RED.toString();

    public static final String TXT_LIGHT_PURPLE = EnumChatFormatting.LIGHT_PURPLE.toString();

    protected static final String BUTTON_LABEL_ADD = TXT_DARK_GREEN + "+" + TXT_RST;
    protected static final String BUTTON_LABEL_REMOVE = TXT_DARK_RED + "-" + TXT_RST;

    public static final int COLOR_WHITE = 0xFFFFFFFF;
    public static final int TOOLTIP_BACKGROUND = 0xB0000000;
    public static final int COLOR_HORIZONTAL_BAR = 0xFF999999;
    protected static final int LEFT = 40;
    protected static final int TOP = 15;
    //    protected static final int LEFT = 20;
//    protected static final int TOP = 10;
    public final Minecraft mc = Minecraft.getMinecraft();
    public final FontRenderer textRenderer = this.mc.fontRenderer;
    public final int fontHeight = this.textRenderer.FONT_HEIGHT;
    protected final List<ButtonBase> buttons = new ArrayList<>();
    protected final List<WidgetBase> widgets = new ArrayList<>();
    protected final List<TextFieldWrapper<? extends WidgetTextField>> textFields = new ArrayList<>();
    private final MessageRenderer messageRenderer = new MessageRenderer(0xDD000000, COLOR_HORIZONTAL_BAR);
    //    private long openTime;
    protected WidgetBase hoveredWidget = null;
    protected String title = "";
    protected boolean useTitleHierarchy = true;
    //    private int keyInputCount;
//    private double mouseWheelDeltaSum;
    @Nullable
    private GuiScreen parent;


//    protected GuiBase()
//    {
//        super(ScreenTexts.EMPTY);
//    }

    public GuiBase setParent(@Nullable GuiScreen parent) {
        // Don't allow nesting the GUI with itself...
        if (parent == null || parent.getClass() != this.getClass()) {
            this.parent = parent;
        }

        return this;
    }

    @Nullable
    public GuiScreen getParent() {
        return this.parent;
    }

    public String getTitleString() {
        return (this.useTitleHierarchy && this.parent instanceof GuiBase) ? (((GuiBase) this.parent).getTitleString() + " => " + this.title) : this.title;
    }

    public WidgetBase getHoveredWidget() {
        return this.hoveredWidget;
    }

    //
//    @Override
//    public Text getTitle()
//    {
//        return Text.of(this.getTitleString());
//    }
//
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public void resize(Minecraft mc, int width, int height) {
        if (this.parent != null && this.parent instanceof GuiBase guiBase) {
            guiBase.resize(mc, width, height);
        }

//        super.resize(mc, width, height);
    }

//    @Override
//    public void init() {
//        super.init();
//
//        this.initGui();
//        this.openTime = System.nanoTime();
//    }

    @Override
    public void initGui() {
        super.initGui();
        this.clearElements();
    }


    protected void closeGui(boolean showParent) {
        if (showParent) {
            this.mc.displayGuiScreen(this.parent);
        }
//        else {
//            this.close();
//        }
    }

    @Override
    public final void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.render(new DrawContext(), mouseX, mouseY, partialTicks);
    }

    protected void render(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
        // Use a custom DrawContext that doesn't always disable depth test when drawing...
//        drawContext = new MalilibDrawContext(this.client, drawContext.getVertexConsumers());

        this.drawScreenBackground(mouseX, mouseY);
        this.drawTitle(drawContext, mouseX, mouseY, partialTicks);

        // Draw base widgets
        this.drawWidgets(mouseX, mouseY, drawContext);
        this.drawTextFields(mouseX, mouseY, drawContext);
        this.drawButtons(mouseX, mouseY, partialTicks, drawContext);

        this.drawContents(drawContext, mouseX, mouseY, partialTicks);

        this.drawButtonHoverTexts(mouseX, mouseY, partialTicks, drawContext);
        this.drawHoveredWidget(mouseX, mouseY, drawContext);
        this.drawGuiMessages(drawContext);
    }

    //    @Override
//    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
//        if (this.mouseWheelDeltaSum != 0.0 && Math.signum(amount) != Math.signum(this.mouseWheelDeltaSum)) {
//            this.mouseWheelDeltaSum = 0.0;
//        }
//
//        this.mouseWheelDeltaSum += amount;
//        amount = (int) this.mouseWheelDeltaSum;
//
//        if (amount != 0.0) {
//            this.mouseWheelDeltaSum -= amount;
//
//            if (this.onMouseScrolled((int) mouseX, (int) mouseY, amount)) {
//                return true;
//            }
//        }
//
//        return super.mouseScrolled(mouseX, mouseY, amount);
//    }
//
//    @Override
//    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
//        if (this.onMouseClicked((int) mouseX, (int) mouseY, mouseButton) == false) {
//            return super.mouseClicked(mouseX, mouseY, mouseButton);
//        }
//
//        return false;
//    }
    @Override
    protected final void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.onMouseClicked(mouseX, mouseY, mouseButton);
    }
//
//    @Override
//    public boolean mouseReleased(double mouseX, double mouseY, int mouseButton) {
//        if (this.onMouseReleased((int) mouseX, (int) mouseY, mouseButton) == false) {
//            return super.mouseReleased(mouseX, mouseY, mouseButton);
//        }
//
//        return false;
//    }

    @Override
    protected final void mouseMovedOrUp(int par1, int par2, int par3) {
        this.onMouseReleased(par1, par2, par3);
    }

//    @Override
//    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
//        this.keyInputCount++;
//
//        if (this.onKeyTyped(keyCode, scanCode, modifiers)) {
//            return true;
//        }
//
//        return super.keyPressed(keyCode, scanCode, modifiers);
//    }
//
//    @Override
//    public boolean charTyped(char charIn, int modifiers)
//    {
//        // This is an ugly fix for the issue that the key press from the hotkey that
//        // opens a GUI would then also get into any text fields or search bars, as the
//        // charTyped() event always fires after the keyPressed() event in any case >_>
//        // The 100ms timeout is to not indefinitely block the first character,
//        // as otherwise IME methods wouldn't work at all, as they don't trigger a key press.
//        if (this.keyInputCount <= 0 && System.nanoTime() - this.openTime <= 100000000)
//        {
//            this.keyInputCount++;
//            return true;
//        }
//
//        if (this.onCharTyped(charIn, modifiers))
//        {
//            return true;
//        }
//
//        return super.charTyped(charIn, modifiers);
//    }

    @Override
    protected final void keyTyped(char par1, int par2) {
        this.onCharTyped(par1, par2);
    }

    protected boolean onMouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (ButtonBase button : this.buttons) {
            if (button.onMouseClicked(mouseX, mouseY, mouseButton)) {
                // Don't call super if the button press got handled
                return true;
            }
        }

        boolean handled = false;

        for (TextFieldWrapper<?> entry : this.textFields) {
            if (entry.mouseClicked(mouseX, mouseY, mouseButton)) {
                // Don't call super if the button press got handled
                handled = true;
            }
        }

        if (handled == false) {
            for (WidgetBase widget : this.widgets) {
                if (widget.isMouseOver(mouseX, mouseY) && widget.onMouseClicked(mouseX, mouseY, mouseButton)) {
                    // Don't call super if the button press got handled
                    handled = true;
                    break;
                }
            }
        }

        return handled;
    }


    protected boolean onMouseReleased(int mouseX, int mouseY, int mouseButton) {
        for (WidgetBase widget : this.widgets) {
            widget.onMouseReleased(mouseX, mouseY, mouseButton);
        }
        for (ButtonBase button : this.buttons) {
            button.onMouseReleased(mouseX, mouseY, mouseButton);
        }

        return false;
    }

//    public boolean onMouseScrolled(int mouseX, int mouseY, double mouseWheelDelta) {
//        for (ButtonBase button : this.buttons) {
//            if (button.onMouseScrolled(mouseX, mouseY, mouseWheelDelta)) {
//                // Don't call super if the button press got handled
//                return true;
//            }
//        }
//
//        for (WidgetBase widget : this.widgets) {
//            if (widget.onMouseScrolled(mouseX, mouseY, mouseWheelDelta)) {
//                // Don't call super if the action got handled
//                return true;
//            }
//        }
//
//        return false;
//    }

//    public boolean onKeyTyped(int keyCode, int scanCode, int modifiers) {
//        boolean handled = false;
//        int selected = -1;
//
//        for (int i = 0; i < this.textFields.size(); ++i) {
//            TextFieldWrapper<?> entry = this.textFields.get(i);
//
//            if (entry.isFocused()) {
//                if (keyCode == KeyCodes.KEY_TAB) {
//                    entry.setFocused(false);
//                    selected = i;
//                } else {
//                    entry.onKeyTyped(keyCode, scanCode, modifiers);
//                }
//
//                handled = keyCode != KeyCodes.KEY_ESCAPE;
//                break;
//            }
//        }
//
//        if (handled == false) {
//            for (WidgetBase widget : this.widgets) {
//                if (widget.onKeyTyped(keyCode, scanCode, modifiers)) {
//                    // Don't call super if the button press got handled
//                    handled = true;
//                    break;
//                }
//            }
//        }
//
//        if (handled == false) {
//            if (keyCode == KeyCodes.KEY_ESCAPE) {
//                this.closeGui(isShiftDown() == false);
//
//                return true;
//            }
//        }
//
//        if (selected >= 0) {
//            if (isShiftDown()) {
//                selected = selected > 0 ? selected - 1 : this.textFields.size() - 1;
//            } else {
//                selected = (selected + 1) % this.textFields.size();
//            }
//
//            this.textFields.get(selected).setFocused(true);
//        }
//
//        return handled;
//    }

    protected boolean onCharTyped(char charIn, int modifiers) {
        boolean handled = false;

        for (TextFieldWrapper<?> entry : this.textFields) {
            if (entry.onCharTyped(charIn, modifiers)) {
                handled = true;
                break;
            }
        }

        if (handled == false) {
            for (WidgetBase widget : this.widgets) {
                if (widget.onCharTyped(charIn, modifiers)) {
                    // Don't call super if the button press got handled
                    handled = true;
                    break;
                }
            }
        }

        if (handled == false && modifiers == 1) {
            this.closeGui(true);
            return true;
        }

        return handled;
    }


    @Override
    public void setString(String string) {
        this.messageRenderer.addMessage(3000, string);
    }

    @Override
    public void addMessage(MessageType type, String messageKey, Object... args) {
        this.addGuiMessage(type, 5000, messageKey, args);
    }

    @Override
    public void addMessage(MessageType type, int lifeTime, String messageKey, Object... args) {
        this.addGuiMessage(type, lifeTime, messageKey, args);
    }

    public void addGuiMessage(MessageType type, int displayTimeMs, String messageKey, Object... args) {
        this.messageRenderer.addMessage(type, displayTimeMs, messageKey, args);
    }

    public void setNextMessageType(MessageType type) {
        this.messageRenderer.setNextMessageType(type);
    }

    protected void drawGuiMessages(DrawContext drawContext) {
        this.messageRenderer.drawMessages(this.width / 2, this.height / 2, drawContext);
    }

    public void bindTexture(ResourceLocation texture) {
        RenderUtils.bindTexture(texture);
    }

    public <T extends ButtonBase> void addButton(T button) {
        this.buttons.add(button);
    }
//    public <T extends ButtonBase> T addButton(T button, IButtonActionListener listener) {
//        button.setActionListener(listener);
//        this.buttons.add(button);
//        return button;
//    }

    public <T extends WidgetTextField> TextFieldWrapper<T> addTextField(T textField, @Nullable ITextFieldListener<T> listener) {
        TextFieldWrapper<T> wrapper = new TextFieldWrapper<>(textField, listener);
        this.textFields.add(wrapper);
        return wrapper;
    }

    public <T extends WidgetBase> T addWidget(T widget) {
        this.widgets.add(widget);
        return widget;
    }

    public WidgetLabel addLabel(int x, int y, int width, int height, int textColor, String... lines) {
        return this.addLabel(x, y, width, height, textColor, Arrays.asList(lines));
    }

    public WidgetLabel addLabel(int x, int y, int width, int height, int textColor, List<String> lines) {
        if (lines.size() > 0) {
            if (width == -1) {
                for (String line : lines) {
                    width = Math.max(width, this.getStringWidth(line));
                }
            }
        }

        return this.addWidget(new WidgetLabel(x, y, width, height, textColor, lines));
    }


    protected boolean removeWidget(WidgetBase widget) {
        if (widget != null && this.widgets.contains(widget)) {
            this.widgets.remove(widget);
            return true;
        }

        return false;
    }

    @Override
    public final void updateScreen() {
        this.tickScreen();
    }

    protected void tickScreen() {
        this.widgets.forEach(WidgetBase::tickScreen);
        this.buttons.forEach(WidgetBase::tickScreen);
//        this.textFields.forEach(WidgetBase::update);
    }

    protected void clearElements() {
        this.widgets.clear();
        this.buttons.clear();
        this.textFields.clear();
    }

    protected void drawScreenBackground(int mouseX, int mouseY) {
        // Draw the dark background
//        RenderUtils.drawRect(0, 0, this.width, this.height, TOOLTIP_BACKGROUND);
        this.drawDefaultBackground();
    }

    protected void drawTitle(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
        this.drawString(drawContext, this.getTitleString(), LEFT, TOP, COLOR_WHITE);
    }

    protected void drawContents(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
    }

    protected void drawButtons(int mouseX, int mouseY, float partialTicks, DrawContext drawContext) {
        for (ButtonBase button : this.buttons) {
            button.render(mouseX, mouseY, button.isMouseOver(), drawContext);
        }
    }

    protected void drawTextFields(int mouseX, int mouseY, DrawContext drawContext) {
        for (TextFieldWrapper<?> entry : this.textFields) {
            entry.render(mouseX, mouseY, drawContext);
        }
    }

    protected void drawWidgets(int mouseX, int mouseY, DrawContext drawContext) {
        this.hoveredWidget = null;
        if (this.widgets.isEmpty() == false) {
            for (WidgetBase widget : this.widgets) {
                widget.render(mouseX, mouseY, false, drawContext);
                if (widget.isMouseOver(mouseX, mouseY)) {
                    this.hoveredWidget = widget;
                }
            }
        }
    }

    protected void drawButtonHoverTexts(int mouseX, int mouseY, float partialTicks, DrawContext drawContext) {
        if (this.shouldRenderHoverStuff() == false) {
            return;
        }

        for (ButtonBase button : this.buttons) {
            if (button.hasHoverText() && button.isMouseOver()) {
                RenderUtils.drawHoverText(mouseX, mouseY, button.getHoverStrings(), drawContext);
            }
        }
    }

    protected boolean shouldRenderHoverStuff() {
        return this.mc.currentScreen == this;
    }

    protected void drawHoveredWidget(int mouseX, int mouseY, DrawContext drawContext) {
        if (this.shouldRenderHoverStuff() == false) {
            return;
        }

        if (this.hoveredWidget != null) {
            this.hoveredWidget.postRenderHovered(mouseX, mouseY, false, drawContext);
        }
    }

    public static boolean isMouseOver(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }

    public int getStringWidth(String text) {
        return this.textRenderer.getStringWidth(text);
    }

    public void drawString(DrawContext drawContext, String text, int x, int y, int color) {
        drawContext.drawText(textRenderer, text, x, y, color, false);
    }

    public void drawStringWithShadow(DrawContext drawContext, String text, int x, int y, int color) {
        drawContext.drawTextWithShadow(textRenderer, text, x, y, color);
    }

    public int getMaxPrettyNameLength(List<? extends IConfigBase> configs) {
        int width = 0;

        for (IConfigBase config : configs) {
            width = Math.max(width, this.getStringWidth(config.getConfigGuiDisplayName()));
        }

        return width;
    }

    public static void openGui(GuiScreen gui) {
        Minecraft.getMinecraft().displayGuiScreen(gui);
    }

    public static boolean isShiftDown() {
        return GuiScreen.isShiftKeyDown();
    }

    public static boolean isCtrlDown() {
        return GuiScreen.isCtrlKeyDown();
    }

    public static boolean isAltDown() {
        return Keyboard.isKeyDown(Keyboard.KEY_LMENU) || Keyboard.isKeyDown(Keyboard.KEY_RMENU);
    }

    public static boolean isLeftClicking() {
        return Mouse.isButtonDown(0);
    }

    public static boolean isRightClicking() {
        return Mouse.isButtonDown(1);
    }
}
