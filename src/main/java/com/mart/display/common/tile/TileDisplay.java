package com.mart.display.common.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileDisplay extends TileBase implements ITickable, ICapabilityProvider {

    private boolean rotation;

    private int rotationDegrees = 0;
    private float xAxisCoord = 0.5f;
    private float yAxisCoord = 0.75f;
    private float zAxisCoord = 0.5f;

    private TileDisplayItemHandler itemStackHandler;

    public TileDisplay(){
        this.rotation = true;
        this.itemStackHandler = new TileDisplayItemHandler(this);
    }

    @Override
    public void update() {

    }

    public void extractItem(EntityPlayer player) {
        if (this.itemStackHandler.getStackInSlot(0).isEmpty()) {
            return;
        }

        player.inventory.addItemStackToInventory(this.itemStackHandler.extractItem(0, 64, false));

        notifyUpdate();
    }

    public void setItem(ItemStack heldItem, EntityPlayer player, EnumHand hand) {
        if (!this.itemStackHandler.getStackInSlot(0).isEmpty()) {
            return;
        }

        ItemStack heldItem2 = heldItem.copy();

        heldItem2.setCount(1);
        this.itemStackHandler.setStackInSlot(0, heldItem2);

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

        compound.setTag("itemStackHandler", this.itemStackHandler.serializeNBT());

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

        this.itemStackHandler.deserializeNBT(compound.getCompoundTag("itemStackHandler"));
    }

    public ItemStack getItemStack() {
        return this.itemStackHandler.getStackInSlot(0);
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

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) this.itemStackHandler;
        }
        return super.getCapability(capability, facing);
    }

    public class TileDisplayItemHandler extends ItemStackHandler{

       private final TileDisplay entity;

       public TileDisplayItemHandler(TileDisplay entity){
           super(1);
           this.entity = entity;
       }

        @Override
        protected void onContentsChanged(int slot) {
            entity.notifyUpdate();
        }
    }


}
