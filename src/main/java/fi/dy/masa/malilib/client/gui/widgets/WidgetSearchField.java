package fi.dy.masa.malilib.client.gui.widgets;

import fi.dy.masa.malilib.client.gui.DrawContext;
import fi.dy.masa.malilib.client.gui.ManyLibIcons;
import fi.dy.masa.malilib.client.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.client.gui.button.interfaces.IToggleableElement;
import fi.dy.masa.malilib.client.gui.screen.interfaces.Searchable;
import fi.dy.masa.malilib.client.gui.wrappers.TextFieldWrapper;
import fi.dy.masa.malilib.client.util.SystemUtils;
import fi.dy.masa.malilib.localization.ScreenText;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class WidgetSearchField extends ButtonGeneric implements IToggleableElement {
    private final TextFieldWrapper<WidgetTextField> textFieldWrapper;

    private boolean searchEnabled;

    private final Searchable searchable;

    public WidgetSearchField(int x, int y, int boxWidth, int boxHeight, Searchable searchable) {
        super(x, y, 12, 12, "", button -> ((WidgetSearchField) button).toggle());
        this.setHoverStrings(ScreenText.SEARCH_BUTTON.translate());
        this.searchable = searchable;
        this.textFieldWrapper = new TextFieldWrapper<>(new WidgetTextField(x + 16, y - 1, boxWidth, boxHeight), textField -> searchable.updateSearchResult(textField.getText()));
        this.setVisible(false);
        this.icon = ManyLibIcons.SEARCH;
        this.setRenderDefaultBackground(false);
    }

    @Override
    protected boolean onCharTypedImpl(char charIn, int modifiers) {
        return this.textFieldWrapper.onCharTyped(charIn, modifiers);
    }

    public void initialSearch() {
        String content = "";
        try {
            content = SystemUtils.getClipboardContent();
        } catch (IOException | UnsupportedFlavorException ignored) {
        }
        this.initialSearch(content);
    }

    public void initialSearch(String content) {
        this.toggle();// make visible
        this.textFieldWrapper.getTextField().setText(content);
        this.selectAll();
        this.searchable.updateSearchResult(content);
    }

    public void setFocused(boolean isFocused) {
        this.textFieldWrapper.setFocused(isFocused);
    }

    public String getText() {
        return this.textFieldWrapper.getText();
    }

    private void selectAll() {
        this.textFieldWrapper.getTextField().charTyped('\u0001', 0);
    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return super.isMouseOver(mouseX, mouseY) || this.textFieldWrapper.getTextField().isMouseOver(mouseX, mouseY);
    }

    @Override
    public void tick() {
        super.tick();
        this.textFieldWrapper.tick();
    }

    @Override
    public boolean onMouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (super.isMouseOver(mouseX, mouseY) && super.onMouseClickedImpl(mouseX, mouseY, mouseButton)) {
            return true;
        }
        return this.textFieldWrapper.mouseClicked(mouseX, mouseY, mouseButton);
    }

//    @Override
//    protected void onMouseReleasedImpl(int mouseX, int mouseY, int mouseButton) {
//        super.onMouseReleasedImpl(mouseX, mouseY, mouseButton);
//        if (this.textFieldWrapper.isFocused() && !this.isMouseOver(mouseX, mouseY)) {
//            this.textFieldWrapper.setFocused(false);
//        }
//    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.render(mouseX, mouseY, selected, drawContext);
        if (this.visible && this.searchEnabled) {
            this.textFieldWrapper.render(mouseX, mouseY, drawContext);
        }
    }

    @Override
    public void toggle() {
        this.setVisible(!this.searchEnabled);
        if (this.searchEnabled) {
            this.textFieldWrapper.getTextField().setText("");
            this.searchable.updateSearchResult("");
        } else {
            this.textFieldWrapper.setFocused(true);
        }
        this.searchEnabled = !this.searchEnabled;
    }

    @Override
    public void setVisible(boolean visible) {
        this.textFieldWrapper.getTextField().setVisible(visible);
        this.textFieldWrapper.getTextField().setEnabled(visible);
    }
}
