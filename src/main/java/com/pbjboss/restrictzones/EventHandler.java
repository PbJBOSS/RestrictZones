package com.pbjboss.restrictzones;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.world.BlockEvent;

/**
 * Created by Nico on 2/11/2015.
 */
public
class EventHandler
{
    @SubscribeEvent
    public void blockBreakEvent(BlockEvent.BreakEvent event)
    {
        if (ConfigurationHandler.opOverridesZones && event.getPlayer().canCommandSenderUseCommand(2,""))
            return;

        for (int i = 0; i < ZoneHandler.zones.size(); i++)
        {
            if (ZoneHandler.zones.get(i).worldId == event.getPlayer().worldObj.provider.dimensionId && ZoneHandler.doesZoneContainBlock(event.x,event.y,event.z, ZoneHandler.zones.get(i)))
            {
                if (ConfigurationHandler.sendPlayerMessage)
                {
                    event.getPlayer().addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "You cannot break blocks here!"));
                }
                event.setCanceled(true);
                return;
            }
        }
    }
}
