package com.mart.display.client.render;

import com.mart.display.common.tile.TileDisplay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class RenderUtil {

    public static void renderLayingOnBlockWithCoords(ItemStack renderItem, TileEntity tileEntity, double x, double y, double z) {
        RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
        if (!renderItem.isEmpty()) {
            GlStateManager.translate(x, y, z);
            EntityItem entityitem = new EntityItem(tileEntity.getWorld(), 0.0D, 0.0D, 0.0D, renderItem);
            entityitem.getItem().setCount(1);
            entityitem.hoverStart = 0.0F;
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();

            GlStateManager.rotate(180, 0.0F, 1.0F, 1);
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            RenderHelper.enableStandardItemLighting();
            itemRenderer.renderItem(entityitem.getItem(), ItemCameraTransforms.TransformType.FIXED);
            RenderHelper.disableStandardItemLighting();

            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        }
    }

    public static void renderFloatingOnBlockWithCoords(ItemStack renderItem, TileEntity tileEntity, double x, double y, double z) {
        RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
        if (!renderItem.isEmpty()) {
            GlStateManager.translate(x, y, z);
            EntityItem entityitem = new EntityItem(tileEntity.getWorld(), 0.0D, 0.0D, 0.0D, renderItem);
            entityitem.getItem().setCount(1);
            entityitem.hoverStart = 0.0F;
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();

            float rotation = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);

            GlStateManager.rotate(rotation, 0.0F, 1.0F, 0);
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            RenderHelper.enableStandardItemLighting();
            itemRenderer.renderItem(entityitem.getItem(), ItemCameraTransforms.TransformType.FIXED);
            RenderHelper.disableStandardItemLighting();

            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        }
    }


    public static void renderFloatingNoRotationOnBlockWithCoords(ItemStack renderItem, TileEntity tileEntity, double x, double y, double z) {
        RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
        if (!renderItem.isEmpty()) {
            GlStateManager.translate(x, y, z);
            EntityItem entityitem = new EntityItem(tileEntity.getWorld(), 0.0D, 0.0D, 0.0D, renderItem);
            entityitem.getItem().setCount(1);
            entityitem.hoverStart = 0.0F;
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();

            GlStateManager.rotate(0, 0.0F, 1.0F, 0);
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            RenderHelper.enableStandardItemLighting();
            itemRenderer.renderItem(entityitem.getItem(), ItemCameraTransforms.TransformType.FIXED);
            RenderHelper.disableStandardItemLighting();

            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        }
    }
}
