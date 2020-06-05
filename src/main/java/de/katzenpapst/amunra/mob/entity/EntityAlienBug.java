package de.katzenpapst.amunra.mob.entity;

import java.util.ArrayList;

import micdoodle8.mods.galacticraft.api.entity.IEntityBreathable;
import micdoodle8.mods.galacticraft.api.world.EnumAtmosphericGas;
import micdoodle8.mods.galacticraft.api.world.IAtmosphericGas;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityAlienBug extends EntityMob implements IEntityNonOxygenBreather, IEntityBreathable {

	public EntityAlienBug(World world) {
		super(world);
		this.setSize(1.4F, 0.9F);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.800000011920929D);
	}

	/**
	 * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
	 */
	@Override
	protected void attackEntity(Entity target, float distanceToTarget) {
		float f1 = this.getBrightness(1.0F);

		if (f1 > 0.5F && this.rand.nextInt(100) == 0) {
			this.entityToAttack = null;
		} else {
			if (distanceToTarget > 2.0F && distanceToTarget < 6.0F && this.rand.nextInt(10) == 0) {
				if (this.onGround) {
					// get closer to target?
					double deltaX = target.posX - this.posX;
					double deltaZ = target.posZ - this.posZ;
					float planarDistance = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
					this.motionX = deltaX / planarDistance * 0.5D * 0.800000011920929D + this.motionX * 0.20000000298023224D;
					this.motionZ = deltaZ / planarDistance * 0.5D * 0.800000011920929D + this.motionZ * 0.20000000298023224D;
					this.motionY = 0.4000000059604645D;
				}
			} else {
				super.attackEntity(target, distanceToTarget);
			}
		}
	}

	@Override
	public boolean canBreath() {
		return true;
	}

	@Override
	public boolean canBreatheIn(ArrayList<EnumAtmosphericGas> atmosphere, boolean isInSealedArea) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canBreatheIn(ArrayList<IAtmosphericGas> atmosphere, boolean isInSealedArea) {
		// maybe make them afraid of oxygen? maybe later

		return atmosphere.contains(IAtmosphericGas.METHANE);
	}

	/**
	 * Drop 0-2 items of this living's type.
	 * 
	 * @param hitByPlayer  - Whether this entity has recently been hit by a player.
	 * @param lootingLevel - Level of Looting used to kill this mob.
	 */
	@Override
	protected void dropFewItems(boolean hitByPlayer, int lootingLevel) {
		// not yet
		/*
		 * super.dropFewItems(hitByPlayer, lootingLevel);
		 * 
		 * if (hitByPlayer && (this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + lootingLevel) > 0)) { this.dropItem(Items.spider_eye, 1); }
		 */
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, new Byte((byte) 0));
	}

	/**
	 * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking (Animals, Spiders at day, peaceful PigZombies).
	 */
	@Override
	protected Entity findPlayerToAttack() {
		float f = this.getBrightness(1.0F);

		if (f < 0.5F) {
			double d0 = 16.0D;
			return this.world.getClosestVulnerablePlayerToEntity(this, d0);
		} else
			return null;
	}

	// onStep?
	@Override
	protected void func_145780_a(int x, int y, int z, Block block) {
		this.playSound("mob.spider.step", 0.15F, 1.0F);
	}

	/**
	 * Get this Entity's EnumCreatureAttribute
	 */
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.ARTHROPOD;
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return "mob.spider.death";
	}

	@Override
	protected Item getDropItem() {
		return Items.string;
	}

	/**
	 * Sets the Entity inside a web block.
	 */
	// @Override
	// public void setInWeb() {}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return "mob.spider.say";
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		return "mob.spider.say";
	}

	/**
	 * Returns true if the WatchableObject (Byte) is 0x01 otherwise returns false. The WatchableObject is updated using setBesideClimableBlock.
	 */
	public boolean isBesideClimbableBlock() {
		return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	/**
	 * returns true if this entity is by a ladder, false otherwise
	 */
	@Override
	public boolean isOnLadder() {
		return this.isBesideClimbableBlock();
	}

	@Override
	public boolean isPotionApplicable(PotionEffect potionEffect) {
		return true;
		// return potionEffect.getPotionID() == Potion.poison.id ? false : super.isPotionApplicable(potionEffect);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData myData) {
		Object moreDataWhat = super.onSpawnWithEgg(myData);

		/*
		 * if (this.world.rand.nextInt(100) == 0) { EntitySkeleton entityskeleton = new EntitySkeleton(this.world); entityskeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F); entityskeleton.onSpawnWithEgg((IEntityLivingData)null); this.world.spawnEntityInWorld(entityskeleton); entityskeleton.mountEntity(this); }
		 */

		/*
		 * if (moreDataWhat == null) { moreDataWhat = new AlienSpider.GroupData();
		 * 
		 * // WTF is this?! // gets a random effect or something? if (this.world.difficultySetting == EnumDifficulty.HARD && this.world.rand.nextFloat() < 0.1F * this.world.func_147462_b(this.posX, this.posY, this.posZ)) { ((AlienSpider.GroupData)moreDataWhat).setRandomPotionEffect(this.world.rand); } }
		 * 
		 * if (moreDataWhat instanceof AlienSpider.GroupData) { int i = ((AlienSpider.GroupData)moreDataWhat).potionEffectId;
		 * 
		 * if (i > 0 && Potion.potionTypes[i] != null) { this.addPotionEffect(new PotionEffect(i, Integer.MAX_VALUE)); } }
		 */
		return (IEntityLivingData) moreDataWhat;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();

		if (!this.world.isRemote) {
			this.setBesideClimbableBlock(this.isCollidedHorizontally);
		}
	}

	/**
	 * Updates the WatchableObject (Byte) created in entityInit(), setting it to 0x01 if par1 is true or 0x00 if it is false.
	 */
	public void setBesideClimbableBlock(boolean isBeside) {
		byte b0 = this.dataWatcher.getWatchableObjectByte(16);

		if (isBeside) {
			b0 = (byte) (b0 | 1);
		} else {
			b0 &= -2;
		}

		this.dataWatcher.updateObject(16, Byte.valueOf(b0));
	}

	/*
	 * public static class GroupData implements IEntityLivingData { public int potionEffectId;
	 * 
	 * public void setRandomPotionEffect(Random rand) { int i = rand.nextInt(5);
	 * 
	 * if (i <= 1) { this.potionEffectId = Potion.moveSpeed.id; } else if (i <= 2) { this.potionEffectId = Potion.damageBoost.id; } else if (i <= 3) { this.potionEffectId = Potion.regeneration.id; } else if (i <= 4) { this.potionEffectId = Potion.invisibility.id; } } }
	 */

}
