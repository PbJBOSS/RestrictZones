package com.pbjboss.restrictzones;

import java.io.Serializable;

/**
 * Created by Nico on 2/11/2015.
 */
public
class Zone implements Serializable
{
    public int worldId,minX,minY,minZ,maxX,maxY,maxZ;

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
}
