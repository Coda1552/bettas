package codyhuh.bettas.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import codyhuh.bettas.Bettas;
import codyhuh.bettas.client.model.BettaFishModel;
import codyhuh.bettas.client.renderer.BettaFishRenderer;
import codyhuh.bettas.init.BettasEntities;

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