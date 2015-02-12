package com.pbjboss.restrictzones;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Level;

import java.io.File;

@Mod(modid = RestrictZones.modid, name = RestrictZones.name, version = RestrictZones.version, acceptableRemoteVersions = "*")
public
class RestrictZones
{
    public static File configDirectory;
    public static final String modid = "restrictzones";
    public static final String name = "RestrictZones";
    public static final String version = "1.7.10-1.0";

    @Mod.EventHandler
    public void serverStartingEvent(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandZone());
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        configDirectory = new File(event.getModConfigurationDirectory()+"/restrictzones");

        if (!configDirectory.exists())
        {
            configDirectory.mkdirs();
        }

        MinecraftForge.EVENT_BUS.register(new EventHandler());
        ConfigurationHandler.load();
        FMLLog.log(Level.INFO, "RestrictZones loaded!");
    }
}
