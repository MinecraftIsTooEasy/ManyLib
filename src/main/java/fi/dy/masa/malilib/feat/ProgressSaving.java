package fi.dy.masa.malilib.feat;

import java.util.HashMap;
import java.util.Map;

public class ProgressSaving {

    private static final Map<String, Progress> progressMap = new HashMap<>();

    public static void saveProgress(String name, int page, int status) {
        progressMap.put(name, new Progress(page, status));
    }

    public static int getPage(String name) {
        if (progressMap.containsKey(name)) {
            return progressMap.get(name).page;
        }
        return 0;
    }

    public static int getStatus(String name) {
        if (progressMap.containsKey(name)) {
            return progressMap.get(name).status;
        }
        return 0;
    }

    private record Progress(int page, int status) {
    }
}
