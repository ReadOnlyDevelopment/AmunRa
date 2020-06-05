package de.katzenpapst.amunra.old.tile;

import de.katzenpapst.amunra.mothership.Mothership;
import de.katzenpapst.amunra.old.block.BlockMachineMeta;
import de.katzenpapst.amunra.old.block.SubBlockMachine;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.core.tile.TileEntityAdvanced;
import net.minecraft.util.EnumFacing;

public class TileEntityMothershipController extends TileEntityAdvanced {

	protected CelestialBody selectedTarget;

	protected Mothership	parentMothership;
	private SubBlockMachine	subBlock	= null;

	public TileEntityMothershipController(String tileName) {
		super(tileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getPacketCooldown() {
		return 3;
	}

	@Override
	public double getPacketRange() {
		return 12.0D;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		// TODO Auto-generated method stub
		return null;
	}

	public SubBlockMachine getSubBlock() {
		if (subBlock == null) {
			subBlock = (SubBlockMachine) ((BlockMachineMeta) this.getBlockType()).getSubBlock(this.getBlockMetadata());
		}
		return subBlock;
	}

	@Override
	public boolean isNetworkedTile() {
		return true;
	}

}
