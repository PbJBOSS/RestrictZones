package com.pbjboss.restrictzones;

import java.io.Serializable;

public
class Zone implements Serializable
{
    public int worldId,minX,minY,minZ,maxX,maxY,maxZ;
    public String tag = "";

    public Zone(int worldId, int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
    {
        this.worldId = worldId;
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public Zone(int worldId, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, String tag)
    {
        this.worldId = worldId;
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
        this.tag = tag;
    }

    public void setTag(String s)
    {
        this.tag = s.toLowerCase();
    }

    public String zoneInfo()
    {
        return (String.format("%s, %s, %s, %s, %s, %s, %s, %s",worldId,minX, minY, minZ, maxX, maxY, maxZ, tag));
    }
}
