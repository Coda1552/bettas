package teamdraco.bettas.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BettaFishModel<T extends Entity> extends EntityModel<T> {
    public ModelPart body;
    public ModelPart tail;
    public ModelPart finTop;
    public ModelPart finBottom;
    public ModelPart finLeftBottom;
    public ModelPart finRightBottom;
    public ModelPart finLeft;
    public ModelPart finRight;

    //Constructor
    public BettaFishModel(ModelPart root) {
        this.body = root.getChild("body");
        this.finLeftBottom = this.body.getChild("finLeftBottom");
        this.tail = this.body.getChild("tail");
        this.finRight = this.body.getChild("finRight");
        this.finTop = this.body.getChild("finTop");
        this.finRightBottom = this.body.getChild("finRightBottom");
        this.finBottom = this.body.getChild("finBottom");
        this.finLeft = this.body.getChild("finLeft");
    }

    //Layer Definition
    @SuppressWarnings("unused")
	public static LayerDefinition createLayerDefinition() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.5F, -3.5F, 2.0F, 3.0F, 7.0F, false), PartPose.offsetAndRotation(0.0F, 22.5F, -0.5F, 0.0F, 0.0F, 0.0F));
        PartDefinition finLeftBottom = body.addOrReplaceChild("finLeftBottom", CubeListBuilder.create().texOffs(11, 9).addBox(0.0F, 0.0F, -1.0F, 0.0F, 3.0F, 2.0F, false), PartPose.offsetAndRotation(0.5F, 1.5F, -0.5F, 0.0F, 0.0F, -0.34906584F));
        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 6).addBox(0.0F, -3.0F, 0.0F, 0.0F, 6.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, 0.0F, 0.0F, 0.0F));
        PartDefinition finRight = body.addOrReplaceChild("finRight", CubeListBuilder.create().texOffs(-1, 1).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, false), PartPose.offsetAndRotation(-1.0F, 0.5F, -0.5F, 0.0F, 0.0F, -0.7853982F));
        PartDefinition finTop = body.addOrReplaceChild("finTop", CubeListBuilder.create().texOffs(0, 1).addBox(0.0F, -3.0F, -1.5F, 0.0F, 3.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, -1.5F, 2.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition finRightBottom = body.addOrReplaceChild("finRightBottom", CubeListBuilder.create().texOffs(11, 9).addBox(0.0F, 0.0F, -1.0F, 0.0F, 3.0F, 2.0F, false), PartPose.offsetAndRotation(-0.5F, 1.5F, -0.5F, 0.0F, 0.0F, 0.34906584F));
        PartDefinition finBottom = body.addOrReplaceChild("finBottom", CubeListBuilder.create().texOffs(11, -6).addBox(0.0F, 0.0F, -3.0F, 0.0F, 2.0F, 6.0F, false), PartPose.offsetAndRotation(0.0F, 1.5F, 0.5F, 0.0F, 3.1415927F, 0.0F));
        PartDefinition finLeft = body.addOrReplaceChild("finLeft", CubeListBuilder.create().texOffs(-1, 3).addBox(0.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, true), PartPose.offsetAndRotation(1.0F, 0.5F, -0.5F, 0.0F, 0.0F, 0.7853982F));
        return LayerDefinition.create(meshdefinition, 32, 16);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f = 1.0F;
        if (!entityIn.isInWater()) {
            f = 1.5F;
        }
        this.tail.yRot = -f * 0.45F * Mth.sin(0.6F * ageInTicks);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float p_103115_, float p_103116_, float p_103117_, float p_103118_) {
        body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, p_103115_, p_103116_, p_103117_, p_103118_);

    }
}
