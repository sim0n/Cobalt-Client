package dev.sim0n.client.event.forge.impl;

import dev.sim0n.client.event.forge.FEventListener;
import dev.sim0n.client.event.impl.player.ClientTickEvent;
import dev.sim0n.client.manager.EventManager;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FClientTickListener extends FEventListener {
    public FClientTickListener() {
        super(TickEvent.ClientTickEvent.class);
    }

    @Override
    public void onEvent(Event event) {
        switch (((TickEvent.ClientTickEvent) event).phase) {
            case START:
                EventManager.INSTANCE.post(new ClientTickEvent(TickEvent.Phase.START));
                break;

            case END:
                EventManager.INSTANCE.post(new ClientTickEvent(TickEvent.Phase.END));
                break;
        }
    }
}
