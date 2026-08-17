package fi.dy.masa.malilib.client.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.client.config.interfaces.IClientConfigHandler;
import fi.dy.masa.malilib.client.config.options.ConfigHotkey;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.SimpleConfigs;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.util.JsonUtils;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.util.List;

public class ClientSimpleConfigs extends SimpleConfigs implements IClientConfigHandler {
    protected final List<ConfigHotkey> hotkeys;

    public ClientSimpleConfigs(String modId, List<ConfigBase<?>> values, List<ConfigHotkey> hotkeys) {
        super(modId, values);
        this.hotkeys = hotkeys;
    }

    @Override
    public void save() {
        JsonObject configRoot = new JsonObject();
        if (!this.hotkeys.isEmpty()) {
            ConfigUtils.writeConfigBase(configRoot, "HotKeys", this.hotkeys);
        }
        if (!this.values.isEmpty()) {
            ConfigUtils.writeConfigBase(configRoot, "Values", this.values);
        }
        JsonUtils.writeJsonToFile(configRoot, this.path.toFile());
    }

    /*
     * Save after load because some loading might fail; though this may lose performance
     * */
    @Override
    public void load() {
        if (!Files.exists(this.path)) {
            save();
            return;
        }
        JsonElement jsonElement = JsonUtils.parseJsonFile(this.path.toFile());
        if (jsonElement != null && jsonElement.isJsonObject()) {
            JsonObject obj = jsonElement.getAsJsonObject();
            ConfigUtils.readConfigBase(obj, "HotKeys", this.hotkeys);
            ConfigUtils.readConfigBase(obj, "Values", this.values);
            this.save();
        }
    }

    @NotNull
    @Override
    public List<ConfigHotkey> getHotkeys() {
        return this.hotkeys;
    }
}
