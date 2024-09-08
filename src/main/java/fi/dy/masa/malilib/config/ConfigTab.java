package fi.dy.masa.malilib.config;

import fi.dy.masa.malilib.ManyLib;
import fi.dy.masa.malilib.compat.PinyinHandler;
import fi.dy.masa.malilib.config.interfaces.ConfigType;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.gui.screen.util.SortCategory;
import fi.dy.masa.malilib.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ConfigTab {
    final static Set<ConfigType> supportedConfigTypes = Set.of(ConfigType.DOUBLE, ConfigType.BOOLEAN, ConfigType.INTEGER, ConfigType.STRING, ConfigType.ENUM, ConfigType.COLOR, ConfigType.HOTKEY, ConfigType.TOGGLE);
    String unlocalizedName;
    final List<ConfigBase<?>> allConfigs;
    List<ConfigBase<?>> searchableConfigs;
    String searchText;

    public ConfigTab(String unlocalizedName, List<?> allConfigs) {
        this.unlocalizedName = unlocalizedName;
        this.allConfigs = new ArrayList<>();
        for (Object allConfig : allConfigs) {
            ConfigBase<?> config = (ConfigBase<?>) allConfig;
            if (supportedConfigTypes.contains(config.getType())) {
                this.allConfigs.add(config);
            }
        }
        this.searchableConfigs = new ArrayList<>(this.allConfigs);
    }

    public String getGuiDisplayName() {
        return StringUtils.getTranslatedOrFallback("config.tab." + this.unlocalizedName, this.unlocalizedName);
    }

    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }

    public String getTooltip() {
        return StringUtils.getTranslatedOrFallback("config.tab." + this.unlocalizedName + ".comment", null);
    }

    public List<ConfigBase<?>> getAllConfigs() {
        return this.allConfigs;
    }

    public void sort(SortCategory sortCategory) {
        if (sortCategory == SortCategory.Default) {
            if (this.searchText == null || this.searchText.isEmpty()) {
                this.searchableConfigs = new ArrayList<>(this.allConfigs);
            } else {
                this.updateSearchableConfigs(this.searchText);
            }
        } else {
            this.searchableConfigs.sort(sortCategory.category);
        }
    }

    public void updateSearchableConfigs(String input) {
        this.searchText = input;
        this.searchableConfigs = this.allConfigs.stream()
                .filter(configBase -> this.stringMatchesInput(configBase.getConfigGuiDisplayName(), input))
                .collect(Collectors.toList());
    }

    private boolean stringMatchesInput(String string, String input) {
        if (string.toLowerCase().contains(input.toLowerCase())) {
            return true;
        }
        PinyinHandler instance = PinyinHandler.getInstance();
        if (instance.isValid()) {
            try {
                if (instance.contains(string, input)) {
                    return true;
                }
            } catch (Exception e) {
                ManyLib.logger.warn("PinyinHandler: failed to match input");
            }
        }
        return false;
    }

    public void resetSearchableConfigs() {
        this.searchableConfigs = new ArrayList<>(this.allConfigs);
    }

    public int getSearchableConfigSize() {
        return this.searchableConfigs.size();
    }

    public ConfigBase<?> getSearchableConfig(int index) {
        return this.searchableConfigs.get(index);
    }

    public int getMaxStatusForScreen(int pageCapacity) {
        if (this.getSearchableConfigSize() > pageCapacity) {
            return this.getSearchableConfigSize() - pageCapacity;
        } else {
            return 0;
        }
    }
}
