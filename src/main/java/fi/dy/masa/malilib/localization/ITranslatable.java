package fi.dy.masa.malilib.localization;

import fi.dy.masa.malilib.client.util.StringUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatMessageComponent;

public interface ITranslatable {
    String getKey();

    @Environment(EnvType.CLIENT)
    default String translate() {
        return StringUtils.translate(this.getKey());
    }

    @Environment(EnvType.CLIENT)
    default String translate(Object... args) {
        return StringUtils.translate(this.getKey(), args);
    }

    default ChatMessageComponent component() {
        return ChatMessageComponent.createFromTranslationKey(this.getKey());
    }

    default ChatMessageComponent component(Object... args) {
        return ChatMessageComponent.createFromTranslationWithSubstitutions(this.getKey(), args);
    }
}
