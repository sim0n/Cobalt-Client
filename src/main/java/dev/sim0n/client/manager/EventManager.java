package dev.sim0n.client.manager;

import dev.sim0n.client.event.Event;
import dev.sim0n.client.event.EventHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public enum EventManager {
    INSTANCE;

    private final List<EventHandler<?>> handlers = new CopyOnWriteArrayList<>();

    public void register(EventHandler<?> handler) {
        handlers.add(handler);
    }

    public void unregister(EventHandler<?> handler) {
        handlers.add(handler);
    }

    public <T extends Event> T post(T event) {
        for (EventHandler eventHandler : handlers) {

            if (eventHandler.getType() != event.getClass())
                continue;

            eventHandler.handle(event);

            if (event.isCancelled())
                break;
        }

        return event;
    }
}
