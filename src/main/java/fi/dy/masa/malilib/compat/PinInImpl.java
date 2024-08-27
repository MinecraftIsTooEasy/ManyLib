package fi.dy.masa.malilib.compat;

import me.towdium.pinin.DictLoader;
import me.towdium.pinin.PinIn;
import me.towdium.pinin.elements.Char;
import me.towdium.pinin.elements.Pinyin;

public class PinInImpl {

    private final PinIn pinInContext = (new PinIn(new DictLoader.Default())).config().accelerate(true).commit();

    public PinInImpl() {
    }

    public boolean contains(String provider, String input) {
        return this.pinInContext.contains(provider, input);
    }

    public String convertToLetters(char c) {
        Char aChar = this.pinInContext.getChar(c);
        Pinyin[] pinYins = aChar.pinyins();
        if (pinYins.length == 0) {
            return String.valueOf(c);
        } else {
            return pinYins[0].toString();
        }
    }
}
