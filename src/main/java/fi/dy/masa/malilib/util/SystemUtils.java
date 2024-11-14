package fi.dy.masa.malilib.util;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

public class SystemUtils {
    public static void copyToClipboard(String content) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(content);
        clipboard.setContents(selection, null);
    }

    public static String getClipboardContent() throws IOException, UnsupportedFlavorException {
        String ret = "";
        Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable clipTf = sysClip.getContents(null);

        if (clipTf != null) {
            if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return (String) clipTf.getTransferData(DataFlavor.stringFlavor);
            }
        }
        return ret;
    }
}
