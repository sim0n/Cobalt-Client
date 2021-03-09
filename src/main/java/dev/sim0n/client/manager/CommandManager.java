package dev.sim0n.client.manager;

import dev.sim0n.client.Client;
import dev.sim0n.client.command.Command;
import dev.sim0n.client.command.impl.ModCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CommandManager {
    private final List<Command> commands = new ArrayList<>();

    public CommandManager() {
        Client.getInstance().getModManager().getMods().forEach(mod -> commands.add(new ModCommand(mod)));
    }

    public boolean handleCommand(String message) {
        String[] parts = message.split(" ");

        Optional<Command> command = commands.stream()
                .filter(cmd -> cmd.getName().equalsIgnoreCase(parts[0]))
                .findFirst();

        if (!command.isPresent())
            return false;

        String[] args = new String[parts.length - 1];

        System.arraycopy(parts, 1, args, 0, parts.length - 1);

        command.get().handle(Arrays.asList(args));
        return true;
    }
}
