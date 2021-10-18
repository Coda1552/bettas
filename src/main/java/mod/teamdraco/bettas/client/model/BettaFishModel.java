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
    public ModelRenderer tail;
    public ModelRenderer finTop;
    public ModelRenderer finBottom;
    public ModelRenderer finLeftBottom;
    public ModelRenderer finRightBottom;
    public ModelRenderer finLeft;
    public ModelRenderer finRight;

    public BettaFishModel() {
        this.texWidth = 32;
        this.texHeight = 16;
        this.finLeftBottom = new ModelRenderer(this, 11, 9);
        this.finLeftBottom.setPos(0.5F, 1.5F, -0.5F);
        this.finLeftBottom.addBox(0.0F, 0.0F, -1.0F, 0.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(finLeftBottom, 0.0F, 0.0F, -0.3490658503988659F);
        this.tail = new ModelRenderer(this, 0, 6);
        this.tail.setPos(0.0F, 0.0F, 3.5F);
        this.tail.addBox(0.0F, -3.0F, 0.0F, 0.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.finRight = new ModelRenderer(this, -1, 1);
        this.finRight.setPos(-1.0F, 0.5F, -0.5F);
        this.finRight.addBox(-2.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(finRight, 0.0F, 0.0F, -0.7853981633974483F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setPos(0.0F, 22.5F, -0.5F);
        this.body.addBox(-1.0F, -1.5F, -3.5F, 2.0F, 3.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.finTop = new ModelRenderer(this, 0, 1);
        this.finTop.setPos(0.0F, -1.5F, 2.0F);
        this.finTop.addBox(0.0F, -3.0F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.finRightBottom = new ModelRenderer(this, 11, 9);
        this.finRightBottom.setPos(-0.5F, 1.5F, -0.5F);
        this.finRightBottom.addBox(0.0F, 0.0F, -1.0F, 0.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(finRightBottom, 0.0F, 0.0F, 0.3490658503988659F);
        this.finBottom = new ModelRenderer(this, 11, -6);
        this.finBottom.setPos(0.0F, 1.5F, 0.5F);
        this.finBottom.addBox(0.0F, 0.0F, -3.0F, 0.0F, 2.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(finBottom, 0.0F, 3.141592653589793F, 0.0F);
        this.finLeft = new ModelRenderer(this, -1, 1);
        this.finLeft.mirror = true;
        this.finLeft.setPos(1.0F, 0.5F, -0.5F);
        this.finLeft.addBox(0.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(finLeft, 0.0F, 0.0F, 0.7853981633974483F);
        this.body.addChild(this.finLeftBottom);
        this.body.addChild(this.tail);
        this.body.addChild(this.finRight);
        this.body.addChild(this.finTop);
        this.body.addChild(this.finRightBottom);
        this.body.addChild(this.finBottom);
        this.body.addChild(this.finLeft);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.body).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f = 1.0F;
        if (!entityIn.isInWater()) {
            f = 1.5F;
        }
        this.tail.yRot = -f * 0.45F * MathHelper.sin(0.6F * ageInTicks);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
