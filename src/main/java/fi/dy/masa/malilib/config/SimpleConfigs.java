package fi.dy.masa.malilib.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.gui.screen.HotKeyScreen;
import fi.dy.masa.malilib.gui.screen.ValueScreen;
import fi.dy.masa.malilib.util.JsonUtils;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.GuiScreen;
import net.minecraft.KeyBinding;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class SimpleConfigs implements IConfigHandler {
    protected String name;
    protected File optionsFile;
    protected final List<ConfigHotkey> hotkeys;
    protected final List<ConfigBase<?>> values;
    protected String valuesComment;
    protected String hotKeysComment;

    public SimpleConfigs(String name, List<ConfigHotkey> hotkeys, List<?> values) {
        this(name, hotkeys, values, null, null);
    }

    public SimpleConfigs(String name, List<ConfigHotkey> hotkeys, List<?> values, String valuesComment, String hotKeysComment) {
        this.name = name;
        this.optionsFile = new File("configs" + File.separator + name + ".json");
        this.hotkeys = hotkeys;
        this.values = cast(values);
        this.valuesComment = valuesComment;
        this.hotKeysComment = hotKeysComment;
    }

    private static List<ConfigBase<?>> cast(List<?> values) {
        ArrayList<ConfigBase<?>> objects = new ArrayList<>();
        for (Object value : values) {
            objects.add((ConfigBase<?>) value);
        }
        return objects;
    }

    @Override
    public GuiScreen getValueScreen(GuiScreen parentScreen) {
        return new ValueScreen(parentScreen, this.getName() + " Configs", this);
//        return new ValueScreen(parentScreen, this.getName(), this, this.values);
//        return new LegacyValueScreen(parentScreen, this.getName(), this, this.values);
    }

    @Override
    public GuiScreen getHotKeyScreen(GuiScreen parentScreen) {
        return new HotKeyScreen(parentScreen, this.getName(), this, this.hotkeys);
    }

    @Override
    public void save() {
        JsonObject configRoot = new JsonObject();
        if (this.hotkeys != null && !this.hotkeys.isEmpty()) {
            ConfigUtils.writeConfigBase(configRoot, "HotKeys", this.hotkeys);
        }
        if (this.values != null && !this.values.isEmpty()) {
            ConfigUtils.writeConfigBase(configRoot, "Values", this.values);
        }
        JsonUtils.writeJsonToFile(configRoot, this.optionsFile);
    }

    /*
     * Save after load because some loading might fail; though this may lose performance
     * */
    @Override
    public void load() {
        if (!this.optionsFile.exists()) {
            save();
            return;
        }
        JsonElement jsonElement = JsonUtils.parseJsonFile(this.optionsFile);
        if (jsonElement != null && jsonElement.isJsonObject()) {
            JsonObject obj = jsonElement.getAsJsonObject();
            ConfigUtils.readConfigBase(obj, "HotKeys", this.hotkeys);
            ConfigUtils.readConfigBase(obj, "Values", this.values);
            this.save();
            KeyBinding.resetKeyBindingArrayAndHash();
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    /**
     * @deprecated this field is now protected
     */
    @Deprecated(since = "1.1.2")
    public File getOptionsFile() {
        return this.optionsFile;
    }

    @Override
    @Nullable
    public List<ConfigHotkey> getHotkeys() {
        return this.hotkeys;
    }

    @Override
    public List<ConfigTab> getConfigTabs() {
        return List.of(new ConfigTab("generic", this.values));
    }

    @Override
    @Nullable
    public List<ConfigBase<?>> getValues() {
        return this.values;
    }

    @Override
    public String getValuesComment() {
        return StringUtils.getTranslatedOrFallback("config.value.comment." + this.name, this.valuesComment);
    }

    @Override
    public String getHotKeysComment() {
        return StringUtils.getTranslatedOrFallback("config.hotkey.comment." + this.name, this.hotKeysComment);
    }
}
