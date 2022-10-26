package teamfusion.bettas.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import teamfusion.bettas.block.MossBallBlock;
import teamfusion.bettas.init.BettasBlocks;
import teamfusion.bettas.init.BettasEntities;
import teamfusion.bettas.init.BettasItems;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class BettaFishEntity extends AbstractFish implements Bucketable {
    public static final int MAX_VARIANTS = 120;
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(BettaFishEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(BettaFishEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> CALMED = SynchedEntityData.defineId(BettaFishEntity.class, EntityDataSerializers.BOOLEAN);

    static final Predicate<BettaFishEntity> CALMED_ENTITY = (p_28528_) -> {
        return p_28528_.isCalmed() && p_28528_.isAlive();
    };

    public BettaFishEntity(EntityType<? extends BettaFishEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 2.0D, true));
        this.goalSelector.addGoal(1, new RandomSwimmingGoal(this, 1.0D, 20));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this, BettaFishEntity.class));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, BettaFishEntity.class, false) {
            @Override
            public boolean canUse() {
                return isFromBucket() && !isCalmed() && super.canUse();
            }


            @Override
            public boolean canContinueToUse() {
                return !isCalmed() && super.canContinueToUse();
            }
        });
    }

    private boolean isFromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    public void setFromBucket(boolean p_203706_1_) {
        this.entityData.set(FROM_BUCKET, p_203706_1_);
    }

    public boolean isCalmed() {
        return this.entityData.get(CALMED);
    }

    public void setCalmed(boolean calmed) {
        this.entityData.set(CALMED, calmed);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(FROM_BUCKET, false);
        this.entityData.define(CALMED, false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes().add(Attributes.MAX_HEALTH, 6.0D).add(Attributes.ATTACK_DAMAGE, 1.0D);
    }

    public static boolean checkBettaFishSpawnRules(EntityType<? extends AbstractFish> type, BlockGetter worldIn, MobSpawnType reason, BlockPos p_223363_3_, Random randomIn) {
        return worldIn.getBlockState(p_223363_3_).is(Blocks.WATER) && worldIn.getBlockState(p_223363_3_.above()).is(Blocks.WATER) && randomIn.nextFloat() > 0.9;
    }

    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getEntity();
            if (entity != null && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.hurt(source, amount);
        }
    }

    public boolean doHurtTarget(Entity entityIn) {
        boolean flag = entityIn.hurt(DamageSource.mobAttack(this), (float) ((int) this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.doEnchantDamageEffects(this, entityIn);
        }

        return flag;
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    private void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return !this.isFromBucket() && !this.hasCustomName();
    }

    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.isFromBucket();
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level.isClientSide()) {
            if (isMossBallNearby(true) && !isCalmed()) {

                this.setCalmed(true);
            } else if (isCalmed() && !isMossBallNearby(false)) {
                this.setCalmed(false);
            }
        }
    }

    private boolean isMossBallNearby(boolean afterCheck) {
        boolean flag = false;
        int mossCount = afterCheck ? 0 : 1;
        BlockPos blockpos = this.blockPosition();
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                for (int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
                    for (int l = k < j && k > -j ? j : 0; l <= j; l = l > 0 ? -l : 1 - l) {
                        blockpos$mutable.setWithOffset(blockpos, k, i, l);
                        if (this.level.getBlockState(blockpos$mutable).is(BettasBlocks.MOSS_BALL.get())) {
                            flag = true;
                            mossCount += this.level.getBlockState(blockpos$mutable).getValue(MossBallBlock.BALLS);
                        } else if (this.level.getBlockState(blockpos$mutable).is(BettasBlocks.MOSS_BALL_BLOCK.get())) {
                            flag = true;
                            mossCount += 8;
                        }
                    }
                }
            }
        }
        if (flag) {
            List<? extends BettaFishEntity> list = this.level.getEntities(BettasEntities.BETTA_FISH.get(), this.getBoundingBox().inflate(8.0D), CALMED_ENTITY);

            if (list.isEmpty() || list.size() < 2 * mossCount) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", getVariant());
        compound.putBoolean("FromBucket", this.isFromBucket());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setVariant(Mth.clamp(compound.getInt("Variant"), 0, MAX_VARIANTS - 1));
        this.setFromBucket(compound.getBoolean("FromBucket"));
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        if (dataTag == null) {
            double chance = getRandom().nextDouble();
            if (chance <= 0.45) setVariant(getRandom().nextInt(MAX_VARIANTS));
            else if (chance <= 0.7) setVariant(24);
            else setVariant(100);
        } else {
            if (dataTag.contains("Variant", 3)) {
                this.setVariant(dataTag.getInt("Variant"));
            }
            if (dataTag.contains("Health", 99)) {
                this.setHealth(dataTag.getFloat("Health"));
            }
        }
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public void saveToBucketTag(ItemStack bucket) {
        CompoundTag compoundnbt = bucket.getOrCreateTag();
        compoundnbt.putInt("Variant", this.getVariant());
        compoundnbt.putFloat("Health", this.getHealth());
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(BettasItems.BETTA_FISH_BUCKET.get());
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.COD_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.COD_HURT;
    }

    @Override
    public ItemStack getPickedResult(HitResult result) {
        return new ItemStack(BettasItems.BETTA_FISH_SPAWN_EGG.get());
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        float maxHealth = this.getMaxHealth();
        float health = this.getHealth();
        if (stack.getItem() == BettasItems.BLACKWATER_BOTTLE.get() && health < maxHealth) {
            if (!player.isCreative()) {
                stack.shrink(1);
            }
            heal(3);
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level.addParticle(ParticleTypes.HEART, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
            return InteractionResult.PASS;
        }
        return super.interactAt(player, vec, hand);
    }
}