package com.ieya.supervisors.items;

import com.ieya.supervisors.AlexsSupervisors;
import com.mojang.logging.LogUtils;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.slf4j.Logger;

import java.util.Objects;

import static com.ieya.supervisors.AlexsSupervisors.WEARING_CAP;

public class SupervisorsCapItem extends Item {
    public static final Logger LOGGER = LogUtils.getLogger();

    public SupervisorsCapItem(Properties properties) {
        super(properties.stacksTo(16));

        NeoForge.EVENT_BUS.addListener(SupervisorsCapItem::onRightClick);
    }

    private static void onRightClick(PlayerInteractEvent.EntityInteract event){
        LOGGER.info(event.getItemStack().getDescriptionId());
        if(!event.getLevel().isClientSide()) {
            Entity target = event.getTarget();
            Player player = event.getEntity();
            ItemStack item = event.getItemStack();

            if (
                    target instanceof TamableAnimal animal
                    && animal.isTame()
                    && player.getUUID().equals(animal.getOwnerUUID())
            ) {
                if(
                        item.is(AlexsSupervisors.SUPERVISORS_CAP)
                        && !animal.getData(WEARING_CAP)
                ) {
                    event.getItemStack().shrink(1);
                    animal.setData(WEARING_CAP, true);
                    event.setCanceled(true);
                } else if (
                        item.is(Items.SHEARS)
                        && animal.getData(WEARING_CAP)
                ) {
                    animal.setData(WEARING_CAP, false);
                    Vec3 newItemPos = target.position();
                    ItemEntity entity = new ItemEntity(event.getLevel(), newItemPos.x, newItemPos.y, newItemPos.z, new ItemStack(AlexsSupervisors.SUPERVISORS_CAP.get()));
                    event.getLevel().addFreshEntity(entity);
                    event.setCanceled(true);
                }
            }
        }
    }
}
