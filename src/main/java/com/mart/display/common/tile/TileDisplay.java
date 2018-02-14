package com.mart.display.common.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;

public class TileDisplay extends TileBase implements ITickable {

    private ItemStack itemStack = ItemStack.EMPTY;

    private boolean rotation;

    private int rotationDegrees = 0;
    private float xAxisCoord = 0.5f;
    private float yAxisCoord = 0.75f;
    private float zAxisCoord = 0.5f;

    public TileDisplay(){
        this.rotation = true;
    }

    @Override
    public void update() {
        notifyUpdate();
    }

    public void extractItem(EntityPlayer player) {
        if (itemStack.isEmpty()) {
            return;
        }

        player.inventory.addItemStackToInventory(itemStack);
        this.itemStack = ItemStack.EMPTY;

        notifyUpdate();
    }

    public void setItem(ItemStack heldItem, EntityPlayer player, EnumHand hand) {
        if (!itemStack.isEmpty()) {
            return;
        }

        ItemStack heldItem2 = heldItem.copy();

        heldItem2.setCount(1);
        this.itemStack = heldItem2;

        heldItem.setCount(heldItem.getCount() - 1);
        player.setHeldItem(hand, heldItem);

        notifyUpdate();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setBoolean("rotation", this.rotation);
        compound.setInteger("rotationDegrees", this.rotationDegrees);
        compound.setFloat("xAxisCoord", this.xAxisCoord);
        compound.setFloat("yAxisCoord", this.yAxisCoord);
        compound.setFloat("zAxisCoord", this.zAxisCoord);


        NBTTagList tagList = new NBTTagList();
        NBTTagCompound itemCompound = new NBTTagCompound();
        this.itemStack.writeToNBT(itemCompound);
        tagList.appendTag(itemCompound);
        compound.setTag("item", tagList);

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.rotation = compound.getBoolean("rotation");
        this.rotationDegrees = compound.getInteger("rotationDegrees");

        this.xAxisCoord = compound.getFloat("xAxisCoord");
        this.yAxisCoord = compound.getFloat("yAxisCoord");
        this.zAxisCoord = compound.getFloat("zAxisCoord");

        NBTTagList tagList = (NBTTagList) compound.getTag("item");
        NBTTagCompound tagCompound = tagList.getCompoundTagAt(0);
        this.itemStack = new ItemStack(tagCompound);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void toggleRotation(EnumFacing facing) {
        rotation = !rotation;

        switch (facing){
            case EAST:
                this.rotationDegrees = 90;
                break;
            case WEST:
                this.rotationDegrees = 90;
                break;
            default:
                this.rotationDegrees = 0;
                break;
        }
    }

    public boolean isRotation() {
        return rotation;
     }

    public int getRotationDegrees() {
        return rotationDegrees;
    }

    public void moveItemXAxis() {
        if(this.xAxisCoord >= 2.5f){
            this.xAxisCoord = -1.5f;
        }
        else{
            this.xAxisCoord += 0.1f;
        }
    }

    public void moveItemYAxis() {
        if(this.yAxisCoord >= 3){
            this.yAxisCoord = 0.75f;
        }
        else{
            this.yAxisCoord += 0.1f;
        }
    }

    public void moveItemZAxis() {
        if(this.zAxisCoord >= 2.5f){
            this.zAxisCoord = -1.5f;
        }
        else{
            this.zAxisCoord += 0.1f;
        }
    }

    public float getxAxisCoord() {
        return xAxisCoord;
    }

    public float getyAxisCoord() {
        return yAxisCoord;
    }

    public float getzAxisCoord() {
        return zAxisCoord;
    }
}
