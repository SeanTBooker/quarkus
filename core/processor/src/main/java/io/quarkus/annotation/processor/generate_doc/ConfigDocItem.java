package io.quarkus.annotation.processor.generate_doc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A config doc item is either a config section {@link ConfigDocSection} or a config key {@link ConfigDocKey}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
final public class ConfigDocItem implements ConfigDocElement, Comparable<ConfigDocItem> {
    private ConfigDocKey configDocKey;
    private ConfigDocSection configDocSection;

    public ConfigDocItem() {
    }

    public ConfigDocItem(ConfigDocSection configDocSection, ConfigDocKey configDocKey) {
        this.configDocSection = configDocSection;
        this.configDocKey = configDocKey;
    }

    public ConfigDocKey getConfigDocKey() {
        return configDocKey;
    }

    public void setConfigDocKey(ConfigDocKey configDocKey) {
        this.configDocKey = configDocKey;
    }

    public ConfigDocSection getConfigDocSection() {
        return configDocSection;
    }

    public void setConfigDocSection(ConfigDocSection configDocSection) {
        this.configDocSection = configDocSection;
    }

    @JsonIgnore
    boolean isConfigSection() {
        return configDocSection != null;
    }

    @JsonIgnore
    boolean isConfigKey() {
        return configDocKey != null;
    }

    @Override
    public String toString() {
        return "ConfigDocItem{" +
                "configDocSection=" + configDocSection +
                ", configDocKey=" + configDocKey +
                '}';
    }

    @Override
    public String accept(DocFormatter docFormatter) {
        if (isConfigSection()) {
            return configDocSection.accept(docFormatter);
        } else if (isConfigKey()) {
            return configDocKey.accept(docFormatter);
        }

        return "";
    }

    @JsonIgnore
    @Override
    public ConfigPhase getConfigPhase() {
        if (isConfigSection()) {
            return configDocSection.getConfigPhase();
        } else if (isConfigKey()) {
            return configDocKey.getConfigPhase();
        }

        return null;
    }

    @JsonIgnore
    @Override
    public boolean isWithinAMap() {
        if (isConfigSection()) {
            return configDocSection.isWithinAMap();
        } else if (isConfigKey()) {
            return configDocKey.isWithinAMap();
        }

        return false;
    }

    /**
     * TODO determine section ordering
     *
     * @param item
     * @return
     */
    @Override
    public int compareTo(ConfigDocItem item) {
        if (isConfigSection() && item.isConfigKey()) {
            return 1; // push sections to the end of the list
        } else if (isConfigKey() && item.isConfigSection()) {
            return -1; // push section to the end of the list
        }

        return compare(item);
    }
}
