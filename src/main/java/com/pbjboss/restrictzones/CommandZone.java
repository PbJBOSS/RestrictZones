package com.pbjboss.restrictzones;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public
class CommandZone implements ICommand
{
    @Override
    public
    String getCommandName()
    {
        return "zone";
    }

    @Override
    public
    String getCommandUsage(ICommandSender p_71518_1_)
    {
        return EnumChatFormatting.GREEN + "/zone <add|remove|list> <radius|xRadius, zRadius>";
    }

    @Override
    public
    List getCommandAliases()
    {
        return null;
    }

    @Override
    public
    void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
    {
        if (!p_71515_1_.getEntityWorld().isRemote)
        {
            if (p_71515_2_.length == 0)
            {
                p_71515_1_.addChatMessage(new ChatComponentText(getCommandUsage(p_71515_1_)));
                return;
            }

            EntityPlayer player = (EntityPlayer) p_71515_1_;
            if (p_71515_2_[0].equalsIgnoreCase("add"))
            {
                int worldId, minX, minY, minZ, maxX, maxY, maxZ;

                worldId = player.worldObj.provider.dimensionId;
                minY = 0;
                maxY = player.worldObj.getActualHeight();
                minX = (int) player.posX - ConfigurationHandler.zoneDefaultRadius;
                minZ = (int) player.posZ - ConfigurationHandler.zoneDefaultRadius;
                maxX = (int) player.posX + ConfigurationHandler.zoneDefaultRadius;
                maxZ = (int) player.posZ + ConfigurationHandler.zoneDefaultRadius;

                if (p_71515_2_.length > 3)
                {
                    player.addChatComponentMessage(new ChatComponentText("Invalid number of args!"));
                    player.addChatComponentMessage(new ChatComponentText(getCommandUsage(p_71515_1_)));
                    return;
                }
                else if (p_71515_2_.length == 2)
                {
                    if (Util.isInt(p_71515_2_[1]))
                    {
                        int radius = Integer.parseInt(p_71515_2_[1]);
                        minX = (int) player.posX - radius;
                        minZ = (int) player.posZ - radius;
                        maxX = (int) player.posX + radius;
                        maxZ = (int) player.posZ + radius;
                    }
                    else
                    {
                        player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.YELLOW + p_71515_2_[1] + " is not a number!"));
                    }
                }
                else if (p_71515_2_.length == 3)
                {
                    if (Util.isInt(p_71515_2_[1]) && Util.isInt(p_71515_2_[2]))
                    {
                        int xRadius = Integer.parseInt(p_71515_2_[1]);
                        int zRadius = Integer.parseInt(p_71515_2_[2]);

                        minX = (int) player.posX - xRadius;
                        minZ = (int) player.posZ - xRadius;
                        maxX = (int) player.posX + xRadius;
                        maxZ = (int) player.posZ + xRadius;
                    }
                    else
                    {
                        player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.YELLOW + p_71515_2_[1] + " or "+ p_71515_2_[2] + " is not a number!"));
                    }
                }
                ZoneHandler.addZone(worldId, minX, minY, minZ, maxX, maxY, maxZ);
            }
            else if (p_71515_2_[0].equalsIgnoreCase("remove"))
            {
                int removed = 0;
                for (int i = 0; i < ZoneHandler.zones.size(); i++)
                {
                    if (ZoneHandler.isPlayerInZone(player, ZoneHandler.zones.get(i)))
                    {
                        ZoneHandler.zones.remove(i);
                        removed++;
                    }
                }
                player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GREEN + "" + removed + " zones removed!"));
            }
            else if (p_71515_2_[0].equalsIgnoreCase("list"))
            {
                for (int  i = 0; i < ZoneHandler.zones.size(); i++)
                {
                    Zone zone = ZoneHandler.zones.get(i);
                    if (ZoneHandler.zones.isEmpty())
                    {
                        player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GREEN + "No zones exist in this world"));
                    }
                    else
                    {
                        player.addChatComponentMessage(new ChatComponentText(String.format("%sDimensionId: %s, minX: %s, minY: %s, minZ: %s, maxX: %s, maxY: %s%s, maxZ: %s",EnumChatFormatting.GOLD, zone.worldId, zone.minX, zone.minY, zone.minZ, zone.maxX, zone.maxY,EnumChatFormatting.GOLD, zone.maxZ)));
                    }
                }
            }
            else
            {
                player.addChatComponentMessage(new ChatComponentText(getCommandUsage(p_71515_1_)));
            }
        }
    }

    @Override
    public
    boolean canCommandSenderUseCommand(ICommandSender p_71519_1_)
    {
        return MinecraftServer.getServer().canCommandSenderUseCommand(3, "");
    }

    @Override
    public
    List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
    {
        return null;
    }

    @Override
    public
    boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_)
    {
        return false;
    }

    @Override
    public
    int compareTo(Object o)
    {
        return 0;
    }
}
