package fi.dy.masa.malilib.gui.button;

import fi.dy.masa.malilib.gui.ManyLibIcons;
import fi.dy.masa.malilib.gui.button.interfaces.IButtonActionListener;

import java.util.function.BooleanSupplier;

public class ResetButton extends ButtonGeneric {

    final BooleanSupplier predicate;

    public ResetButton(int x, int y, BooleanSupplier predicate, IButtonActionListener listener) {
        super(x, y, 20, 20, "", listener);
        this.predicate = predicate;
        this.icon = ManyLibIcons.ResetButton;
        this.renderDefaultBackground = false;
        this.setOnUpdate(button -> this.setEnabled(this.predicate.getAsBoolean()));
    }
}
