package fi.dy.masa.malilib.config.interfaces;

import fi.dy.masa.malilib.core.Color4f;

public interface IConfigColor {
    /**
     * a+r+g+b
     */
    int getColorInteger();

    Color4f getColor();

    String getColorString();

    void setColor(Color4f color);

    Color4f getDefaultColor();
}
