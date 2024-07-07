package fi.dy.masa.malilib.gui.button.interfaces;

public interface ISliderButton extends IButtonStringUpdatable {
    void updateSliderRatioByConfig();

    float getRatioFromSlider(int mouseX);
}
