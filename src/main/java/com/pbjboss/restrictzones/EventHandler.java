package com.pbjboss.restrictzones;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import org.apache.logging.log4j.Level;

public
class EventHandler
{
    @SubscribeEvent
    public void blockBreakEvent(BlockEvent.BreakEvent event)
    {
        //ZoneHandler.loadZones();


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

    @SubscribeEvent
    public void worldLoadEvent(WorldEvent.Load event)
    {
        ZoneHandler.loadZones();

        if (ConfigurationHandler.generateZoneAroundSpawn)
        {
            if (!event.world.isRemote)
            {
                if (event.world.provider.dimensionId == 0)
                {
                    for (int i = 0; i < ZoneHandler.zones.size(); i++)
                    {
                        Zone zone = ZoneHandler.zones.get(i);
                        if (zone.worldId == 0 && zone.tag.equals("spawn"))
                        {
                            ZoneHandler.zones.remove(zone);
                        }
                    }
                    ChunkCoordinates spawn = event.world.getSpawnPoint();
                    ZoneHandler.addZone(event.world.provider.dimensionId, spawn.posX - ConfigurationHandler.spawnZoneXRadius, 0, spawn.posZ - ConfigurationHandler.spawnZoneZRadius, spawn.posX + ConfigurationHandler.spawnZoneXRadius, event.world.getActualHeight(), spawn.posZ + ConfigurationHandler.spawnZoneZRadius, "spawn");
                    FMLLog.log(Level.INFO, "Generating spawn zone around coordinates: " + event.world.getSpawnPoint().posX + " " + event.world.getSpawnPoint().posY + " " + event.world.getSpawnPoint().posZ);
                }
            }
        }
    }
}
