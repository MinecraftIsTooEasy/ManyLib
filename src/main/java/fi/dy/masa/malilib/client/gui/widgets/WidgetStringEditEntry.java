package fi.dy.masa.malilib.client.gui.widgets;

import fi.dy.masa.malilib.client.gui.ManyLibIcons;
import fi.dy.masa.malilib.client.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.client.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.client.gui.widgets.text.TextField;

import java.util.List;

public class WidgetStringEditEntry extends WidgetContainer {
    private final int realIndex;
    private final String originalString;
    private final List<String> tempList;
    private final Runnable markDirty;

    public WidgetStringEditEntry(int realIndex, String originalString, List<String> tempList, Runnable markDirty) {
        super(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);// dummy
        this.realIndex = realIndex;
        this.originalString = originalString;
        this.tempList = tempList;
        this.markDirty = markDirty;
    }

    @Override
    public void init() {
        super.init();

        WidgetText markNumber = WidgetText.of(String.valueOf(realIndex)).position(x, y + ScreenConstants.commentedTextShift);
        this.addWidget(markNumber);

        WidgetTextField<TextField> widgetTextField = new WidgetTextField<>(new TextField(x + 20, y, 150, 18), s -> tempList.set(realIndex, s.getText()));
        widgetTextField.setText(this.originalString);
        this.addWidget(widgetTextField);

        this.addWidget(ButtonGeneric.builder(ManyLibIcons.PLUS, button -> insertBelow(realIndex)).dimensions(x + 180, y, 15, 15).build());
        this.addWidget(ButtonGeneric.builder(ManyLibIcons.MINUS, button -> delete(realIndex)).dimensions(x + 200, y, 15, 15).build());
        this.addWidget(ButtonGeneric.builder(ManyLibIcons.ARROW_UP, button -> moveUp(realIndex)).dimensions(x + 220, y, 15, 15).build());
        this.addWidget(ButtonGeneric.builder(ManyLibIcons.ARROW_DOWN, button -> moveDown(realIndex)).dimensions(x + 240, y, 15, 15).build());
    }

    private void markDirty() {
        this.markDirty.run();
    }

    private void delete(int operant) {
        this.tempList.remove(operant);
        this.markDirty();
    }

    private void insertBelow(int operant) {
        this.tempList.add(operant + 1, "");
        this.markDirty();
    }

    private void moveUp(int operant) {
        if (operant == 0) return;
        String string = this.tempList.get(operant);
        this.tempList.set(operant, this.tempList.get(operant - 1));
        this.tempList.set(operant - 1, string);
        this.markDirty();
    }

    private void moveDown(int operant) {
        if (operant == this.tempList.size() - 1) return;
        String string = this.tempList.get(operant);
        this.tempList.set(operant, this.tempList.get(operant + 1));
        this.tempList.set(operant + 1, string);
        this.markDirty();
    }
}
