package dev.sim0n.client.util;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;

@UtilityClass
public class ReflectionUtil {

    public Field getField(Class clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);

            return field;
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException(clazz.getSimpleName() + ":" + fieldName);
        }
    }

    public <T> T getFieldValue(Field field, Object instance) {
        try {
            return (T) field.get(instance);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void setFieldValue(Field field, Object instance, Object value) {
        try {
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException();
        }
    }
}

