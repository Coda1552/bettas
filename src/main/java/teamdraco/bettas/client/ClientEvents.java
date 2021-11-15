package teamdraco.bettas.client;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.client.ForgeHooksClient;
import teamdraco.bettas.Bettas;
import teamdraco.bettas.client.model.BettaFishModel;
import teamdraco.bettas.client.renderer.BettaFishRenderer;
import teamdraco.bettas.init.BettasBlocks;
import teamdraco.bettas.init.BettasEntities;
import teamdraco.bettas.item.BettasSpawnEggItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Bettas.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(BettasEntities.BETTA_FISH.get(), BettaFishRenderer::new);
        ItemBlockRenderTypes.setRenderLayer(BettasBlocks.DRIED_LEAVES.get(), RenderType.cutout());

        ForgeHooksClient.registerLayerDefinition(BettaFishRenderer.MODEL_LAYER, BettaFishModel::createLayerDefinition);

    }

    @SubscribeEvent
    public static void itemColors(ColorHandlerEvent.Item event) {
        ItemColors handler = event.getItemColors();
        ItemColor eggColor = (stack, tintIndex) -> ((BettasSpawnEggItem) stack.getItem()).getColor(tintIndex);
        for (BettasSpawnEggItem e : BettasSpawnEggItem.UNADDED_EGGS) handler.register(eggColor, e);
    }
}
