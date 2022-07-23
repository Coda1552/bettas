package teamdraco.bettas.client;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import teamdraco.bettas.Bettas;
import teamdraco.bettas.client.model.BettaFishModel;
import teamdraco.bettas.client.renderer.BettaFishRenderer;
import teamdraco.bettas.init.BettasBlocks;
import teamdraco.bettas.init.BettasEntities;

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