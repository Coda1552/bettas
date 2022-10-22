package teamfusion.bettas.client;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import teamfusion.bettas.Bettas;
import teamfusion.bettas.client.model.BettaFishModel;
import teamfusion.bettas.client.renderer.BettaFishRenderer;
import teamfusion.bettas.init.BettasBlocks;
import teamfusion.bettas.init.BettasEntities;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Bettas.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(BettasEntities.BETTA_FISH.get(), BettaFishRenderer::new);
        ItemBlockRenderTypes.setRenderLayer(BettasBlocks.DRIED_LEAVES.get(), RenderType.cutout());

        ForgeHooksClient.registerLayerDefinition(BettaFishRenderer.MODEL_LAYER, BettaFishModel::createLayerDefinition);
    }
}