package dev.sim0n.client.event.forge.impl;

import dev.sim0n.client.event.forge.FEventListener;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FRenderTickListener extends FEventListener {
    public FRenderTickListener() {
        super(TickEvent.RenderTickEvent.class);
    }

    @Override
    public void onEvent(Event event) {
        if (((TickEvent.RenderTickEvent) event).phase == TickEvent.Phase.END) {

        }
    }
}
