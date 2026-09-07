package fi.dy.masa.malilib.config;

import com.google.gson.JsonObject;
import fi.dy.masa.malilib.client.config.interfaces.IClientConfigHandler;
import fi.dy.masa.malilib.config.interfaces.IConfigBase;
import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.util.JsonUtils;

import java.util.List;
import java.util.stream.Stream;

public class ConfigUtils {
    public static void readConfigBase(JsonObject root, String category, List<? extends IConfigBase> options) {
        JsonObject obj = JsonUtils.getNestedObject(root, category, false);
        if (obj != null && options != null) {
            for (IConfigBase option : options) {
                String name = option.getName();
                if (obj.has(name)) {
                    option.setValueFromJsonElement(obj.get(name));
                }
            }
        }
    }

    public static void writeConfigBase(JsonObject root, String category, List<? extends IConfigBase> options) {
        JsonObject obj = JsonUtils.getNestedObject(root, category, true);
        if (obj != null && options != null) {
            for (IConfigBase option : options) {
                obj.add(option.getName(), option.getAsJsonElement());
            }
        }
    }

    public static Stream<? extends IConfigBase> streamAllOptions(IConfigHandler iConfigHandler) {
        if (iConfigHandler instanceof IClientConfigHandler clientConfigHandler) {
            return Stream.concat(
                    clientConfigHandler.getValues().stream(),
                    clientConfigHandler.getHotkeys().stream()
            );
        } else {
            return iConfigHandler.getValues().stream();
        }
    }
}
