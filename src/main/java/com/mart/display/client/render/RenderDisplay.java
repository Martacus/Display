package com.mart.display.client.render;

import com.mart.display.common.tile.TileDisplay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

public class RenderDisplay extends TileEntitySpecialRenderer<TileDisplay> {

    @Override
    public void render(TileDisplay tileDisplay, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        ItemStack inputStack = tileDisplay.getItemStack();

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);

        if(tileDisplay.isRotation()){
            RenderUtil.renderFloatingOnBlockWithCoords(inputStack, tileDisplay, 0.5, 0.75, 0.5);
        }
        else{
            RenderUtil.renderFloatingNoRotationOnBlockWithCoords(inputStack, tileDisplay, 0.5, 0.75, 0.5);
        }

        GlStateManager.popMatrix();
    }
}
