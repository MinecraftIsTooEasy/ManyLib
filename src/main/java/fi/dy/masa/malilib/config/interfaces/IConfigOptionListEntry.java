package fi.dy.masa.malilib.config.interfaces;

public interface IConfigOptionListEntry {
    String getStringValue();

    String getDisplayName();

    IConfigOptionListEntry cycle(boolean forward);

    IConfigOptionListEntry fromString(String value);
}
