package dev.sim0n.client.event.forge.impl;

import dev.sim0n.client.event.forge.FEventListener;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FPacketListener extends FEventListener {
    public FPacketListener() {
        super(TickEvent.ClientTickEvent.class);
    }

    @Override
    public void onEvent(Event event) {
        ChannelPipeline pipeline = getPipeline();

        if (pipeline == null) {
            return;
        }

        try {
            if (pipeline.get(PacketHandler.class) == null && pipeline.get("packet_handler") != null) {
                pipeline.addBefore("packet_handler", String.valueOf(System.currentTimeMillis()), new PacketHandler());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unregister() {
        super.unregister();

        ChannelPipeline pipeline = getPipeline();

        if (pipeline == null) {
            return;
        }

        if (pipeline.get(PacketHandler.class) != null) {
            pipeline.remove(PacketHandler.class);
        }
    }

    private ChannelPipeline getPipeline() {
        NetHandlerPlayClient netHandler = Minecraft.getMinecraft().getNetHandler();

        if (netHandler == null) {
            return null;
        }

        if (netHandler.getNetworkManager() == null) {
            return null;
        }

        try {
            return netHandler.getNetworkManager().channel().pipeline();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private class PacketHandler extends ChannelDuplexHandler {

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            if (msg instanceof C01PacketChatMessage) {
                String message = ((C01PacketChatMessage) msg).getMessage();


                if (message.startsWith(".") && FPacketListener.this.client.getCommandManager().handleCommand(message.substring(1))) {
                    return; // cancel chat message
                }
            }

            super.write(ctx, msg, promise);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

            super.channelRead(ctx, msg);
        }

    }
}
