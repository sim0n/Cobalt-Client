package dev.sim0n.client.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import java.util.List;

@RequiredArgsConstructor
public abstract class Command {
    protected final Minecraft mc = Minecraft.getMinecraft();

    @Getter
    private final String name;

    public abstract void handle(List<String> args);

    protected void sendMessage(String message) {
        mc.thePlayer.addChatMessage(new ChatComponentText(message));
    }
}
