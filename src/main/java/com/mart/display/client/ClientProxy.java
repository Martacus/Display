package com.mart.display.client;

import com.mart.display.client.render.RenderDisplay;
import com.mart.display.common.CommonProxy;
import com.mart.display.common.tile.TileDisplay;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerItemRenderer(Item item, int meta) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    @Override
    public void preInit() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileDisplay.class, new RenderDisplay());
    }


}