package mod.teamdraco.bettas.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BettaFishModel<T extends Entity> extends EntityModel<T> {
    public ModelRenderer body;
    public ModelRenderer finLeft;
    public ModelRenderer finRight;
    public ModelRenderer finTop;
    public ModelRenderer finBottom;
    public ModelRenderer tail;

    public BettaFishModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 22.5F, 0.0F);
        this.body.addBox(-1.0F, -1.5F, -2.5F, 2.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.finTop = new ModelRenderer(this, 0, 8);
        this.finTop.setRotationPoint(0.0F, -1.5F, 1.0F);
        this.finTop.addBox(0.0F, -3.0F, -2.5F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 9, -5);
        this.tail.setRotationPoint(0.0F, 0.0F, 2.5F);
        this.tail.addBox(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.finBottom = new ModelRenderer(this, 0, 4);
        this.finBottom.setRotationPoint(0.0F, 1.5F, 0.5F);
        this.finBottom.addBox(0.0F, 0.0F, -2.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.finRight = new ModelRenderer(this, 0, 0);
        this.finRight.setRotationPoint(1.0F, 0.0F, 0.0F);
        this.finRight.addBox(0.0F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.finLeft = new ModelRenderer(this, 0, 0);
        this.finLeft.setRotationPoint(-1.0F, 0.0F, 0.0F);
        this.finLeft.addBox(-1.0F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.body.addChild(this.finTop);
        this.body.addChild(this.tail);
        this.body.addChild(this.finBottom);
        this.body.addChild(this.finRight);
        this.body.addChild(this.finLeft);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.body).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f = 1.0F;
        if (!entityIn.isInWater()) {
            f = 1.5F;
        }
        this.tail.rotateAngleY = -f * 0.45F * MathHelper.sin(0.6F * ageInTicks);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
