package codyhuh.bettas.client.renderer;

import codyhuh.bettas.Bettas;
import codyhuh.bettas.entity.BettaFishEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import codyhuh.bettas.client.model.BettaFishModel;

@OnlyIn(Dist.CLIENT)
public class BettaFishRenderer extends MobRenderer<BettaFishEntity, BettaFishModel<BettaFishEntity>> {
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(new ResourceLocation(Bettas.MOD_ID, "betta"), "main");
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[BettaFishEntity.MAX_VARIANTS];

    private static final ResourceLocation BETO = new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta/beto.png");

    public BettaFishRenderer(EntityRendererProvider.Context manager) {
        super(manager, new BettaFishModel<>(manager.bakeLayer(MODEL_LAYER)), 0.2F);
        this.shadowRadius = 0.2F;
    }

    @Override
    public ResourceLocation getTextureLocation(BettaFishEntity entity) {
        String s = ChatFormatting.stripFormatting(entity.getName().getString());
        if (s != null && ("beto".equals(s) || "Beto".equals(s))) {
            return BETO;
        }
        int variant = entity.getVariant();
        if (TEXTURES[variant] == null) {
            ResourceLocation loc = new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta/body_" + variant + ".png");
            if (!Minecraft.getInstance().getResourceManager().getResource(loc).isPresent()) {
                System.out.println("Found Unknown variant " + variant + ", using default");
                loc = new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta/body_0.png");
            }

            return TEXTURES[variant] = loc;
        }

        return TEXTURES[variant];
    }


    @Override
    protected void setupRotations(BettaFishEntity entityLiving, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        float f = 4.3F * Mth.sin(0.6F * ageInTicks);
        matrixStackIn.mulPose(Axis.YP.rotationDegrees(f));
        if (!entityLiving.isInWater()) {
            matrixStackIn.translate(0.2d, 0.1d, 0);
            matrixStackIn.mulPose(Axis.ZP.rotationDegrees(90.0F));
        }
    }
}