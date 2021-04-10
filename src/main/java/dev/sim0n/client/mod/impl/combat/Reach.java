package dev.sim0n.client.mod.impl.combat;

import dev.sim0n.client.mod.Mod;
import dev.sim0n.client.mod.ModType;
import dev.sim0n.client.setting.impl.DoubleSetting;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import java.lang.reflect.Method;
import java.util.List;

public class Reach extends Mod {
    private final Method canRiderInteractMethod;

    private final DoubleSetting range = new DoubleSetting("Range", "Extends your attack range", 0D, 6D, 3D);
    private final DoubleSetting expansion = new DoubleSetting("Expansion", "Expands your attack hitbox", 0D, 1D, 0D);

    public Reach() {
        super("Reach", ModType.COMBAT);

        addSettings(range, expansion);

        try {
            this.canRiderInteractMethod = Entity.class.getDeclaredMethod("canRiderInteract");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        mc.entityRenderer = new ReachEntityRenderer();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();

    }

    private class ReachEntityRenderer extends EntityRenderer {

        private Entity pointedEntity;

        public ReachEntityRenderer() {
            super(mc, mc.getResourceManager());
        }

        @Override
        public void getMouseOver(float p_78473_1_) {
            if (mc.getRenderViewEntity() != null) {
                if (mc.theWorld != null) {
                    mc.pointedEntity = null;
                    double d0 = mc.playerController.getBlockReachDistance();
                    mc.objectMouseOver = mc.getRenderViewEntity().rayTrace(d0, p_78473_1_);
                    double d1 = d0;
                    Vec3 vec3 = mc.getRenderViewEntity().getPositionEyes(p_78473_1_);

                    if (mc.playerController.extendedReach()) {
                        d0 = 6.0D;
                        d1 = 6.0D;
                    } else {
                        if (d0 > range.getValue()) {
                            d1 = range.getValue();
                        }

                        d0 = d1;
                    }

                    if (mc.objectMouseOver != null) {
                        d1 = mc.objectMouseOver.hitVec.distanceTo(vec3);
                    }

                    Vec3 vec31 = mc.getRenderViewEntity().getLook(p_78473_1_);
                    Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
                    this.pointedEntity = null;
                    Vec3 vec33 = null;
                    float f1 = 1.0F;
                    List<Entity> list = mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.getRenderViewEntity(), mc.getRenderViewEntity().getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand(f1, f1, f1));
                    double d2 = d1;

                    for (Object o : list) {
                        Entity entity = (Entity) o;

                        if (entity.canBeCollidedWith()) {
                            float f2 = entity.getCollisionBorderSize();
                            float expand = expansion.getValue().floatValue();
                            AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(f2, f2, f2).expand(expand, expand, expand);
                            MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

                            if (axisalignedbb.isVecInside(vec3)) {
                                if (0.0D < d2 || d2 == 0.0D) {
                                    this.pointedEntity = entity;
                                    vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                                    d2 = 0.0D;
                                }
                            } else if (movingobjectposition != null) {
                                double d3 = vec3.distanceTo(movingobjectposition.hitVec);

                                if (d3 < d2 || d2 == 0.0D) {
                                    if (entity == mc.getRenderViewEntity().ridingEntity && !canRiderInteract(entity)) {
                                        if (d2 == 0.0D) {
                                            this.pointedEntity = entity;
                                            vec33 = movingobjectposition.hitVec;
                                        }
                                    } else {
                                        this.pointedEntity = entity;
                                        vec33 = movingobjectposition.hitVec;
                                        d2 = d3;
                                    }
                                }
                            }
                        }
                    }

                    if (this.pointedEntity != null && (d2 < d1 || mc.objectMouseOver == null)) {
                        mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity, vec33);

                        if (this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof EntityItemFrame) {
                            mc.pointedEntity = this.pointedEntity;
                        }
                    }
                }
            }
        }

        private boolean canRiderInteract(Entity entity) {
            try {
                return (boolean) canRiderInteractMethod.invoke(entity);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

}
