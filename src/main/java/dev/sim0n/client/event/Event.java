package dev.sim0n.client.event;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Event {
    private boolean cancelled;
}
