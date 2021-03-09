package dev.sim0n.client.event;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class LambdaBasedEventHandler<T extends Event> implements EventHandler<T> {
    private final Class<?> type;

    private final Consumer<Event> handlerConsumer;

    @Override
    public Class<?> getType() {
        return type;
    }

    @Override
    public void handle(T event) {
        handlerConsumer.accept(event);
    }
}
