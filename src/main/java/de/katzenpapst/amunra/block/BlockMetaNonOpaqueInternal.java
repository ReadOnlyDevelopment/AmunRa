package de.katzenpapst.amunra.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMetaNonOpaqueInternal extends BlockBasicMeta {

    public BlockMetaNonOpaqueInternal(String name, Material mat, int numSubBlocks) {
        super(name, mat, numSubBlocks);
    }

    public BlockMetaNonOpaqueInternal(String name, Material mat) {
        super(name, mat);
    }


    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean isBlockNormalCube()
    {
        return false;
    }

    @Override
    public boolean isNormalCube()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return null;
    }
}
