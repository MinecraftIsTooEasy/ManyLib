package fi.dy.masa.malilib.command;

import fi.dy.masa.malilib.config.ConfigManager;
import net.minecraft.CommandBase;
import net.minecraft.CommandException;
import net.minecraft.ICommandSender;
import net.minecraft.WrongUsageException;

import java.util.List;
import java.util.Set;

public class CommandReload implements IManyLibCommand {
    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) {
        int length = strings.length;
        if (length == 1) {
            String key = strings[0];
            if (ConfigManager.getInstance().getConfigMap().containsKey(key)) {
                ConfigManager.getInstance().getConfigMap().get(key).load();
                CommandBase.notifyAdmins(iCommandSender, "commands.manyLib.reload.success", key);
            } else {
                throw new CommandException("commands.manyLib.reload.configNotFound");
            }
        } else {
            throw new WrongUsageException("commands.manyLib.reload.usage");
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        int length = par2ArrayOfStr.length;
        if (length == 1) {
            return CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, this.getAllConfigNames().toArray(String[]::new));
        }
        return null;
    }

    private Set<String> getAllConfigNames() {
        return ConfigManager.getInstance().getConfigMap().keySet();
    }
}
