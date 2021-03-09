package dev.sim0n.client;

import dev.sim0n.client.event.forge.FEventListener;
import dev.sim0n.client.event.forge.impl.FClientTickListener;
import dev.sim0n.client.event.forge.impl.FPacketListener;
import dev.sim0n.client.event.forge.impl.FRenderTickListener;
import dev.sim0n.client.manager.CommandManager;
import dev.sim0n.client.manager.ModManager;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Client {
    @Getter
    private static Client instance;

    private ModManager modManager;
    private CommandManager commandManager;

    private final Set<FEventListener> forgeListeners = new HashSet<>();

    public Client() {
        instance = this;

        start();
    }

    public void start() {
        System.out.println("Starting client...");

        registerManagers();
        registerEvents();
    }

    private void registerManagers() {
        modManager = new ModManager();
        commandManager = new CommandManager();
    }

    private void registerEvents() {
        forgeListeners.add(new FRenderTickListener());
        forgeListeners.add(new FClientTickListener());
        forgeListeners.add(new FPacketListener());

        forgeListeners.forEach(FEventListener::register);
    }
}
