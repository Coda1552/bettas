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
        hashMap.put(0, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_1.png"));
        hashMap.put(1, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_2.png"));
        hashMap.put(2, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_3.png"));
        hashMap.put(3, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_4.png"));
        hashMap.put(4, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_5.png"));
        hashMap.put(5, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_6.png"));
        hashMap.put(6, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_7.png"));
        hashMap.put(7, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_8.png"));
        hashMap.put(8, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_9.png"));
        hashMap.put(9, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_10.png"));
        hashMap.put(10, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_11.png"));
        hashMap.put(11, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_12.png"));
        hashMap.put(12, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_13.png"));
        hashMap.put(13, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_14.png"));
        hashMap.put(14, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_15.png"));
        hashMap.put(15, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_16.png"));
        hashMap.put(16, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_17.png"));
        hashMap.put(17, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_18.png"));
        hashMap.put(18, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_19.png"));
        hashMap.put(19, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_20.png"));
        hashMap.put(20, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_21.png"));
        hashMap.put(21, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_22.png"));
        hashMap.put(22, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_23.png"));
        hashMap.put(23, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_24.png"));
        hashMap.put(24, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_25.png"));
        hashMap.put(25, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_26.png"));
        hashMap.put(26, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_27.png"));
        hashMap.put(27, new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta_28.png"));
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