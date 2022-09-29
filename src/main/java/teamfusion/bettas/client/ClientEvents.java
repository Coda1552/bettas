package teamfusion.bettas.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import teamfusion.bettas.Bettas;
import teamfusion.bettas.client.model.BettaFishModel;
import teamfusion.bettas.client.renderer.BettaFishRenderer;
import teamfusion.bettas.init.BettasEntities;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Bettas.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BettaFishRenderer.MODEL_LAYER, BettaFishModel::createLayerDefinition);
    }


    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(BettasEntities.BETTA_FISH.get(), BettaFishRenderer::new);
    }
}