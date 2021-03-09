package dev.sim0n.client.event.forge;

import dev.sim0n.client.Client;
import dev.sim0n.client.manager.EventManager;
import dev.sim0n.client.event.impl.render.RenderTickEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.IEventListener;
import net.minecraftforge.fml.common.eventhandler.ListenerList;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public abstract class FEventListener implements IEventListener {
    protected final Client client = Client.getInstance();

    private final Class<? extends Event>[] eventClasses;

    private boolean registered = false;

    public FEventListener(Class<? extends Event>... eventClasses) {
        this.eventClasses = eventClasses;
    }

    public void register() {
        try {
            for (Class<? extends Event> clazz : eventClasses) {
                ListenerList listenerList = clazz.newInstance().getListenerList();

                if (clazz == TickEvent.RenderTickEvent.class) {
                    listenerList.register(0, EventPriority.HIGH, this);
                } else {
                    listenerList.register(0, EventPriority.NORMAL, this);
                }
            }

            this.registered = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void unregister() {
        try {
            for (Class<? extends Event> clazz : eventClasses) {
                ListenerList listenerList = clazz.newInstance().getListenerList();

                listenerList.unregister(0, this);
            }

            this.registered = false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public final void invoke(Event event) {
        if (registered) {
            // because Forge is retarded
            if (event instanceof TickEvent.RenderTickEvent) {
                EventManager.INSTANCE.post(new RenderTickEvent());
            }

            onEvent(event);
        }
    }

    public abstract void onEvent(Event event);

}
