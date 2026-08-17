package fi.dy.masa.malilib.command;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.localization.CommandText;
import net.minecraft.CommandBase;
import net.minecraft.ICommandSender;

import java.util.List;

public class CommandReloadAll implements IManyLibCommand {

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) {
        if (strings.length == 0) {
            ConfigManager.getInstance().loadAllConfigs();
            CommandBase.notifyAdmins(iCommandSender, CommandText.RELOAD_ALL_SUCCESS.getKey());
        } else {
            iCommandSender.sendChatToPlayer(CommandText.RELOAD_ALL_USAGE.component());
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] strings) {
        return null;
    }
}