package com.mart.display.common.registry;

import com.mart.display.common.item.ItemBase;
import com.mart.display.common.item.ItemDisplayConfigurator;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

    public static ItemDisplayConfigurator displayConfigurator;

    public static void init(RegistryEvent.Register<Item> event) {
        displayConfigurator = register(new ItemDisplayConfigurator("display_configurator"), event);
//        registerModels();
    }

    private static <T extends Item> T register(T item, RegistryEvent.Register<Item> event) {
        event.getRegistry().register(item);

        if (item instanceof ItemBase) {
            ((ItemBase) item).registerItemModel();
        }

        return item;
    }

    @SideOnly(Side.CLIENT)
    private static void registerModels() {
//        for (RuneType type : RuneType.values())
 //           ModelLoader.setCustomModelResourceLocation(RUNES, type.ordinal(), new ModelResourceLocation(RUNES.getRegistryName(), "type=" + type.name().toLowerCase(Locale.ENGLISH)));
    }
}
