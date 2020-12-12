package mod.teamdraco.bettas.entity;

import mod.teamdraco.bettas.init.BettasItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BettaFishEntity extends AbstractFishEntity {
    public static final int MAX_VARIANTS = 108;

    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(BettaFishEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> FROM_BUCKET = EntityDataManager.createKey(BettaFishEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Float> HEALTH = EntityDataManager.createKey(BettaFishEntity.class, DataSerializers.FLOAT);

    public BettaFishEntity(EntityType<? extends BettaFishEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(VARIANT, 0);
        this.dataManager.register(FROM_BUCKET, false);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 2.0D, true));
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }

    private boolean isFromBucket() {
        return this.dataManager.get(FROM_BUCKET);
    }

    public void setFromBucket(boolean p_203706_1_) {
        this.dataManager.set(FROM_BUCKET, p_203706_1_);
    }

    public static AttributeModifierMap.MutableAttribute func_234176_m_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 6.0D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0D);
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getTrueSource();
            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    public int getVariant() {
        return this.dataManager.get(VARIANT);
    }

    private void setVariant(int variant) {
        this.dataManager.set(VARIANT, variant);
    }

    public boolean canDespawn(double distanceToClosestPlayer) {
        return !this.isFromBucket() && !this.hasCustomName();
    }

    public boolean preventDespawn() {
        return super.preventDespawn() || this.isFromBucket();
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Variant", getVariant());
        compound.putBoolean("FromBucket", this.isFromBucket());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        setVariant(compound.getInt("Variant"));
        this.setFromBucket(compound.getBoolean("FromBucket"));
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (dataTag == null) {
            double chance = getRNG().nextDouble();
            if (chance <= 0.33) setVariant(getRNG().nextInt(MAX_VARIANTS));
            else if (chance <= 0.5) setVariant(24);
            else setVariant(100);
        }
        else {
            if (dataTag.contains("BucketVariantTag", 3)) {
                this.setVariant(dataTag.getInt("BucketVariantTag"));
                this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, BettaFishEntity.class, false));
            }
            if (dataTag.contains("Health", 99)) {
                this.setHealth(dataTag.getFloat("Health"));
            }
        }
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    protected void setBucketData(ItemStack bucket) {
        CompoundNBT compoundnbt = bucket.getOrCreateTag();
        compoundnbt.putInt("BucketVariantTag", this.getVariant());
        compoundnbt.putFloat("Health", this.getHealth());
    }

    @Override
    protected ItemStack getFishBucket() {
        return new ItemStack(BettasItems.BETTA_FISH_BUCKET.get());
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_COD_FLOP;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_COD_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_COD_HURT;
    }

    static class SwimGoal extends RandomSwimmingGoal {
        private final BettaFishEntity fish;

        public SwimGoal(BettaFishEntity fish) {
            super(fish, 1.0D, 40);
            this.fish = fish;
        }

        public boolean shouldExecute() {
            return this.fish.func_212800_dy() && super.shouldExecute();
        }
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult result) {
        return new ItemStack(BettasItems.BETTA_FISH_SPAWN_EGG.get());
    }

    @Override
    public ActionResultType applyPlayerInteraction(PlayerEntity player, Vector3d vec, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        float maxHealth = this.getMaxHealth();
        float health = this.getHealth();
        if (stack.getItem() == BettasItems.BLACKWATER_BOTTLE.get() && health < maxHealth) {
            if (!player.isCreative()) {
                stack.shrink(1);
            }
            heal(3);
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            this.world.addParticle(ParticleTypes.HEART, this.getPosXRandom(1.0D), this.getPosYRandom() + 0.5D, this.getPosZRandom(1.0D), d0, d1, d2);
            return ActionResultType.PASS;
        }
        return super.applyPlayerInteraction(player, vec, hand);
    }
}