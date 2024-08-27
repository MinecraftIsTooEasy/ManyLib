package fi.dy.masa.malilib.compat;

import fi.dy.masa.malilib.ManyLib;
import net.xiaoyu233.fml.FishModLoader;

import java.lang.reflect.InvocationTargetException;

public class PinyinHandler {
    private static final PinyinHandler Instance = new PinyinHandler();
    private boolean isValid;
    private PinInImpl pinInImpl = null;

    public PinyinHandler() {
        if (this.hasLib()) {
            this.tryInit();
        }
    }

    private boolean hasLib() {
        return FishModLoader.hasMod("pinin");
    }

    private void tryInit() {
        try {
            this.pinInImpl = new PinInImpl();
            this.isValid = true;
        } catch (Exception e) {
            ManyLib.logger.warn("PinyinHandler: found pinYin mod, but failed to init");
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

    public int compareChars(char input1, char input2) {
        return this.convertToLetters(input1).compareToIgnoreCase(this.convertToLetters(input2));
    }

    public boolean contains(String provider, String input) {
        return this.pinInImpl.contains(provider, input);
    }

    private String convertToLetters(char c) {
        return this.pinInImpl.convertToLetters(c);
    }
}
