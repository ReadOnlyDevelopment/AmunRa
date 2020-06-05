package de.katzenpapst.amunra.old.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.katzenpapst.amunra.AmunRa;
import micdoodle8.mods.galacticraft.core.items.ItemBlockDesc;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBasicMulti extends Item implements ItemBlockDesc.IBlockShiftDesc {
	// public static final String[] names = { "solar_module_0", "solar_module_1", "rawSilicon", "ingotCopper", "ingotTin", "ingotAluminum", "compressedCopper", "compressedTin", "compressedAluminum", "compressedSteel", "compressedBronze", "compressedIron", "waferSolar", "waferBasic", "waferAdvanced", "dehydratedApple", "dehydratedCarrot", "dehydratedMelon", "dehydratedPotato", "frequencyModule" };

	// protected IIcon[] icons = new IIcon[ItemBasic.names.length];
	protected ArrayList<SubItem> subItems = null;

	protected HashMap<String, Integer> nameDamageMapping = null;

	public ItemBasicMulti(String name) {
		super();
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setUnlocalizedName(name);
		subItems = new ArrayList<SubItem>();
		nameDamageMapping = new HashMap<String, Integer>();
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		SubItem item = getSubItem(par1ItemStack.getItemDamage());

		item.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);

		String info = item.getItemInfo();
		if (info != null) {
			info = GCCoreUtil.translate(info);
			par3List.addAll(FMLClientHandler.instance().getClient().fontRenderer.listFormattedStringToWidth(info, 150));
		}
	}

	public ItemDamagePair addSubItem(int damage, SubItem item) {
		if (damage >= subItems.size()) {
			subItems.ensureCapacity(damage);
			while (damage >= subItems.size()) {
				subItems.add(null);
			}
		}
		if (subItems.get(damage) != null)
			throw new IllegalArgumentException("SubItem with damage " + damage + " already exists in " + this.getUnlocalizedName());
		String itemName = item.getUnlocalizedName();
		if (nameDamageMapping.get(itemName) != null)
			throw new IllegalArgumentException("SubItem with name " + itemName + " already exists in " + this.getUnlocalizedName());
		nameDamageMapping.put(itemName, damage);
		subItems.add(damage, item);
		return new ItemDamagePair(this, damage);
	}

	@Override
	public CreativeTabs getCreativeTab() {
		return AmunRa.arTab;
	}

	public int getDamageByName(String name) {
		if (!nameDamageMapping.containsKey(name))
			return -1;
		return nameDamageMapping.get(name).intValue();
	}

	public int getFuelDuration(int meta) {
		return getSubItem(meta).getFuelDuration();
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return subItems.get(stack.getItemDamage()).getIcon(stack, pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage) {
		return subItems.get(damage).getIconFromDamage(0);
	}

	/**
	 * Returns the icon index of the stack given as argument.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack stack) {
		return subItems.get(stack.getItemDamage()).getIconIndex(stack);
	}

	public ItemStack getItemStack(int damage, int count) {
		// ensure it exists
		if (subItems.get(damage) == null)
			throw new IllegalArgumentException("SubItem with damage " + damage + " does not exist in " + this.getUnlocalizedName());

		return new ItemStack(this, count, damage);
	}

	public ItemStack getItemStack(String name, int count) {

		return getItemStack(getDamageByName(name), count);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return getSubItem(par1ItemStack.getItemDamage()).getItemUseAction(par1ItemStack);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return getSubItem(par1ItemStack.getItemDamage()).getMaxItemUseDuration(par1ItemStack);
	}

	@Override
	public int getMetadata(int par1) {
		return par1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return ClientProxyCore.galacticraftItem;
	}

	@Override
	public String getShiftDescription(int meta) {
		// TODO Auto-generated method stub
		return null;
	}

	public SubItem getSubItem(int damage) {
		if (damage >= subItems.size() || subItems.get(damage) == null)
			throw new IllegalArgumentException("Requested invalid SubItem " + damage + " from " + this.getUnlocalizedName());
		return subItems.get(damage);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int i = 0; i < subItems.size(); i++) {
			if (subItems.get(i) == null) {
				continue;
			}
			par3List.add(new ItemStack(par1, 1, i));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return this.getUnlocalizedName() + "." + getSubItem(itemStack.getItemDamage()).getUnlocalizedName();
	}

	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		return getSubItem(par1ItemStack.getItemDamage()).onEaten(par1ItemStack, par2World, par3EntityPlayer);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		return getSubItem(par1ItemStack.getItemDamage()).onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack itemStack, EntityPlayer player, Entity entity) {
		return getSubItem(itemStack.getItemDamage()).onLeftClickEntity(itemStack, player, entity);
	}

	public void register() {
		GameRegistry.registerItem(this, this.getUnlocalizedName(), AmunRa.MODID);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		for (SubItem item : subItems) {
			if (item == null) {
				continue;
			}

			item.registerIcons(iconRegister);
			// item.icon = iconRegister.registerIcon(item.getIconString());
		}
	}

	@Override
	public boolean showDescription(int meta) {
		// TODO Auto-generated method stub
		return false;
	}
}
