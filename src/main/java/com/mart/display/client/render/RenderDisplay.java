package com.mart.display.client.render;

import com.mart.display.common.tile.TileDisplay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

public class RenderDisplay extends TileEntitySpecialRenderer<TileDisplay> {

    @Override
    public void render(TileDisplay tileDisplay, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        ItemStack inputStack = tileDisplay.getItemStack();
        RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();

        if(inputStack.isEmpty()){
            return;
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + tileDisplay.getxAxisCoord(), y + tileDisplay.getyAxisCoord(), z + tileDisplay.getzAxisCoord());

        EntityItem entityitem = new EntityItem(tileDisplay.getWorld(), 0.0D, 0.0D, 0.0D, inputStack);
        entityitem.getItem().setCount(1);
        entityitem.hoverStart = 0.0F;

        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();

        float rotation;

        if(tileDisplay.isRotation()){
             rotation = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);
        }
        else {
            rotation = tileDisplay.getRotationDegrees();
        }

        GlStateManager.rotate(rotation, 0.0F, 1.0F, 0);
        GlStateManager.scale(0.5F, 0.5F, 0.5F);

        RenderHelper.enableStandardItemLighting();
        itemRenderer.renderItem(entityitem.getItem(), ItemCameraTransforms.TransformType.FIXED);
        RenderHelper.disableStandardItemLighting();

        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }
}
