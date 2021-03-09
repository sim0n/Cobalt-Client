package dev.sim0n.client.start;

import dev.sim0n.client.Client;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "cobalt client", version = "1.3.3.7")
public class ClientMod {

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        new Client();
    }

}
