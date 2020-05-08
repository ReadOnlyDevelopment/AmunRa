package de.katzenpapst.amunra.mothership;

import micdoodle8.mods.galacticraft.api.world.BiomeGenBaseGC;

public class MothershipWorldChunkManager extends BiomeGenBaseGC
{
    @Override
    public BiomeGenBase getBiome()
    {
        return BiomeGenBaseOrbit.space;
    }
}
