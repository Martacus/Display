package com.mart.display.common.block;

import com.mart.display.common.DisplaySide;
import com.mart.display.common.item.ItemDisplayConfigurator;
import com.mart.display.common.registry.ModItems;
import com.mart.display.common.tile.TileDisplay;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockDisplay extends BlockBase {

    private final AxisAlignedBB displayAabb = new AxisAlignedBB(0.2D, 0.0D, 0.2D, 0.8D, 0.45D, 0.8D);

    public BlockDisplay(String registryName) {
        super(Material.ROCK, registryName);
        setCreativeTab(CreativeTabs.DECORATIONS);

        setHardness(3.0f);
        setSoundType(SoundType.STONE);
        setLightOpacity(0);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileDisplay tileEntity = (TileDisplay) world.getTileEntity(pos);

        if (world.isRemote || tileEntity == null) {
            return;
        }

        if (tileEntity.getItemStack().isEmpty()) {
            return;
        }

        double d0 = (double) (world.rand.nextFloat() * 0.5F) + 0.25D;
        double d1 = (double) (world.rand.nextFloat() * 0.5F) + 0.25D;
        double d2 = (double) (world.rand.nextFloat() * 0.5F) + 0.25D;
        EntityItem dropItem = new EntityItem(world, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, tileEntity.getItemStack());
        dropItem.setDefaultPickupDelay();
        world.spawnEntity(dropItem);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileDisplay tileEntity = (TileDisplay) world.getTileEntity(pos);
        ItemStack heldItem = player.getHeldItem(hand);

        if (world.isRemote || tileEntity == null) {
            return true;
        }

        if (player.isSneaking()) {
            tileEntity.toggleRotation(facing);
            return true;
        }

        if (!heldItem.isEmpty()) {

            if (heldItem.getItem() == ModItems.displayConfigurator) {
                DisplaySide side = ItemDisplayConfigurator.getDisplaySide(heldItem);

                if (side == DisplaySide.X) {
                    tileEntity.moveItemXAxis();
                } else if (side == DisplaySide.Y) {
                    tileEntity.moveItemYAxis();
                } else if (side == DisplaySide.Z) {
                    tileEntity.moveItemZAxis();
                }
                return true;
            }

            tileEntity.setItem(heldItem, player, hand);
            return true;
        } else {
            player.swingArm(hand);
            tileEntity.extractItem(player);
        }

        return true;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileDisplay();
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return displayAabb;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }
}
