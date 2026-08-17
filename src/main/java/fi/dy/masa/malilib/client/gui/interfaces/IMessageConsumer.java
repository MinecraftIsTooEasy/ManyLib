package fi.dy.masa.malilib.client.gui.interfaces;

import fi.dy.masa.malilib.client.gui.Message.MessageType;

public interface IMessageConsumer {
    void addMessage(MessageType type, String messageKey, Object... args);

    void addMessage(MessageType type, int lifeTime, String messageKey, Object... args);
}
