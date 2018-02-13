package com.mart.display.common.block;

import com.mart.display.common.tile.TileDisplay;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
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

public class BlockDisplay extends BlockBase implements ITileEntityProvider{

    private final AxisAlignedBB displayAabb = new AxisAlignedBB(0.2D, 0.0D, 0.2D, 0.8D, 0.45D, 0.8D);

    public BlockDisplay(String registryName) {
        super(Material.ROCK, registryName);
        setCreativeTab(CreativeTabs.DECORATIONS);

        setHardness(3.0f);
        setSoundType(SoundType.STONE);
        setLightOpacity(0);
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileDisplay tileEntity = (TileDisplay) world.getTileEntity(pos);

        if (world.isRemote || tileEntity == null) {
            return;
        }

        if(tileEntity.getItemStack().isEmpty()){
            return;
        }

        double d0 = (double)(world.rand.nextFloat() * 0.5F) + 0.25D;
        double d1 = (double)(world.rand.nextFloat() * 0.5F) + 0.25D;
        double d2 = (double)(world.rand.nextFloat() * 0.5F) + 0.25D;
        EntityItem dropItem = new EntityItem(world, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, tileEntity.getItemStack());
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

        if (player.isSneaking()){
            tileEntity.toggleRotation();
            return true;
        }

        if (!heldItem.isEmpty()) {
            tileEntity.setItem(heldItem, player, hand);
            return true;
        }else{
            player.swingArm(hand);
            tileEntity.extractItem(player);
        }

        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileDisplay();
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return displayAabb;
    }
}
