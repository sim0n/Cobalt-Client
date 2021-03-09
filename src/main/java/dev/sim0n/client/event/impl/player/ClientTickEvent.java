package dev.sim0n.client.event.impl.player;

import dev.sim0n.client.event.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Getter
@RequiredArgsConstructor
public class ClientTickEvent extends Event {
    private final TickEvent.Phase phase;
}
