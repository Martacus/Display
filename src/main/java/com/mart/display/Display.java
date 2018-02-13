package com.mart.display;

import com.mart.display.common.CommonProxy;
import com.mart.display.common.registry.ModTiles;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.logging.Logger;

@Mod(modid = Display.MODID, version = Display.VERSION)
public class Display {

    public static final String MODID = "display";
    public static final String name = "Display";
    public static final String VERSION = "1.1";

    public static Logger logger;

    @SidedProxy(serverSide = "com.mart.display.common.CommonProxy", clientSide = "com.mart.display.client.ClientProxy")
    public static CommonProxy proxy;

    @Mod.Instance(MODID)
    public static Display instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModTiles.init();

        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
