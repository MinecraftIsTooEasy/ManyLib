package fi.dy.masa.malilib.config.interfaces;

public interface IStringRepresentable extends IStringValue {
    String getDefaultStringValue();

    void setValueFromString(String value);

    /**
     * Checks whether or not the given value would be modified from the default value.
     *
     * @param newValue
     * @return
     */
    boolean isModified(String newValue);
}
