package fi.dy.masa.malilib.config.interfaces;

import com.google.gson.JsonElement;
import fi.dy.masa.malilib.config.ConfigType;

import javax.annotation.Nullable;

public interface IConfigBase {
    ConfigType getType();

    String getName();

    /**
     * Returns the comment displayed when hovering over the config name in the config GUI.
     * Newlines can be added with "\n". Can be null if there is no comment for this config.
     *
     * @return the comment, or null if no comment has been set
     */
    @Nullable
    String getComment();

    /**
     * Set the value of this config option from a JSON element (is possible)
     *
     * @param element
     */
    void setValueFromJsonElement(JsonElement element);

    /**
     * Return the value of this config option as a JSON element, for saving into a config file.
     *
     * @return
     */
    JsonElement getAsJsonElement();
}
