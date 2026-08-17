package fi.dy.masa.malilib.client.gui.button.interfaces;

public interface ISliderButton extends IButtonStringUpdatable {
    void updateSliderRatioByConfig();

    float getRatioFromSlider(int mouseX);
}
