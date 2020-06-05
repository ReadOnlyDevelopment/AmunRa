package de.katzenpapst.amunra.old.item;

import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.entity.EntityBaseLaserArrow;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemAbstractRaygun extends ItemAbstractBatteryUser {

	// set to true for chargeMode, instead of single-shot mode, which would fire each time
	// the player rightclicks
	protected boolean chargeMode = false;

	public ItemAbstractRaygun(String assetName) {
		this.setUnlocalizedName(assetName);
		this.setTextureName(AmunRa.TEXTUREPREFIX + assetName);
		this.maxStackSize = 1;

		// batteryInUse = new ItemStack(GCItems.battery, 1);
		// batteryInUse.getTagCompound()
	}

	abstract protected EntityBaseLaserArrow createProjectile(ItemStack itemStack, EntityPlayer entityPlayer, World world);

	protected boolean fire(ItemStack itemStack, EntityPlayer entityPlayer, World world) {
		if (!entityPlayer.capabilities.isCreativeMode) {
			this.setElectricity(itemStack, this.getElectricityStored(itemStack) - this.getModifiedEnergyPerShot(itemStack));
		}
		if (!world.isRemote) {
			world.playSoundAtEntity(entityPlayer, getFiringSound(), 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
			// LaserArrow entityarrow = new LaserArrow(world, entityPlayer);
			spawnProjectile(itemStack, entityPlayer, world);
		}
		return true;
	}

	@Override
	public CreativeTabs getCreativeTab() {
		return AmunRa.arTab;
	}

	protected String getEmptySound() {
		return AmunRa.TEXTUREPREFIX + "weapon.lasergun.empty";
	}

	public float getEnergyPerShot() {
		return 300.0F;
	}

	protected String getFiringSound() {
		return AmunRa.TEXTUREPREFIX + "weapon.lasergun.shot";
	}

	@Override
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		return super.getIcon(stack, renderPass, player, usingItem, useRemaining);

		/*
		 * final int count2 = useRemaining / 2;
		 * 
		 * switch (count2 % 5) { case 0: if (useRemaining == 0) { return this.icons[0]; } return this.icons[4]; case 1: return this.icons[3]; case 2: return this.icons[2]; case 3: return this.icons[1]; case 4: return this.icons[0]; }
		 * 
		 * return this.icons[0];
		 */
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based on material.
	 */
	@Override
	public int getItemEnchantability() {
		return 0;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.bow;
	}

	/**
	 * How long it takes to use or consume an item
	 */

	@Override
	public int getMaxItemUseDuration(ItemStack itemStack) {
		return 72000;
	}

	protected float getModifiedEnergyPerShot(ItemStack stack) {
		float base = this.getEnergyPerShot();

		int efficiency = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack);
		// max level seems to be 5
		float relativeEff = efficiency / 10.0F;

		base = base * (1.0F - relativeEff);

		return base;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		/*
		 * ArrowNockEvent event = new ArrowNockEvent(entityPlayer, itemStack); MinecraftForge.EVENT_BUS.post(event); if (event.isCanceled()) { return event.result; }
		 */
		if (entityPlayer.capabilities.isCreativeMode || getElectricityStored(itemStack) >= getEnergyPerShot()) {

			entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
			if (!this.chargeMode) {
				fire(itemStack, entityPlayer, world);
			}
		} else {
			if (!world.isRemote) {
				world.playSoundAtEntity(entityPlayer, getEmptySound(), 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);

			}
		}

		return itemStack;
	}

	/**
	 * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
	 */

	@Override
	public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer entityPlayer, int itemInUseCount) {
		if (!this.chargeMode)
			return;

		this.fire(itemStack, entityPlayer, world);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(this.getIconString());
		// this.itemEmptyIcon = iconRegister.registerIcon(this.getIconString() + "_empty");
	}

	protected void spawnProjectile(ItemStack itemStack, EntityPlayer entityPlayer, World world) {
		EntityBaseLaserArrow ent = createProjectile(itemStack, entityPlayer, world);

		// enchantment stuff

		world.spawnEntityInWorld(ent);
	}

}
