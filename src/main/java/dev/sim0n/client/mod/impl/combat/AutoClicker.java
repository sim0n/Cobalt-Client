package dev.sim0n.client.mod.impl.combat;

import dev.sim0n.client.event.impl.player.ClientTickEvent;
import dev.sim0n.client.event.impl.render.RenderTickEvent;
import dev.sim0n.client.mod.Mod;
import dev.sim0n.client.mod.ModType;
import dev.sim0n.client.setting.impl.BooleanSetting;
import dev.sim0n.client.setting.impl.IntegerSetting;
import dev.sim0n.client.util.MathUtil;
import dev.sim0n.client.util.MouseUtil;
import dev.sim0n.client.util.ReflectionUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

import java.lang.reflect.Field;

public class AutoClicker extends Mod {
    private Field clickCounterField;

    private long nextLUp, nextLDown;

    private long nextDrop;
    private long nextExhaust;

    private double dropRate;

    private boolean dropping;

    private final IntegerSetting minCps = new IntegerSetting("Min CPS", "The minimum CPS", 0, 20, 7);
    private final IntegerSetting maxCps = new IntegerSetting("Max CPS", "The maximum CPS", 0, 20, 13);

    private final BooleanSetting disableClickDelay = new BooleanSetting("Disable Click Delay", "Disables MC's between hits attack delay", true);
    private final BooleanSetting disableOnBlock = new BooleanSetting("Disable On Blocks", "Stops clicking if you're digging a block", true);

    public AutoClicker() {
        super("AutoClicker", ModType.COMBAT);

        try {
            clickCounterField = ReflectionUtil.getField(Minecraft.class, "leftClickCounter");
        } catch (IllegalArgumentException e) {
            clickCounterField = ReflectionUtil.getField(Minecraft.class, "field_71429_W");
        }

        addSetting(minCps);
        addSetting(maxCps);
        addSetting(disableClickDelay);
        addSetting(disableOnBlock);

        registerEventHandler(ClientTickEvent.class, (ClientTickEvent event) -> {
            if (mc.thePlayer == null)
                return;

            if (event.getPhase() == TickEvent.Phase.END && disableClickDelay.getValue()) {
                ReflectionUtil.setFieldValue(clickCounterField, mc, 0);
            }
        });

        registerEventHandler(RenderTickEvent.class, event -> {
            long now = System.currentTimeMillis();

            if (mc.currentScreen == null) {
                Mouse.poll();

                if (Mouse.isButtonDown(0)) {
                    if (disableOnBlock.getValue()) {
                        if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                            KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindAttack.getKeyCode(), true);
                            return;
                        }
                    }

                    if (nextLDown > 0L && nextLUp > 0L) {
                        if (now > nextLDown) {
                            MouseUtil.sendClick(0, true);
                            randomiseLeft();
                        } else if (now > nextLUp) {
                            MouseUtil.sendClick(0, false);
                        }
                    } else {
                        randomiseLeft();
                    }
                } else {
                    nextLUp = nextLDown = 0L;
                }
            }
        });
    }

    private void randomiseLeft() {
        double minCps = this.minCps.getValue();
        double maxCps = this.maxCps.getValue();

        double cps = minCps + (MathUtil.RANDOM.nextDouble() * (maxCps - minCps));
        long delay = (int) Math.round(1000.0D / cps);

        long now = System.currentTimeMillis();

        if (now > nextDrop) {
            if (!dropping && MathUtil.RANDOM.nextInt(100) >= 83) {
                dropping = true;
                dropRate = 1.1D + (MathUtil.RANDOM.nextDouble() * 0.2D);
            } else {
                dropping = false;
            }

            nextDrop = now + 450L + (long) MathUtil.RANDOM.nextInt(1500);
        }

        if (dropping) {
            delay *= dropRate;
        }

        if (now > nextExhaust) {
            if (MathUtil.RANDOM.nextInt(100) >= 77) {
                delay += (50L + (long) MathUtil.RANDOM.nextInt(150));
            }

            nextExhaust = System.currentTimeMillis() + 450L + (long) MathUtil.RANDOM.nextInt(1500);
        }

        nextLDown = System.currentTimeMillis() + delay;
        nextLUp = System.currentTimeMillis() + (delay / 2) - MathUtil.RANDOM.nextInt(10) + MathUtil.RANDOM.nextInt(10);
    }
}
