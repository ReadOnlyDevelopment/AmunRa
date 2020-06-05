package de.katzenpapst.amunra.mob.entity;

import java.util.ArrayList;

import micdoodle8.mods.galacticraft.api.world.EnumAtmosphericGas;

/**
 * @deprecated
 * @see micdoodle8.mods.galacticraft.api.entity.IEntityBreatheable#canBreath()
 */
@Deprecated
public interface IEntityNonOxygenBreather {
	/**
	 * Return true if the creature can breathe in the given atmosphere
	 * 
	 * @param atmosphere     ArrayList of EnumAtmosphericGas
	 * @param isInSealedArea if true, behave as if oxygen is present in the atmosphere
	 * @return
	 */
	public boolean canBreatheIn(ArrayList<EnumAtmosphericGas> atmosphere, boolean isInSealedArea);
}
