package fi.dy.masa.malilib.gui.widgets;

import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.ManyLibIcons;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.gui.wrappers.TextFieldWrapper;
import net.minecraft.GuiScreen;

import java.util.ArrayList;
import java.util.List;

public class WidgetStringEditEntry extends WidgetBase {
    private final List<String> tempList;
    private final Runnable markDirty;
    private final WidgetText markNumber;
    private final TextFieldWrapper<WidgetTextField> textFieldWrapper;
    private final List<ButtonGeneric> buttons;

    public WidgetStringEditEntry(int realIndex, int relativeIndex, String originalString, List<String> tempList, GuiScreen screen, Runnable markDirty) {
        super(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
        this.tempList = tempList;
        this.markDirty = markDirty;
        int yPos = ScreenConstants.getYPos(relativeIndex, screen);
        this.markNumber = WidgetText.of(String.valueOf(realIndex)).position(50, yPos + ScreenConstants.commentedTextShift);
        this.textFieldWrapper = new TextFieldWrapper<>(new WidgetTextField(70, yPos, 200, 18), s -> tempList.set(realIndex, s.getText()));
        this.textFieldWrapper.setText(originalString);
        this.buttons = new ArrayList<>();
        ButtonGeneric.builder(ManyLibIcons.PLUS, button -> insertBelow(realIndex)).dimensions(290, yPos, 20, 20).addToList(this.buttons);
        ButtonGeneric.builder(ManyLibIcons.MINUS, button -> delete(realIndex)).dimensions(320, yPos, 20, 20).addToList(this.buttons);
        ButtonGeneric.builder(ManyLibIcons.ARROW_UP, button -> moveUp(realIndex)).dimensions(350, yPos, 20, 20).addToList(this.buttons);
        ButtonGeneric.builder(ManyLibIcons.ARROW_DOWN, button -> moveDown(realIndex)).dimensions(380, yPos, 20, 20).addToList(this.buttons);
    }

    private void markDirty() {
        this.markDirty.run();
    }

    private void delete(int operant) {
        this.tempList.remove(operant);
        this.markDirty();
    }

    private void insertBelow(int operant) {
        this.tempList.add(operant + 1, "");
        this.markDirty();
    }

    private void moveUp(int operant) {
        if (operant == 0) return;
        String string = this.tempList.get(operant);
        this.tempList.set(operant, this.tempList.get(operant - 1));
        this.tempList.set(operant - 1, string);
        this.markDirty();
    }

    private void moveDown(int operant) {
        if (operant == this.tempList.size() - 1) return;
        String string = this.tempList.get(operant);
        this.tempList.set(operant, this.tempList.get(operant + 1));
        this.tempList.set(operant + 1, string);
        this.markDirty();
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.render(mouseX, mouseY, selected, drawContext);
        this.markNumber.render(mouseX, mouseY, selected, drawContext);
        this.textFieldWrapper.render(mouseX, mouseY, drawContext);
        this.buttons.forEach(x -> x.render(mouseX, mouseY, selected, drawContext));
    }

    @Override
    public void tickScreen() {
        super.tickScreen();
        this.textFieldWrapper.tickScreen();
    }

    @Override
    protected boolean onCharTypedImpl(char charIn, int modifiers) {
        return this.textFieldWrapper.onCharTyped(charIn, modifiers);
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
        if (super.onMouseClickedImpl(mouseX, mouseY, mouseButton)) return true;
        if (mouseButton == 0 && this.buttons.stream().anyMatch(button -> button.onMouseClicked(mouseX, mouseY, mouseButton)))
            return true;
        return this.textFieldWrapper.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void onMouseReleasedImpl(int mouseX, int mouseY, int mouseButton) {
        super.onMouseReleasedImpl(mouseX, mouseY, mouseButton);
        this.textFieldWrapper.onMouseReleased(mouseX, mouseY, mouseButton);
    }
}
