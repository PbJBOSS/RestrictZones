package com.pbjboss.restrictzones;

/**
 * Created by Nico on 2/11/2015.
 */
public
class Util
{
    public static boolean isInt(String s)
    {
        try
        {
            Integer.parseInt(s);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
