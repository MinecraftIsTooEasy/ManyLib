package fi.dy.masa.malilib.config.interfaces;

public interface IConfigSlideable {
    double getRatio();

    void setValueByRatio(double ratio);

    default boolean shouldUseSlider() {
        return true;
    }

    void toggleUseSlider();
}
