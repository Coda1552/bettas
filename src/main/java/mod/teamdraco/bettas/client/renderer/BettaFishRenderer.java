package mod.teamdraco.bettas.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.teamdraco.bettas.Bettas;
import mod.teamdraco.bettas.client.model.BettaFishModel;
import mod.teamdraco.bettas.entity.BettaFishEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BettaFishRenderer extends MobRenderer<BettaFishEntity, BettaFishModel<BettaFishEntity>> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[BettaFishEntity.MAX_VARIANTS];

    public BettaFishRenderer(EntityRendererManager manager) {
        super(manager, new BettaFishModel<>(), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(BettaFishEntity entity) {
        int variant = entity.getVariant();
        if (TEXTURES[variant] == null)
        {
            ResourceLocation loc = new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta/body_" + variant + ".png");
            if (!Minecraft.getInstance().getResourceManager().hasResource(loc)) // todo: remove this block in 1.17, since variants won't matter on an update!
            {
                System.out.println("Found Unknown variant " + variant + ", using default");
                loc = new ResourceLocation(Bettas.MOD_ID, "textures/entity/betta/body_0.png");
            }

            return TEXTURES[variant] = loc;
        }

        return TEXTURES[variant];
    }

    @Override
    protected void setupRotations(BettaFishEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        float f = 4.3F * MathHelper.sin(0.6F * ageInTicks);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f));
        if (!entityLiving.isInWater()) {
            matrixStackIn.translate(0.2d, 0.1d, 0);
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
        }
    }
}