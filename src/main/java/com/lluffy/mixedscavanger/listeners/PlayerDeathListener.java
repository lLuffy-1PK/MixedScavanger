package com.lluffy.mixedscavanger.listeners;

import com.lluffy.mixedscavanger.MixedScavenger;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.GameRules;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.UUID;

@Mod.EventBusSubscriber
public class PlayerDeathListener {

    private static final HashMap<String, ItemStack[]> playerKeepsMap = new HashMap();

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (!(event.getEntityLiving() instanceof PlayerEntity) && (!event.getEntityLiving().world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY))) {
            return;
        }

        PlayerEntity player = (PlayerEntity) event.getEntityLiving();

        //Юзал бы PermissionAPI форджа, но ебанный CatServer с ним не работает
        if (MixedScavenger.hasPermission(player, "mixedscavenger.scavenger")) {
            ItemStack[] VanillaStacks = new ItemStack[player.inventory.getSizeInventory()];
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {

                VanillaStacks[i] = player.inventory.getStackInSlot(i);
                player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
            }
            playerKeepsMap.put(player.getName().getUnformattedComponentText(), VanillaStacks);
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        PlayerEntity player = event.getPlayer();

        if (playerKeepsMap.containsKey(player.getName().getUnformattedComponentText())) {
            ItemStack[] VanillaList = playerKeepsMap.get(player.getName().getUnformattedComponentText());

            for (int i = 0; i < VanillaList.length; i++) {
                player.inventory.setInventorySlotContents(i, VanillaList[i]);
            }
            playerKeepsMap.remove(player.getName().getUnformattedComponentText());
            event.getPlayer().sendMessage(new StringTextComponent("§8[§cMixedScavenger§8] §aВаш инвентарь был восстановлен!"), UUID.randomUUID());
        }
    }

}
