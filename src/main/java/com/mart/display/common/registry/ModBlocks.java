package com.mart.display.common.registry;

import com.mart.display.common.block.BlockBase;
import com.mart.display.common.block.BlockDisplay;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {

    public static List<String> displays;
    public static List<BlockDisplay> displayBlocks;

    public static void init(RegistryEvent.Register<Block> event) {
        displays = new ArrayList<>();
        displayBlocks = new ArrayList<>();

        displays.add("display");
        displays.add("wood_display");
        displays.add("dirt_display");
        displays.add("sand_display");
        displays.add("gravel_display");
        displays.add("iron_display");
        displays.add("gold_display");
        displays.add("diamond_display");
        displays.add("obsidian_display");
        displays.add("quartz_display");

        for(String registryName : displays){
            displayBlocks.add(register(new BlockDisplay(registryName), event));
        }
    }

    public static void initItemBlocks(RegistryEvent.Register<Item> event) {
        for(BlockDisplay display : displayBlocks){
            registerItemBlock(display, event);
        }
    }

    private static <T extends Block> T register(T block, RegistryEvent.Register<Block> event) {
        event.getRegistry().register(block);

        return block;
    }

    private static void registerItemBlock(Block block, RegistryEvent.Register<Item> event) {
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());

        if (block instanceof BlockBase) {
            ((BlockBase) block).registerItemModel(itemBlock);
        }

        event.getRegistry().register(itemBlock);
    }

}