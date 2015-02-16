package com.pbjboss.restrictzones;

import cpw.mods.fml.common.FMLLog;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

import java.io.File;

public
class ConfigurationHandler
{

    public static Configuration config;
    public static boolean opOverridesZones;
    public static boolean creativeOverridesZones;
    //public static boolean zonesUseMaxHeight;
    public static int zoneDefaultRadius;
    public static boolean sendPlayerMessage;
    public static boolean generateZoneAroundSpawn;
    public static int spawnZoneXRadius;
    public static int spawnZoneZRadius;

    public static void load()
    {
        File file = new File(RestrictZones.configDirectory.getPath() + "/restrictzones.cfg");
        config = new Configuration(file);
        try
        {
            config.load();
            opOverridesZones = config.getBoolean("opOverridesZones",Configuration.CATEGORY_GENERAL,true,"Does having op override zones");
            //zonesUseMaxHeight = config.getBoolean("zonesUseMaxHeight", Configuration.CATEGORY_GENERAL, true, "Is the default height of a zone the max world height");
            zoneDefaultRadius = config.getInt("zoneDefaultRadius", Configuration.CATEGORY_GENERAL, 20, 0, 1000, "Default radius for a zone if the command is run without arguments");
            sendPlayerMessage = config.getBoolean("sendPlayerMessage", Configuration.CATEGORY_GENERAL, false, "Sends the player a message if they can't break a block");
            generateZoneAroundSpawn = config.getBoolean("generateSpawnZone", Configuration.CATEGORY_GENERAL, false, "Generate a zone around any newly created worlds");
            spawnZoneXRadius = config.getInt("spawnZoneDefaultXRadius", Configuration.CATEGORY_GENERAL, 20, 0 , 1000, "Default x radius for a spawn zone");
            spawnZoneZRadius = config.getInt("spawnZoneDefaultZRadius", Configuration.CATEGORY_GENERAL, 20, 0 , 1000, "Default z radius for a spawn zone");
            creativeOverridesZones = config.getBoolean("creativeOverridesZones", Configuration.CATEGORY_GENERAL, false, "Does having creative override zones");
        }
        catch (Exception e)
        {
            FMLLog.log(Level.ERROR , "Error initializing configuration!");
        }
        finally
        {
            if (config.hasChanged())
                config.save();
        }
    }
}
