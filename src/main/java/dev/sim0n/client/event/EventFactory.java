package dev.sim0n.client.event;

import java.util.function.Consumer;

public final class EventFactory {

    public static <E> EventHandler<?> create(Class<? extends Event> clazz, Consumer<E> handlerConsumer) {
        return new LambdaBasedEventHandler(clazz, handlerConsumer);
    }

}

