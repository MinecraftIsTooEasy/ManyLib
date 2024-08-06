package fi.dy.masa.malilib.compat;

import fi.dy.masa.malilib.ManyLib;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.impl.util.version.SemanticVersionImpl;
import net.xiaoyu233.fml.FishModLoader;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PinyinHandler {
    private static final PinyinHandler Instance = new PinyinHandler();
    private boolean isValid;
    private Object pinInContext;
    private Method containsMethod;
    private Method getCharMethod;
    private Method pinyinsMethod;

    public PinyinHandler() {
        if (this.hasLib()) {
            this.tryInit();
        }
    }

    private boolean hasLib() {
        if (FishModLoader.hasMod("craftguide")) {
            Version version = FishModLoader.getModsMap().get("craftguide")
                    .getMetadata()
                    .getVersion();
            int compare = 1;
            try {
                Version version103 = new SemanticVersionImpl("1.0.3", false);
                compare = version.compareTo(version103);
            } catch (VersionParsingException e) {
                throw new RuntimeException(e);
            }
            return compare >= 0;
        }
        return false;
    }

    private void tryInit() {
        try {
            Class<?> clazz = Class.forName("craftguide.api.PinyinMatch");
            Field context = clazz.getDeclaredField("context");
            context.setAccessible(true);
            this.pinInContext = context.get(null);
            this.containsMethod = this.pinInContext.getClass().getMethod("contains", String.class, String.class);
            this.containsMethod.setAccessible(true);
            this.getCharMethod = this.pinInContext.getClass().getMethod("getChar", char.class);
            this.getCharMethod.setAccessible(true);
            this.pinyinsMethod = this.getCharMethod.getReturnType().getMethod("pinyins");
            this.pinyinsMethod.setAccessible(true);
            this.isValid = true;
        } catch (NoSuchFieldException | ClassNotFoundException | IllegalAccessException |
                 NoSuchMethodException e) {
            ManyLib.logger.warn("PinyinHandler: found craftguide mod, but failed to init");
            e.printStackTrace();
        }
    }

    public static PinyinHandler getInstance() {
        return Instance;
    }

    public boolean isValid() {
        return this.isValid;
    }

    public int compareTheInit(String input1, String input2) throws InvocationTargetException, IllegalAccessException {
        return this.compareChars(input1.charAt(0), input2.charAt(0));
    }

    public int compareChars(char input1, char input2) throws InvocationTargetException, IllegalAccessException {
        return this.convertToLetters(input1).compareToIgnoreCase(this.convertToLetters(input2));
    }

    public boolean contains(String provider, String input) throws InvocationTargetException, IllegalAccessException {
        return (boolean) this.containsMethod.invoke(this.pinInContext, provider, input);
    }

    private String convertToLetters(char c) throws InvocationTargetException, IllegalAccessException {
        Object charObject = this.getCharMethod.invoke(this.pinInContext, c);
        Object[] pinYins = (Object[]) this.pinyinsMethod.invoke(charObject);
        if (pinYins.length == 0) {
            return String.valueOf(c);
        } else {
            return pinYins[0].toString();
        }
    }
}
