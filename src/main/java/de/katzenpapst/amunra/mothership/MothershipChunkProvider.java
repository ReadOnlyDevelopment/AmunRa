package de.katzenpapst.amunra.mothership;

import java.util.Random;

import micdoodle8.mods.galacticraft.core.world.gen.ChunkProviderOrbit;
import net.minecraft.world.World;

public class MothershipChunkProvider extends ChunkProviderOrbit { // for now, just like this

    protected final Random rand;
    // ...sigh...
    protected final World worldObjNonPrivate;

    public MothershipChunkProvider(World par1World, long par2, boolean par4) {
        super(par1World, par2, par4);
        this.rand = new Random(par2);
        worldObjNonPrivate = par1World;
    }

}
