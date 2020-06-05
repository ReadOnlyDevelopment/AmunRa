package de.katzenpapst.amunra.mob.entity;

import de.katzenpapst.amunra.old.tile.ITileDungeonSpawner;
import net.minecraft.util.math.AxisAlignedBB;

public interface IAmunRaBoss {
	public void despawnBoss();

	public AxisAlignedBB getRoomArea();

	public ITileDungeonSpawner getSpawner();

	public void setRoomArea(AxisAlignedBB aabb);

	public void setSpawner(ITileDungeonSpawner spawner);
}
