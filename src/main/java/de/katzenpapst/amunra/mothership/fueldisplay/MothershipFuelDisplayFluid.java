package de.katzenpapst.amunra.mothership.fueldisplay;

import net.minecraftforge.fluids.Fluid;

public class MothershipFuelDisplayFluid extends MothershipFuelDisplay {

	private Fluid fluid;

	public MothershipFuelDisplayFluid(Fluid fluid) {
		this.fluid = fluid;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof MothershipFuelDisplayFluid))
			return false;
		return fluid == ((MothershipFuelDisplayFluid) other).fluid;
	}

	@Override
	public String getDisplayName() {
		return fluid.getLocalizedName();
	}

	@Override
	public float getFactor() {
		return 0.001F;
	}

	@Override
	public IIcon getIcon() {
		return fluid.getIcon();
	}

	@Override
	public int getSpriteNumber() {
		return fluid.getSpriteNumber();
	}

	@Override
	public String getUnit() {
		return "B";
	}

	@Override
	public int hashCode() {
		return fluid.hashCode() + 89465;
	}

}
