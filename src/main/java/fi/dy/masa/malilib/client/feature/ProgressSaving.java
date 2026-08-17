package fi.dy.masa.malilib.client.feature;

import fi.dy.masa.malilib.config.interfaces.IConfigHandler;

import java.util.HashMap;
import java.util.Map;

public class ProgressSaving {

    private static final Map<IConfigHandler, Progress> progressMap = new HashMap<>();

    public static void saveProgress(IConfigHandler configHandler, int page, int status) {
        progressMap.put(configHandler, new Progress(page, status));
    }

    public static int getPage(IConfigHandler configHandler) {
        if (progressMap.containsKey(configHandler)) {
            return progressMap.get(configHandler).page;
        }
        return 0;
    }

    public static int getStatus(IConfigHandler configHandler) {
        if (progressMap.containsKey(configHandler)) {
            return progressMap.get(configHandler).status;
        }
        return 0;
    }

    private record Progress(int page, int status) {
    }
}
