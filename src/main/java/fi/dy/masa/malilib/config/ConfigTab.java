package fi.dy.masa.malilib.config;

import fi.dy.masa.malilib.config.interfaces.ConfigType;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.gui.screen.util.SortCategory;
import fi.dy.masa.malilib.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ConfigTab {
    final static Set<ConfigType> supportedConfigTypes = Set.of(ConfigType.DOUBLE, ConfigType.BOOLEAN, ConfigType.INTEGER, ConfigType.STRING, ConfigType.ENUM, ConfigType.COLOR);
    String unlocalizedName;
    final List<ConfigBase<?>> allConfigs;
    List<ConfigBase<?>> searchableConfigs;
    String searchText;

    public ConfigTab(String unlocalizedName, List<ConfigBase<?>> allConfigs) {
        this.unlocalizedName = unlocalizedName;
        this.allConfigs = allConfigs.stream()
                .filter(configBase -> supportedConfigTypes.contains(configBase.getType()))
                .toList();
        this.searchableConfigs = new ArrayList<>(this.allConfigs);
    }

    public String getGuiDisplayName() {
        return StringUtils.getTranslatedOrFallback("config.tab." + this.unlocalizedName, this.unlocalizedName);
    }

    public String getUnlocalizedName() {
        return this.unlocalizedName;
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

    public void updateSearchableConfigs(String string) {
        this.searchText = string;
        this.searchableConfigs = this.allConfigs.stream()
                .filter(configBase -> configBase.getConfigGuiDisplayName().toLowerCase().contains(string.toLowerCase()))
                .collect(Collectors.toList());
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
