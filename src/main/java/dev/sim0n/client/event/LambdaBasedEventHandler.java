package dev.sim0n.client.event;

import lombok.Getter;

import java.util.function.Consumer;
import java.util.function.Predicate;

@Getter
public class LambdaBasedEventHandler<T extends Event> implements EventHandler<T> {
    private final Class<?> type;

    private final Consumer<Event> handlerConsumer;

    private Predicate<T> condition = t -> true;

    public LambdaBasedEventHandler(Class<?> type, Consumer<Event> handlerConsumer, Predicate<T> condition) {
        this.type = type;
        this.handlerConsumer = handlerConsumer;
        this.condition = condition;
    }

    public LambdaBasedEventHandler(Class<?> type, Consumer<Event> handlerConsumer) {
        this.type = type;
        this.handlerConsumer = handlerConsumer;
    }

    @Override
    public void handle(T event) {
        if (condition.test(event))
            handlerConsumer.accept(event);
    }
}
