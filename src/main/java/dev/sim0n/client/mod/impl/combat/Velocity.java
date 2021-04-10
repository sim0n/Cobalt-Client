package dev.sim0n.client.mod.impl.combat;

import dev.sim0n.client.event.impl.player.PlayerUpdateEvent;
import dev.sim0n.client.mod.Mod;
import dev.sim0n.client.mod.ModType;
import dev.sim0n.client.setting.impl.DoubleSetting;
import dev.sim0n.client.setting.impl.IntegerSetting;
import net.minecraft.entity.player.EntityPlayer;

public class Velocity extends Mod {
    private final DoubleSetting horizontal = new DoubleSetting("Horizontal", "The horizontal factor", 0D, 2D, 1D);
    private final DoubleSetting vertical = new DoubleSetting("Vertical", "The vertical factor", 0D, 2D, 1D);

    private final IntegerSetting delay = new IntegerSetting("Delay", "The delay until enabled", 0, 9, 0);

    public Velocity() {
        super("Velocity", ModType.COMBAT);

        addSettings(horizontal, vertical, delay);

        registerEventHandler(PlayerUpdateEvent.class, event -> {
            EntityPlayer player = mc.thePlayer;

            if (player.maxHurtResistantTime - delay.getValue() == player.hurtResistantTime && player.maxHurtResistantTime != 0) {
                player.setVelocity(
                        player.motionX * horizontal.getValue(),
                        player.motionY * vertical.getValue(),
                        player.motionZ * horizontal.getValue()
                );
            }
        });

        /*registerEventHandler(PacketEvent.class, event -> {
            S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();

        }, event -> {
            if (event.getPacket() instanceof S12PacketEntityVelocity) {
                S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();

                return packet.getEntityID() == mc.thePlayer.getEntityId();
            }

            return false;
        });*/
    }
}
