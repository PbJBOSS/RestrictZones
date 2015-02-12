package com.pbjboss.restrictzones;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.entity.player.EntityPlayer;
import org.apache.logging.log4j.Level;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Nico on 2/11/2015.
 */
public
class ZoneHandler
{
    public static
    ArrayList<Zone> zones;
    static File file;

    public static void loadZones(File location)
    {
        File configLocation = new File(location.getPath() + "/restrictzones");
        file = new File(configLocation.getPath() + "/restrictzones.zones");
        if (!configLocation.exists())
        {
            configLocation.mkdirs();
        }

        if (!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch (IOException e)
            {
                FMLLog.log(Level.ERROR, "Error Generating Config!");
            }
        }

        try
        {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            zones = (ArrayList<Zone>) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();

            FMLLog.log(Level.INFO, String.format("%s zones loaded", zones.size()));
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        if (zones == null)
        {
            zones = new ArrayList<Zone>();
        }

    }

    public static void saveZones()
    {
        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(zones);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static boolean doesZoneContainBlock(int x, int y, int z, Zone zone)
    {
        if (x >= zone.minX && y >= zone.minY && z >= zone.minZ && x <= zone.maxX && y <= zone.maxY && z <= zone.maxZ)
        {
            return true;
        }

        return false;
    }

    public static boolean isPlayerInZone(EntityPlayer player, Zone zone)
    {
        double x = player.posX;
        double y = player.posY;
        double z = player.posZ;
        if (x >= zone.minX && y >= zone.minY && z >= zone.minZ && x <= zone.maxX && y <= zone.maxY && z <= zone.maxZ)
        {
            return true;
        }

        return false;
    }

    public static void addZone(int worldId, int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
    {
        Zone zone = new Zone(worldId, minX, minY, minZ, maxX, maxY, maxZ);
        ZoneHandler.zones.add(zone);
        ZoneHandler.saveZones();
    }
}
