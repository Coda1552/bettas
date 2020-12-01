package mod.teamdraco.bettas.client.renderer;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import mod.teamdraco.bettas.Bettas;
import mod.teamdraco.bettas.client.model.BettaFishModel;
import mod.teamdraco.bettas.entity.BettaFishEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class BettaFishRenderer extends MobRenderer<BettaFishEntity, BettaFishModel<BettaFishEntity>> {
    public static final Map<Integer, ResourceLocation> TEXTURES = Util.make(Maps.newHashMap(), (hashMap) -> {
        hashMap.put(0, new ResourceLocation(Bettas.MOD_ID, "textures/entity/common/common_11.png"));
        hashMap.put(1, new ResourceLocation(Bettas.MOD_ID, "textures/entity/white/white_base_masked.png"));
        hashMap.put(2, new ResourceLocation(Bettas.MOD_ID, "textures/entity/common/common_12.png"));
        hashMap.put(3, new ResourceLocation(Bettas.MOD_ID, "textures/entity/common/common_13.png"));
        hashMap.put(4, new ResourceLocation(Bettas.MOD_ID, "textures/entity/common/common_14.png"));
        hashMap.put(5, new ResourceLocation(Bettas.MOD_ID, "textures/entity/common/common_15.png"));
        hashMap.put(6, new ResourceLocation(Bettas.MOD_ID, "textures/entity/black/black_base_dragon_scale.png"));
        hashMap.put(7, new ResourceLocation(Bettas.MOD_ID, "textures/entity/blue/blue_base.png"));
        hashMap.put(8, new ResourceLocation(Bettas.MOD_ID, "textures/entity/blue/blue_base_crown_fin.png"));
        hashMap.put(9, new ResourceLocation(Bettas.MOD_ID, "textures/entity/blue/blue_base_dragon_scale.png"));
        hashMap.put(10, new ResourceLocation(Bettas.MOD_ID, "textures/entity/cellophane/cellophane_base_tancho_4.png"));
        hashMap.put(11, new ResourceLocation(Bettas.MOD_ID, "textures/entity/cellophane/cellophane_base_tancho.png"));
        hashMap.put(12, new ResourceLocation(Bettas.MOD_ID, "textures/entity/common/common_16.png"));
        hashMap.put(13, new ResourceLocation(Bettas.MOD_ID, "textures/entity/red/red_base_koi.png"));
        hashMap.put(14, new ResourceLocation(Bettas.MOD_ID, "textures/entity/common/common_10.png"));
        hashMap.put(15, new ResourceLocation(Bettas.MOD_ID, "textures/entity/yellow/yellow_base_koi.png"));
        hashMap.put(16, new ResourceLocation(Bettas.MOD_ID, "textures/entity/orange/orange_base.png"));
        hashMap.put(17, new ResourceLocation(Bettas.MOD_ID, "textures/entity/orange/orange_base_crown_fin.png"));
        hashMap.put(18, new ResourceLocation(Bettas.MOD_ID, "textures/entity/orange/orange_base_dragon_scale.png"));
        hashMap.put(19, new ResourceLocation(Bettas.MOD_ID, "textures/entity/red/red_base.png"));
        hashMap.put(20, new ResourceLocation(Bettas.MOD_ID, "textures/entity/red/red_base_crown_fin.png"));
        hashMap.put(21, new ResourceLocation(Bettas.MOD_ID, "textures/entity/common/common.png"));
        hashMap.put(22, new ResourceLocation(Bettas.MOD_ID, "textures/entity/red/red_base_dragon_scale.png"));
        hashMap.put(23, new ResourceLocation(Bettas.MOD_ID, "textures/entity/white/white_base.png"));
        hashMap.put(24, new ResourceLocation(Bettas.MOD_ID, "textures/entity/wild/wild.png"));
        hashMap.put(25, new ResourceLocation(Bettas.MOD_ID, "textures/entity/yellow/yellow_base.png"));
        hashMap.put(26, new ResourceLocation(Bettas.MOD_ID, "textures/entity/yellow/yellow_base_crown_fin.png"));
        hashMap.put(27, new ResourceLocation(Bettas.MOD_ID, "textures/entity/yellow/yellow_base_dragon_scale.png"));
        hashMap.put(28, new ResourceLocation(Bettas.MOD_ID, "textures/entity/black/black_base.png"));
        hashMap.put(29, new ResourceLocation(Bettas.MOD_ID, "textures/entity/black/black_base_samurai.png"));
        hashMap.put(30, new ResourceLocation(Bettas.MOD_ID, "textures/entity/black/black_base_butterfly.png"));
        hashMap.put(31, new ResourceLocation(Bettas.MOD_ID, "textures/entity/black/black_base_dumbo.png"));
        hashMap.put(32, new ResourceLocation(Bettas.MOD_ID, "textures/entity/black/black_base_dumbo_masked.png"));
        hashMap.put(33, new ResourceLocation(Bettas.MOD_ID, "textures/entity/black/black_base_crown_fin_samurai.png"));
        hashMap.put(34, new ResourceLocation(Bettas.MOD_ID, "textures/entity/black/black_base_dumbo_crown_fin_masked.png"));
        hashMap.put(35, new ResourceLocation(Bettas.MOD_ID, "textures/entity/black/black_base_koi.png"));
        hashMap.put(36, new ResourceLocation(Bettas.MOD_ID, "textures/entity/black/black_base_crown_fin.png"));
        hashMap.put(37, new ResourceLocation(Bettas.MOD_ID, "textures/entity/black/black_base_masked.png"));
        hashMap.put(38, new ResourceLocation(Bettas.MOD_ID, "textures/entity/blue/blue_base_samurai.png"));
        hashMap.put(39, new ResourceLocation(Bettas.MOD_ID, "textures/entity/blue/blue_base_butterfly.png"));
        hashMap.put(40, new ResourceLocation(Bettas.MOD_ID, "textures/entity/blue/blue_base_dumbo.png"));
        hashMap.put(41, new ResourceLocation(Bettas.MOD_ID, "textures/entity/blue/blue_base_dumbo_masked.png"));
        hashMap.put(42, new ResourceLocation(Bettas.MOD_ID, "textures/entity/blue/blue_base_crown_fin_samurai.png"));
        hashMap.put(43, new ResourceLocation(Bettas.MOD_ID, "textures/entity/blue/blue_base_dumbo_crown_fin_masked.png"));
        hashMap.put(44, new ResourceLocation(Bettas.MOD_ID, "textures/entity/blue/blue_base_koi.png"));
        hashMap.put(45, new ResourceLocation(Bettas.MOD_ID, "textures/entity/blue/blue_base_masked.png"));
        hashMap.put(46, new ResourceLocation(Bettas.MOD_ID, "textures/entity/cellophane/cellophane_base.png"));
        hashMap.put(47, new ResourceLocation(Bettas.MOD_ID, "textures/entity/cellophane/cellophane_base_dragon_scale.png"));
        hashMap.put(48, new ResourceLocation(Bettas.MOD_ID, "textures/entity/cellophane/cellophane_base_samurai.png"));
        hashMap.put(49, new ResourceLocation(Bettas.MOD_ID, "textures/entity/cellophane/cellophane_base_tancho_2.png"));
        hashMap.put(50, new ResourceLocation(Bettas.MOD_ID, "textures/entity/cellophane/cellophane_base_tancho_3.png"));
        hashMap.put(51, new ResourceLocation(Bettas.MOD_ID, "textures/entity/cellophane/cellophane_base_tancho_4.png"));
        hashMap.put(52, new ResourceLocation(Bettas.MOD_ID, "textures/entity/cellophane/cellophane_base_tancho_6.png"));
        hashMap.put(53, new ResourceLocation(Bettas.MOD_ID, "textures/entity/cellophane/cellophane_base_dumbo.png"));
        hashMap.put(54, new ResourceLocation(Bettas.MOD_ID, "textures/entity/cellophane/cellophane_base_dumbo_masked.png"));
        hashMap.put(55, new ResourceLocation(Bettas.MOD_ID, "textures/entity/cellophane/cellophane_base_crown_fin_samurai.png"));
        hashMap.put(56, new ResourceLocation(Bettas.MOD_ID, "textures/entity/cellophane/cellophane_base_dumbo_crown_fin_masked.png"));
        hashMap.put(57, new ResourceLocation(Bettas.MOD_ID, "textures/entity/cellophane/cellophane_base_koi.png"));
        hashMap.put(58, new ResourceLocation(Bettas.MOD_ID, "textures/entity/cellophane/cellophane_base_crown_fin.png"));
        hashMap.put(59, new ResourceLocation(Bettas.MOD_ID, "textures/entity/cellophane/cellophane_base_masked.png"));
        hashMap.put(60, new ResourceLocation(Bettas.MOD_ID, "textures/entity/common/common_2.png"));
        hashMap.put(61, new ResourceLocation(Bettas.MOD_ID, "textures/entity/common/common_3.png"));
        hashMap.put(62, new ResourceLocation(Bettas.MOD_ID, "textures/entity/common/common_4.png"));
        hashMap.put(63, new ResourceLocation(Bettas.MOD_ID, "textures/entity/common/common_5.png"));
        hashMap.put(64, new ResourceLocation(Bettas.MOD_ID, "textures/entity/common/common_6.png"));
        hashMap.put(65, new ResourceLocation(Bettas.MOD_ID, "textures/entity/common/common_7.png"));
        hashMap.put(66, new ResourceLocation(Bettas.MOD_ID, "textures/entity/common/common_8.png"));
        hashMap.put(67, new ResourceLocation(Bettas.MOD_ID, "textures/entity/common/common_9.png"));
        hashMap.put(67, new ResourceLocation(Bettas.MOD_ID, "textures/entity/common/common_9.png"));
        hashMap.put(68, new ResourceLocation(Bettas.MOD_ID, "textures/entity/golden/golden_base.png"));
        hashMap.put(69, new ResourceLocation(Bettas.MOD_ID, "textures/entity/golden/golden_base_butterfly.png"));
        hashMap.put(70, new ResourceLocation(Bettas.MOD_ID, "textures/entity/golden/golden_base_dragon_scale.png"));
        hashMap.put(71, new ResourceLocation(Bettas.MOD_ID, "textures/entity/golden/golden_base_samurai.png"));
        hashMap.put(72, new ResourceLocation(Bettas.MOD_ID, "textures/entity/golden/golden_base_butterfly.png"));
        hashMap.put(73, new ResourceLocation(Bettas.MOD_ID, "textures/entity/golden/golden_base_dumbo.png"));
        hashMap.put(74, new ResourceLocation(Bettas.MOD_ID, "textures/entity/golden/golden_base_dumbo_masked.png"));
        hashMap.put(75, new ResourceLocation(Bettas.MOD_ID, "textures/entity/golden/golden_base_crown_fin_samurai.png"));
        hashMap.put(76, new ResourceLocation(Bettas.MOD_ID, "textures/entity/golden/golden_base_dumbo_crown_fin_masked.png"));
        hashMap.put(77, new ResourceLocation(Bettas.MOD_ID, "textures/entity/golden/golden_base_crown_fin.png"));
        hashMap.put(78, new ResourceLocation(Bettas.MOD_ID, "textures/entity/golden/golden_base_masked.png"));
        hashMap.put(79, new ResourceLocation(Bettas.MOD_ID, "textures/entity/orange/orange_base_samurai.png"));
        hashMap.put(80, new ResourceLocation(Bettas.MOD_ID, "textures/entity/orange/orange_base_butterfly.png"));
        hashMap.put(81, new ResourceLocation(Bettas.MOD_ID, "textures/entity/orange/orange_base_dumbo.png"));
        hashMap.put(82, new ResourceLocation(Bettas.MOD_ID, "textures/entity/orange/orange_base_dumbo_masked.png"));
        hashMap.put(83, new ResourceLocation(Bettas.MOD_ID, "textures/entity/orange/orange_base_crown_fin_samurai.png"));
        hashMap.put(84, new ResourceLocation(Bettas.MOD_ID, "textures/entity/orange/orange_base_dumbo_crown_fin_masked.png"));
        hashMap.put(85, new ResourceLocation(Bettas.MOD_ID, "textures/entity/orange/orange_base_koi.png"));
        hashMap.put(86, new ResourceLocation(Bettas.MOD_ID, "textures/entity/orange/orange_base_masked.png"));
        hashMap.put(87, new ResourceLocation(Bettas.MOD_ID, "textures/entity/red/red_base_samurai.png"));
        hashMap.put(88, new ResourceLocation(Bettas.MOD_ID, "textures/entity/red/red_base_butterfly.png"));
        hashMap.put(89, new ResourceLocation(Bettas.MOD_ID, "textures/entity/red/red_base_dumbo.png"));
        hashMap.put(90, new ResourceLocation(Bettas.MOD_ID, "textures/entity/red/red_base_dumbo_masked.png"));
        hashMap.put(91, new ResourceLocation(Bettas.MOD_ID, "textures/entity/red/red_base_crown_fin_samurai.png"));
        hashMap.put(92, new ResourceLocation(Bettas.MOD_ID, "textures/entity/red/red_base_dumbo_crown_fin_masked.png"));
        hashMap.put(93, new ResourceLocation(Bettas.MOD_ID, "textures/entity/red/red_base_masked.png"));
        hashMap.put(94, new ResourceLocation(Bettas.MOD_ID, "textures/entity/white/white_base_butterfly.png"));
        hashMap.put(95, new ResourceLocation(Bettas.MOD_ID, "textures/entity/white/white_base_dumbo.png"));
        hashMap.put(96, new ResourceLocation(Bettas.MOD_ID, "textures/entity/white/white_base_dumbo_masked.png"));
        hashMap.put(97, new ResourceLocation(Bettas.MOD_ID, "textures/entity/white/white_base_dumbo_crown_fin_masked.png"));
        hashMap.put(98, new ResourceLocation(Bettas.MOD_ID, "textures/entity/white/white_base_koi.png"));
        hashMap.put(99, new ResourceLocation(Bettas.MOD_ID, "textures/entity/white/white_base_crown_fin.png"));
        hashMap.put(100, new ResourceLocation(Bettas.MOD_ID, "textures/entity/wild/wild_2.png"));
        hashMap.put(101, new ResourceLocation(Bettas.MOD_ID, "textures/entity/yellow/yellow_base_samurai.png"));
        hashMap.put(102, new ResourceLocation(Bettas.MOD_ID, "textures/entity/yellow/yellow_base_butterfly.png"));
        hashMap.put(103, new ResourceLocation(Bettas.MOD_ID, "textures/entity/yellow/yellow_base_dumbo.png"));
        hashMap.put(104, new ResourceLocation(Bettas.MOD_ID, "textures/entity/yellow/yellow_base_dumbo_masked.png"));
        hashMap.put(105, new ResourceLocation(Bettas.MOD_ID, "textures/entity/yellow/yellow_base_crown_fin_samurai.png"));
        hashMap.put(106, new ResourceLocation(Bettas.MOD_ID, "textures/entity/yellow/yellow_base_dumbo_crown_fin_masked.png"));
        hashMap.put(107, new ResourceLocation(Bettas.MOD_ID, "textures/entity/yellow/yellow_base_masked.png"));
    });

    public BettaFishRenderer(EntityRendererManager manager) {
        super(manager, new BettaFishModel<>(), 0.2f);
    }

    @Override
    public ResourceLocation getEntityTexture(BettaFishEntity entity) {
        return TEXTURES.getOrDefault(entity.getVariant(), TEXTURES.get(0));
    }

    @Override
    protected void applyRotations(BettaFishEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        float f = 4.3F * MathHelper.sin(0.6F * ageInTicks);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f));
        if (!entityLiving.isInWater()) {
            matrixStackIn.translate((double)0.2F, (double)0.1F, 0.0D);
            matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(90.0F));
        }
    }
}