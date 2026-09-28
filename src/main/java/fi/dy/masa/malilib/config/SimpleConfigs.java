package fi.dy.masa.malilib.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.interfaces.IConfigBase;
import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.util.JsonUtils;
import fi.dy.masa.malilib.util.Platform;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class SimpleConfigs implements IConfigHandler {
    private final String id;
    protected Path path;
    protected final List<? extends IConfigBase> values;

    public SimpleConfigs(String id, List<? extends IConfigBase> values) {
        this.id = id;
        this.path = Platform.getConfigPath().resolve(this.id + "_" + this.getSide().toString() + ".json");
        this.values = values;
    }

    @Override
    public void save() {
        JsonObject configRoot = new JsonObject();
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
            ConfigUtils.readConfigBase(obj, "Values", this.values);
            this.save();
        }
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    @NotNull
    public List<? extends IConfigBase> getValues() {
        return this.values;
    }
}
