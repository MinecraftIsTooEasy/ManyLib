package fi.dy.masa.malilib.util;

import net.minecraft.GuiScreen;
import net.minecraft.Minecraft;
import net.minecraft.ScaledResolution;

import javax.annotation.Nullable;

public class GuiUtils {
    public static int getScaledWindowWidth() {
        Minecraft minecraft = Minecraft.getMinecraft();
        ScaledResolution scaledResolution = new ScaledResolution(minecraft.gameSettings, minecraft.displayWidth, minecraft.displayHeight);
        return scaledResolution.getScaledWidth();
    }

    public static int getScaledWindowHeight() {
        Minecraft minecraft = Minecraft.getMinecraft();
        ScaledResolution scaledResolution = new ScaledResolution(minecraft.gameSettings, minecraft.displayWidth, minecraft.displayHeight);
        return scaledResolution.getScaledHeight();
    }

    public static int getDisplayWidth() {
        return Minecraft.getMinecraft().displayWidth;
    }

    public static int getDisplayHeight() {
        return Minecraft.getMinecraft().displayHeight;
    }

    public static int getScaleFactor() {
        Minecraft minecraft = Minecraft.getMinecraft();
        ScaledResolution scaledResolution = new ScaledResolution(minecraft.gameSettings, minecraft.displayWidth, minecraft.displayHeight);
        return scaledResolution.getScaleFactor();
    }

    @Nullable
    public static GuiScreen getCurrentScreen() {
        return Minecraft.getMinecraft().currentScreen;
    }
//
//    public static void createBlockPosInputsVertical(int x, int y, int textFieldWidth, BlockPos pos,
//                                                    ICoordinateValueModifier modifier, boolean addButton, GuiBase gui) {
//        createBlockPosInput(x, y, textFieldWidth, CoordinateType.X, pos, modifier, addButton, gui);
//        createBlockPosInput(x, y + 17, textFieldWidth, CoordinateType.Y, pos, modifier, addButton, gui);
//        createBlockPosInput(x, y + 34, textFieldWidth, CoordinateType.Z, pos, modifier, addButton, gui);
//    }
//
//    public static void createVec3dInputsVertical(int x, int y, int textFieldWidth, Vec3d pos,
//                                                 ICoordinateValueModifier modifier, boolean addButton, GuiBase gui) {
//        createVec3dInput(x, y, textFieldWidth, CoordinateType.X, pos, modifier, addButton, gui);
//        createVec3dInput(x, y + 17, textFieldWidth, CoordinateType.Y, pos, modifier, addButton, gui);
//        createVec3dInput(x, y + 34, textFieldWidth, CoordinateType.Z, pos, modifier, addButton, gui);
//    }
//
//    public static void createBlockPosInput(int x, int y, int textFieldWidth, CoordinateType type, BlockPos pos,
//                                           ICoordinateValueModifier modifier, boolean addButton, GuiBase gui) {
//        x = addLabel(x, y, type, gui);
//
//        GuiTextFieldInteger textField = new GuiTextFieldInteger(x, y + 1, textFieldWidth, 14, Minecraft.getMinecraft().textRenderer);
//        textField.setText(getCoordinateValueString(type, pos));
//
//        addTextFieldAndButton(x + textFieldWidth + 4, y, type, modifier, textField, addButton, gui);
//    }
//
//    public static void createVec3dInput(int x, int y, int textFieldWidth, CoordinateType type, Vec3d pos,
//                                        ICoordinateValueModifier modifier, boolean addButton, GuiBase gui) {
//        x = addLabel(x, y, type, gui);
//
//        GuiTextFieldDouble textField = new GuiTextFieldDouble(x, y + 1, textFieldWidth, 14, Minecraft.getMinecraft().textRenderer);
//        textField.setText(getCoordinateValueString(type, pos));
//
//        addTextFieldAndButton(x + textFieldWidth + 4, y, type, modifier, textField, addButton, gui);
//    }
//
//    protected static void addTextFieldAndButton(int x, int y, CoordinateType type, ICoordinateValueModifier modifier,
//                                                GuiTextFieldGeneric textField, boolean addButton, GuiBase gui) {
//        gui.addTextField(textField, new TextFieldListenerCoordinateInput(type, modifier));
//
//        if (addButton) {
//            String hover = StringUtils.translate("malilib.gui.button.hover.plus_minus_tip");
//            ButtonGeneric button = new ButtonGeneric(x, y, MaLiLibIcons.BTN_PLUSMINUS_16, hover);
//            gui.addButton(button, new ButtonListenerCoordinateInput(type, modifier));
//        }
//    }
//
//    public static String getCoordinateValueString(CoordinateType type, BlockPos pos) {
//        switch (type) {
//            case X:
//                return String.valueOf(pos.getX());
//            case Y:
//                return String.valueOf(pos.getY());
//            case Z:
//                return String.valueOf(pos.getZ());
//        }
//
//        return "";
//    }
//
//    public static String getCoordinateValueString(CoordinateType type, Vec3d pos) {
//        switch (type) {
//            case X:
//                return String.valueOf(pos.x);
//            case Y:
//                return String.valueOf(pos.y);
//            case Z:
//                return String.valueOf(pos.z);
//        }
//
//        return "";
//    }
//
//    protected static int addLabel(int x, int y, CoordinateType type, GuiBase gui) {
//        String label = type.name() + ":";
//        int labelWidth = 0;
//
//        for (CoordinateType t : CoordinateType.values()) {
//            labelWidth = Math.max(labelWidth, StringUtils.getStringWidth(t.name() + ":") + 4);
//        }
//
//        gui.addLabel(x, y, labelWidth, 20, 0xFFFFFFFF, label);
//        x += labelWidth;
//
//        return x;
//    }
//
//    public static class TextFieldListenerCoordinateInput implements ITextFieldListener<GuiTextFieldGeneric> {
//        protected final ICoordinateValueModifier modifier;
//        protected final CoordinateType type;
//
//        public TextFieldListenerCoordinateInput(CoordinateType type, ICoordinateValueModifier modifier) {
//            this.modifier = modifier;
//            this.type = type;
//        }
//
//        @Override
//        public boolean onTextChange(GuiTextFieldGeneric textField) {
//            this.modifier.setValueFromString(this.type, textField.getText());
//
//            return false;
//        }
//    }
//
//    public static class ButtonListenerCoordinateInput implements IButtonActionListener {
//        protected final ICoordinateValueModifier modifier;
//        protected final CoordinateType type;
//
//        public ButtonListenerCoordinateInput(CoordinateType type, ICoordinateValueModifier modifier) {
//            this.modifier = modifier;
//            this.type = type;
//        }
//
//        @Override
//        public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
//            int amount = mouseButton == 1 ? -1 : 1;
//            if (GuiBase.isShiftDown()) {
//                amount *= 8;
//            }
//            if (GuiBase.isAltDown()) {
//                amount *= 4;
//            }
//
//            this.modifier.modifyValue(this.type, amount);
//        }
//
//        public enum Type {
//            NUDGE_COORD_X,
//            NUDGE_COORD_Y,
//            NUDGE_COORD_Z;
//        }
//    }
}
