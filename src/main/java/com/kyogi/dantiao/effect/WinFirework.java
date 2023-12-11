package com.kyogi.dantiao.effect;

import com.kyogi.dantiao.util.Positions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;

public class WinFirework {
	public static void setFirework(Positions positions) {
		if (positions == null) {
			return;
		}
		FireworkRocketEntity firework = new FireworkRocketEntity(EntityType.FIREWORK_ROCKET, positions.getLevel());
		firework.moveTo(positions.getPosition().getX(),positions.getPosition().getY(),positions.getPosition().getZ());
	}
}
