package com.pbjboss.restrictzones;

import cpw.mods.fml.common.FMLLog;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

import java.io.File;

/**
 * Created by Nico on 2/11/2015.
 */
public
class ConfigurationHandler
{

    public static Configuration config;
    public static boolean opOverridesZones;
    //public static boolean zonesUseMaxHeight;
    public static int zoneDefaultRadius;
    public static boolean sendPlayerMessage;

    public static void load(File file)
    {
        if (config == null)
        {
            config = new Configuration(file);
        }
        
        FMLLog.log(Level.INFO, "Loading RestrictZones config");
        try
        {
            config.load();
            opOverridesZones = config.getBoolean("opOverridesZones",Configuration.CATEGORY_GENERAL,true,"Does having op override zones");
            //zonesUseMaxHeight = config.getBoolean("zonesUseMaxHeight", Configuration.CATEGORY_GENERAL, true, "Is the default height of a zone the max world height");
            zoneDefaultRadius = config.getInt("zoneDefaultRadius", Configuration.CATEGORY_GENERAL, 20, 0, 500, "Default radius for a zone if the command is run without arguments");
            sendPlayerMessage = config.getBoolean("sendPlayerMessage", Configuration.CATEGORY_GENERAL, false, "Sends the player a message if they can't break a block");
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
