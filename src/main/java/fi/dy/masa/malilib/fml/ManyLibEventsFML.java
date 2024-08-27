package fi.dy.masa.malilib.fml;

import com.google.common.eventbus.Subscribe;
import fi.dy.masa.malilib.command.CommandMain;
import net.xiaoyu233.fml.reload.event.CommandRegisterEvent;

public class ManyLibEventsFML {
    @Subscribe
    public void onCommandRegister(CommandRegisterEvent commandRegisterEvent) {
        commandRegisterEvent.register(new CommandMain());
    }
}
