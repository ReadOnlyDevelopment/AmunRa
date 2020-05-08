package de.katzenpapst.amunra.event;

import de.katzenpapst.amunra.item.ItemBasicMulti;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class FurnaceHandler implements IFuelHandler {



	@Override
	public int getBurnTime(ItemStack fuel) {

		if(fuel.getItem() instanceof ItemBasicMulti) {
			return ((ItemBasicMulti)fuel.getItem()).getFuelDuration(fuel.getItemDamage());
		}
		return 0;
	}

}
