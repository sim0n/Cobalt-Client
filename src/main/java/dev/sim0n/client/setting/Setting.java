package dev.sim0n.client.setting;

public interface Setting<T> {
    String getName();

    String getDescription();

    T getValue();

    void setValue(T value);
}
