package dev.sim0n.client.util;

import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import org.lwjgl.input.Mouse;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

@UtilityClass
public class MouseUtil {

    private Field buttonStateField;
    private Field buttonField;

    private Field buttonsField;

    static {
        try {
            buttonField = MouseEvent.class.getDeclaredField("button");
            buttonField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            //
        }

        try {
            buttonStateField = MouseEvent.class.getDeclaredField("buttonstate");
            buttonStateField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            //
        }

        try {
            buttonsField = Mouse.class.getDeclaredField("buttons");
            buttonsField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            //
        }
    }

    public void sendClick(int button, boolean state) {
        Minecraft mc = Minecraft.getMinecraft();
        int keyBind = button == 0 ? mc.gameSettings.keyBindAttack.getKeyCode() : mc.gameSettings.keyBindUseItem.getKeyCode();

        KeyBinding.setKeyBindState(button == 0 ? mc.gameSettings.keyBindAttack.getKeyCode() : mc.gameSettings.keyBindUseItem.getKeyCode(), state);
        if (state) {
            KeyBinding.onTick(keyBind);
        }

        try {
            MouseEvent mouseEvent = new MouseEvent();

           // buttonField.setAccessible(true);
            buttonField.set(mouseEvent, button);
          //  buttonField.setAccessible(false);

          //  buttonStateField.setAccessible(true);
            buttonStateField.set(mouseEvent, state);
           // buttonStateField.setAccessible(false);

            //buttonsField.setAccessible(true);
            ByteBuffer buffer = (ByteBuffer) buttonsField.get(null);
            //  buttonsField.setAccessible(false);

            buffer.put(button, (byte) (state ? 1 : 0));

        } catch (Exception e) {
            e.printStackTrace();
            //
        }
    }
}

