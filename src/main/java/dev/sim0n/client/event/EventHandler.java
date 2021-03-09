package dev.sim0n.client.event;

public interface EventHandler<T extends Event> {
    Class<?> getType();

    void handle(T event);
}
