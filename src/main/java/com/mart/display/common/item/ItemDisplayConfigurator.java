package com.mart.display.common.item;

import com.mart.display.common.DisplaySide;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemDisplayConfigurator extends ItemBase {

    public ItemDisplayConfigurator(String registryName) {
        super(registryName);
        setCreativeTab(CreativeTabs.TOOLS);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (worldIn.isRemote) {
            return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItemMainhand());
        }

        ItemStack stack = playerIn.getHeldItem(handIn);

        if (stack.isEmpty()) {
            return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItemMainhand());
        }

        if (playerIn.isSneaking()) {
            DisplaySide displaySide = getDisplaySide(stack);
            if (displaySide == DisplaySide.X) {
                setDisplaySide(stack, DisplaySide.Y);
                playerIn.sendMessage(new TextComponentString("Configuration: Y"));
            } else if(displaySide == DisplaySide.Y){
                setDisplaySide(stack, DisplaySide.Z);
                playerIn.sendMessage(new TextComponentString("Configuration: Z"));
            }
            else if(displaySide == DisplaySide.Z){
                setDisplaySide(stack, DisplaySide.X);
                playerIn.sendMessage(new TextComponentString("Configuration: X"));
            }
            return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItemMainhand());
        }



        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    private void setDisplaySide(ItemStack stack, DisplaySide displaySide) {
        NBTTagCompound tag = getCompound(stack);
        tag.setString("displaySide", displaySide.toString());
    }

    public static DisplaySide getDisplaySide(ItemStack stack) {
        NBTTagCompound tag = getCompound(stack);
        if(tag.getString("displaySide").equalsIgnoreCase("")){
            return DisplaySide.X;
        }
        return DisplaySide.valueOf(tag.getString("displaySide"));
    }


}
