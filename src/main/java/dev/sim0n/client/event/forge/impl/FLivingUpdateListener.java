package dev.sim0n.client.event.forge.impl;

import dev.sim0n.client.event.forge.FEventListener;
import dev.sim0n.client.event.impl.player.PlayerUpdateEvent;
import dev.sim0n.client.manager.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class FLivingUpdateListener extends FEventListener {

    public FLivingUpdateListener() {
        super(LivingEvent.LivingUpdateEvent.class);
    }

    @Override
    public void onEvent(Event event) {
        if (((LivingEvent.LivingUpdateEvent) event).entity.equals(Minecraft.getMinecraft().thePlayer)) {
            EventManager.INSTANCE.post(new PlayerUpdateEvent());
        }
    }
}
