package dev.sim0n.client.event;

import java.util.function.Consumer;
import java.util.function.Predicate;

public final class EventFactory {

    public static <E> EventHandler<?> create(Class<? extends Event> clazz, Consumer<E> handlerConsumer, Predicate<E> condition) {
        return new LambdaBasedEventHandler(clazz, handlerConsumer, condition);
    }

    public static <E> EventHandler<?> create(Class<? extends Event> clazz, Consumer<E> handlerConsumer) {
        return new LambdaBasedEventHandler(clazz, handlerConsumer);
    }

}

