package fi.dy.masa.malilib.gui.button;

import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.ManyLibIcons;
import fi.dy.masa.malilib.gui.button.interfaces.IToggleableElement;
import fi.dy.masa.malilib.gui.screen.interfaces.Searchable;
import fi.dy.masa.malilib.gui.widgets.WidgetTextField;
import fi.dy.masa.malilib.util.StringUtils;

public class SearchField extends ButtonGeneric implements IToggleableElement {
    private final WidgetTextField textField;

    private boolean searchEnabled;

    private final Searchable searchable;

    public SearchField(int x, int y, int boxWidth, int boxHeight, Searchable searchable) {
        super(x, y, 12, 12, "", button -> ((SearchField) button).toggle());
        this.tooltip(StringUtils.translate("manyLib.gui.button.search"));
        this.textField = new WidgetTextField(x + 16, y - 1, boxWidth, boxHeight);
        this.searchable = searchable;
        this.setVisible(false);
        this.icon = ManyLibIcons.SEARCH;
        this.setRenderDefaultBackground(false);
    }

    @Override
    protected boolean onCharTypedImpl(char charIn, int modifiers) {
        if (this.textField.isFocused()) {
            String temp = this.textField.getText();
            this.textField.textboxKeyTyped(charIn, modifiers);
            String after = this.textField.getText();
            if (!after.equals(temp)) {
                this.searchable.updateSearchResult(after);
            }
            if (modifiers == 1 || modifiers == 28 || modifiers == 156) {
                this.textField.setFocused(false);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return super.isMouseOver(mouseX, mouseY) || this.textField.isMouseOver(mouseX, mouseY);
    }

    @Override
    public void update() {
        super.update();
        this.textField.updateCursorCounter();
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
        if (super.isMouseOver(mouseX, mouseY) && super.onMouseClickedImpl(mouseX, mouseY, mouseButton)) {
            return true;
        }
        if (this.textField.isMouseOver(mouseX, mouseY)) {
            return this.textField.onMouseClicked(mouseX, mouseY, mouseButton);
        }
        return false;
    }

    @Override
    protected void onMouseReleasedImpl(int mouseX, int mouseY, int mouseButton) {
        super.onMouseReleasedImpl(mouseX, mouseY, mouseButton);
        if (this.textField.isFocused() && !this.isMouseOver(mouseX, mouseY)) this.textField.setFocused(false);
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.render(mouseX, mouseY, selected, drawContext);
        if (this.visible && this.searchEnabled) {
            this.textField.render(mouseX, mouseY, selected, drawContext);
        }
    }

    @Override
    public void toggle() {
        this.setVisible(!this.searchEnabled);
        if (this.searchEnabled) {
            this.searchable.resetSearchResult();
            this.textField.setText("");
        } else {
            this.textField.setFocused(true);
        }
        this.searchEnabled = !this.searchEnabled;
    }

    @Override
    public void setVisible(boolean visible) {
        this.textField.setVisible(visible);
        this.textField.setEnabled(visible);
    }
}
