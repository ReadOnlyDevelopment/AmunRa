package de.katzenpapst.amunra.old.block;

import java.util.Random;

import de.katzenpapst.amunra.old.item.ItemDamagePair;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SubBlockLeaf extends SubBlock {

	protected IIcon				blockIconOpaque	= null;
	protected ItemDamagePair	itemDropped		= null;

	public SubBlockLeaf(String name, String texture) {
		super(name, texture);
		// TODO Auto-generated constructor stub
	}

	public SubBlockLeaf(String name, String texture, String tool, int harvestLevel) {
		super(name, texture, tool, harvestLevel);
		// TODO Auto-generated constructor stub
	}

	public SubBlockLeaf(String name, String texture, String tool, int harvestLevel, float hardness, float resistance) {
		super(name, texture, tool, harvestLevel, hardness, resistance);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Determines the damage on the item the block drops. Used in cloth and wood.
	 */
	@Override
	public int damageDropped(int p_149692_1_) {
		return itemDropped.getDamage();
	}

	@Override
	public boolean dropsSelf() {
		return false;
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return itemDropped.getItem();
	}

	public IIcon getOpaqueIcon(int side) {
		return blockIconOpaque;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconReg) {
		this.blockIcon = iconReg.registerIcon(this.getTextureName());
		this.blockIconOpaque = iconReg.registerIcon(this.getTextureName() + "_opaque");
	}

	public SubBlockLeaf setSaplingDropped(BlockMetaPair sapling) {
		itemDropped = new ItemDamagePair(Item.getItemFromBlock(sapling.getBlock()), sapling.getMetadata());
		return this;
	}

}
