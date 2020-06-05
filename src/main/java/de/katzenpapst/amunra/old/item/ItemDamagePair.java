package de.katzenpapst.amunra.old.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemDamagePair {

	protected Item	item;
	protected int	damage;

	public ItemDamagePair(Item item, int damage) {
		this.item = item;
		this.damage = damage;
	}

	public ItemDamagePair(ItemStack stack) {
		this.item = stack.getItem();
		this.damage = stack.getItemDamage();
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof ItemDamagePair))
			return false;
		ItemDamagePair otherCast = (ItemDamagePair) other;

		return isSameItem(otherCast.getItem(), otherCast.getDamage());
	}

	public int getDamage() {
		return damage;
	}

	public Item getItem() {
		return item;
	}

	public ItemStack getItemStack(int numItems) {
		return new ItemStack(item, numItems, damage);
	}

	public Item getSubItem() {
		if (!(item instanceof ItemBasicMulti))
			return item;
		return ((ItemBasicMulti) item).getSubItem(damage);
	}

	@Override
	public int hashCode() {
		if (item instanceof ItemBlock)
			return ((ItemBlock) this.item).field_150939_a.hashCode() ^ ~damage;
		return item.hashCode() ^ ~damage;
	}

	public boolean isSameItem(Item item, int damage) {

		// this matters anyway, do it here before the other mess
		if (this.damage != damage)
			return false;
		if (item instanceof ItemBlock) {
			if (this.item instanceof ItemBlock)
				// compare blocks... *sigh*
				return ((ItemBlock) this.item).field_150939_a == ((ItemBlock) item).field_150939_a;
			else
				return false;
		} else {
			if (this.item instanceof ItemBlock)
				return false;
			else
				return this.item == item;
		}
	}

	public boolean isSameItem(ItemStack stack) {
		if (stack == null)
			return false;
		return isSameItem(stack.getItem(), stack.getItemDamage());
	}

}
