package fi.dy.masa.malilib.client.gui.interfaces;


import fi.dy.masa.malilib.client.gui.widgets.text.TextField;

public interface ITextFieldListener<T extends TextField> {
    void onTextChange(T textField);

    default void onFinish(T textField) {
    }
}
