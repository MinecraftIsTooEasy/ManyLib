package fi.dy.masa.malilib.client.gui.interfaces;


import fi.dy.masa.malilib.client.gui.widgets.WidgetTextField;

public interface ITextFieldListener<T extends WidgetTextField> {
    void onTextChange(T textField);

    default void onFinish(T textField) {
    }
}
