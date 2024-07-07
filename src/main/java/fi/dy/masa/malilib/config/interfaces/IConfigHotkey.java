package fi.dy.masa.malilib.config.interfaces;

import net.minecraft.Minecraft;

import java.util.function.Consumer;

public interface IConfigHotkey extends IConfigValue {
    int getKeyCode();
    String getKeyName();

    void setHotKeyAndHash(int keyCode);

    void setHotKeyWithoutHash(int keyCode);

    void setHotKeyPressCallBack(Consumer<Minecraft> callBack);

    void onPressed(Minecraft minecraft);
}
