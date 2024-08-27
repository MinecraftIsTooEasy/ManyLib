package fi.dy.masa.malilib.command;

import net.minecraft.CommandBase;
import net.minecraft.ICommandSender;
import net.minecraft.WrongUsageException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandMain extends CommandBase {
    private final Map<String, IManyLibCommand> commandMap = new HashMap<>() {{
        this.put("reload", new CommandReload());
        this.put("reloadAll", new CommandReloadAll());
    }};

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandName() {
        return "manyLib";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "commands.manyLib.usage";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) {
        if (strings.length == 0) {
            throw new WrongUsageException("commands.manyLib.usage");
        }
        if (this.commandMap.containsKey(strings[0])) {
            String[] newStrings = new String[strings.length - 1];
            System.arraycopy(strings, 1, newStrings, 0, strings.length - 1);
            this.commandMap.get(strings[0]).processCommand(iCommandSender, newStrings);
        } else {
            throw new WrongUsageException("commands.manyLib.usage");
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender iCommandSender, String[] strings) {
        int length = strings.length;
        if (length == 1)
            return getListOfStringsMatchingLastWord(strings, this.commandMap.keySet().toArray(String[]::new));
        if (length > 1) {
            String string = strings[0];
            if (this.commandMap.containsKey(string)) {
                String[] newStrings = new String[strings.length - 1];
                System.arraycopy(strings, 1, newStrings, 0, strings.length - 1);
                return this.commandMap.get(string).addTabCompletionOptions(iCommandSender, newStrings);
            }
        }
        return null;
    }
}
