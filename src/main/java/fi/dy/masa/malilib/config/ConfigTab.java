package fi.dy.masa.malilib.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.feat.SortCategory;
import fi.dy.masa.malilib.gui.screen.util.ConfigItem;
import fi.dy.masa.malilib.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigTab {
    String unlocalizedName;
    final ImmutableList<ConfigBase<?>> allConfigs;
    List<ConfigBase<?>> searchableConfigs;
    String searchText;

    public ConfigTab(String unlocalizedName, List<?> allConfigs) {
        this.unlocalizedName = unlocalizedName;
        ImmutableList.Builder<ConfigBase<?>> builder = ImmutableList.builder();
        for (Object allConfig : allConfigs) {
            ConfigBase<?> config = (ConfigBase<?>) allConfig;
            if (ConfigItem.supported(config)) builder.add(config);
        }
        this.allConfigs = builder.build();
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
            if (this.searchText == null || this.searchText.isEmpty()) {// not searching: default order
                this.searchableConfigs = new ArrayList<>(this.allConfigs);
            } else {//
                this.updateSearchableConfigs(this.searchText);// searching: do one search
            }
        } else {// with category
            this.searchableConfigs.sort(sortCategory.category);
        }
    }

    public void updateSearchableConfigs(String input) {
        this.searchText = input;
        if (input.isEmpty()) {
            this.resetSearchableConfigs();
            return;
        }
        this.searchableConfigs = this.allConfigs.stream()
                .filter(configBase -> StringUtils.stringMatchesInput(configBase.getConfigGuiDisplayName(), input))
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
}
