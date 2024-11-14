package fi.dy.masa.malilib.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.gui.screen.DefaultConfigScreen;
import fi.dy.masa.malilib.util.JsonUtils;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.GuiScreen;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class SimpleConfigs implements IConfigHandler {
    protected String name;
    protected File optionsFile;
    protected final List<ConfigHotkey> hotkeys;
    protected final List<ConfigBase<?>> values;
    protected String menuComment;

    public SimpleConfigs(String name, List<ConfigHotkey> hotkeys, List<?> values) {
        this(name, hotkeys, values, null);
    }

    public SimpleConfigs(String name, List<ConfigHotkey> hotkeys, List<?> values, String menuComment) {
        this.name = name;
        this.optionsFile = new File("config" + File.separator + name + ".json");
        if (values == null || values.isEmpty()) {
            this.values = List.of();
        } else {
            this.values = new ArrayList<>();
            castFill(this.values, values);
        }
        if (hotkeys == null || hotkeys.isEmpty()) {
            this.hotkeys = List.of();
        } else {
            this.hotkeys = hotkeys;
        }
        this.menuComment = menuComment;
    }

    @Deprecated(since = "2.1.0", forRemoval = true)
    public SimpleConfigs(String name, List<ConfigHotkey> hotkeys, List<?> values, String valueComment, String hotKeysComment) {
        this(name, hotkeys, values, valueComment);
    }

    @SuppressWarnings("unchecked")
    private static <T> void castFill(List<T> to, List<?> from) {
        for (Object o : from) {
            to.add((T) o);
        }
    }

    @Override
    public GuiScreen getConfigScreen(GuiScreen parentScreen) {
//        return new DefaultConfigScreen(parentScreen, this);
        return new DefaultConfigScreen(parentScreen, this);
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
        List<ConfigTab> configTabs = new ArrayList<>();
        if (!this.values.isEmpty()) {
            configTabs.add(new ConfigTab("generic", this.values));
        }
        if (!this.hotkeys.isEmpty()) {
            List<ConfigBase<?>> temp = new ArrayList<>();
            castFill(temp, this.hotkeys);
            configTabs.add(new ConfigTab("hotkey", temp));
        }
        return configTabs;
    }

    @Override
    @Nullable
    public List<ConfigBase<?>> getValues() {
        return this.values;
    }

    @Override
    public String getMenuComment() {
        return StringUtils.getTranslatedOrFallback("config.menu.comment." + this.name, this.menuComment);
    }
}
