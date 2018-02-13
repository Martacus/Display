package com.mart.display.common.registry;

import com.mart.display.Display;
import com.mart.display.common.tile.TileDisplay;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTiles {

    public static TileDisplay display = new TileDisplay();

    public static void init(){
        GameRegistry.registerTileEntity(display.getClass(), Display.MODID + "display");
    }

}
