package fi.dy.masa.malilib.gui.button.interfaces;

public interface IInteractiveElement {
    void keyTyped(char c, int i);

    void mouseClicked(int mouseX, int mouseY, int click);

    void updateScreen();
}
