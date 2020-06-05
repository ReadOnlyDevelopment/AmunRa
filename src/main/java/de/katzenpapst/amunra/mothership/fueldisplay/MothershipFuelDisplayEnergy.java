package de.katzenpapst.amunra.mothership.fueldisplay;

import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import net.minecraft.item.ItemStack;

public class MothershipFuelDisplayEnergy extends MothershipFuelDisplay {

	protected static MothershipFuelDisplayEnergy instance = null;

	public static MothershipFuelDisplayEnergy getInstance() {
		if (instance == null) {
			instance = new MothershipFuelDisplayEnergy();
		}
		return instance;
	}

	protected ItemStack stack;

	protected MothershipFuelDisplayEnergy() {
		stack = new ItemStack(GCItems.battery, 1, 0);
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof MothershipFuelDisplayEnergy))
			return false;
		return other == this;
	}

	@Override
	public String formatValue(float value) {
		// EnergyDisplayHelper
		return EnergyDisplayHelper.getEnergyDisplayS(value);
	}

	@Override
	public String getDisplayName() {
		return StatCollector.translateToLocal("gui.message.energy");
		// return stack.getDisplayName();
	}

	@Override
	public float getFactor() {
		return 1;
	}

	@Override
	public IIcon getIcon() {

		return stack.getItem().getIconFromDamage(stack.getItemDamage());
	}

	@Override
	public int getSpriteNumber() {
		return stack.getItemSpriteNumber();
	}

	@Override
	public String getUnit() {
		return "gJ";
	}

	@Override
	public int hashCode() {
		return stack.hashCode() + 135842;
	}

}
