package com.ieya.supervisors;

import com.ieya.supervisors.items.SupervisorsCapItem;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(AlexsSupervisors.MODID)
public class AlexsSupervisors {
    public static final String MODID = "alexssupervisors";
    public static final Logger LOGGER = LogUtils.getLogger();

    // Register Item
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredItem<SupervisorsCapItem> SUPERVISORS_CAP = ITEMS.registerItem(
            "supervisors_cap",
            SupervisorsCapItem::new,
            new Item.Properties()
    );

    // Register Creative Mode Tab
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("alexssupervisors", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.creativeTabName")) //The language key for the title of your CreativeModeTab
            .icon(() -> SUPERVISORS_CAP.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(SUPERVISORS_CAP.get()); // Add item to the tab. For your own tabs, this method is preferred over the event
            }).build());

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public AlexsSupervisors(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Registers to the mod event bus
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("Max number of Supervisors: {}", Config.MAX_SUPERVISORS.getAsInt());
    }
}
