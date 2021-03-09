package dev.sim0n.client.mod;

import dev.sim0n.client.event.Event;
import dev.sim0n.client.event.EventFactory;
import dev.sim0n.client.event.EventHandler;
import dev.sim0n.client.manager.EventManager;
import dev.sim0n.client.setting.Setting;
import dev.sim0n.client.setting.impl.BooleanSetting;
import dev.sim0n.client.setting.impl.DoubleSetting;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;

import java.util.*;
import java.util.function.Consumer;

@Getter
public abstract class Mod {
    protected final Minecraft mc = Minecraft.getMinecraft();

    private final List<Setting> settings = new LinkedList<>();

    private final String name;
    private final ModType type;

    @Setter
    private boolean state;

    private Set<EventHandler<?>> eventHandlers = new HashSet<>();

    public Mod(String name, ModType type) {
        this.name = name;
        this.type = type;
    }

    protected <T> void registerEventHandler(Class<? extends Event> clazz, Consumer<T> handlerConsumer) {
        eventHandlers.add(EventFactory.create(clazz, handlerConsumer));
    }

    protected <T extends Setting> void addSetting(T t) {
        this.settings.add(t);
    }

    @SuppressWarnings("unchecked") private <T extends Setting> T getSetting(String name) {
        return (T) settings.stream()
                .filter(setting -> setting.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    protected <T extends BooleanSetting> T getBooleanSetting(String name) {
        return this.getSetting(name);
    }

    protected <T extends DoubleSetting> T getFloatSetting(String name) {
        return this.getSetting(name);
    }

    public void onEnable() {
        eventHandlers.forEach(EventManager.INSTANCE::register);
    }

    public void toggle() {
        state = !state;

        if (state)
            onEnable();
        else
            onDisable();
    }

    public void onDisable() {
        eventHandlers.forEach(EventManager.INSTANCE::unregister);
    }
}
