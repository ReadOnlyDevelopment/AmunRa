package de.katzenpapst.amunra.old.tile;

import de.katzenpapst.amunra.mob.entity.IAmunRaBoss;
import de.katzenpapst.amunra.vec.Vector3int;
import net.minecraft.util.math.AxisAlignedBB;

public interface ITileDungeonSpawner {
	public Vector3int getBlockPosition();

	public AxisAlignedBB getRoomArea();

	public IAmunRaBoss getSpawnedBoss();

	public void onBossDefeated();

	public void setBossClass(Class<? extends IAmunRaBoss> theClass);

	public void setRoomArea(AxisAlignedBB aabb);

	public void setSpawnedBoss(IAmunRaBoss boss);
}
