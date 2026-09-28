package fi.dy.masa.malilib.command;

import fi.dy.masa.malilib.api.ManyLibApi;
import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.core.Side;
import fi.dy.masa.malilib.localization.CommandText;
import net.minecraft.CommandBase;
import net.minecraft.ICommandSender;

import java.util.List;
import java.util.Map;

public class CommandReload implements IManyLibCommand {

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) {
        int length = strings.length;

        if (length == 1) {
            String key = strings[0];
            Map<Side, IConfigHandler> map = ManyLibApi.getSideMap(key);
            if (map != null) {
                for (IConfigHandler config : map.values()) {
                    config.load();
                }
                CommandBase.notifyAdmins(iCommandSender, CommandText.RELOAD_SUCCESS.getKey(), key);
            } else {
                iCommandSender.sendChatToPlayer(CommandText.CONFIG_NOT_FOUND.component(key));
            }
        } else {
            iCommandSender.sendChatToPlayer(CommandText.RELOAD_USAGE.component());
        }
    }

    @Override
    @SuppressWarnings("unchecked, rawtypes")
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        int length = par2ArrayOfStr.length;
        if (length == 1) {
            return CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, ManyLibApi.streamIds().toArray(String[]::new));
        }
        return null;
    }

}