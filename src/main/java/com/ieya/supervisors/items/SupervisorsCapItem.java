package com.ieya.supervisors.items;

import com.ieya.supervisors.AlexsSupervisors;
import com.mojang.logging.LogUtils;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.slf4j.Logger;

import java.util.Objects;

public class SupervisorsCapItem extends Item {
    public static final Logger LOGGER = LogUtils.getLogger();

    public SupervisorsCapItem(Properties properties) {
        super(properties.stacksTo(16));

        NeoForge.EVENT_BUS.addListener(SupervisorsCapItem::onRightClick);
    }

    private static void onRightClick(PlayerInteractEvent.EntityInteract event){
        LOGGER.info(event.getItemStack().getDescriptionId());
        if(!event.getLevel().isClientSide()) {
            // Player's name
            LOGGER.info(event.getEntity().getName().getString());
            // Mob name
            LOGGER.info(event.getTarget().getName().getString());
            // Mob name tagged name
            if(event.getEntity().getCustomName() != null)
                LOGGER.info(event.getEntity().toString());
        }
    }

}
