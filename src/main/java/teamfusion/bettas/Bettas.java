package teamfusion.bettas;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import teamfusion.bettas.entity.BettaFishEntity;
import teamfusion.bettas.init.BettaBiomeModifiers;
import teamfusion.bettas.init.BettasBlocks;
import teamfusion.bettas.init.BettasConfiguredFeatures;
import teamfusion.bettas.init.BettasEntities;
import teamfusion.bettas.init.BettasFeatures;
import teamfusion.bettas.init.BettasItems;
import teamfusion.bettas.init.BettasPlacedFeatures;

import java.util.List;

@Mod(Bettas.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Bettas.MOD_ID)
public class Bettas {
    public static final String MOD_ID = "bettas";

    public Bettas() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::registerCommon);

        BettaBiomeModifiers.BIOME_MODIFIER_SERIALIZERS.register(bus);
        BettasItems.ITEMS.register(bus);
        BettasBlocks.BLOCKS.register(bus);
        BettasEntities.ENTITIES.register(bus);
        BettasFeatures.FEATURES.register(bus);
        BettasConfiguredFeatures.CONFIGURED_FEATURES.register(bus);
        BettasPlacedFeatures.PLACED_FEATURES.register(bus);

        bus.addListener(this::registerEntityAttributes);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void registerCommon(FMLCommonSetupEvent event) {
        SpawnPlacements.register(BettasEntities.BETTA_FISH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BettaFishEntity::checkBettaFishSpawnRules);
   }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(BettasEntities.BETTA_FISH.get(), BettaFishEntity.createAttributes().build());
    }

    //Thanks to BlueDuck for the help with the loot table code :)
    @Mod.EventBusSubscriber(modid = Bettas.MOD_ID)
    public static class LootEvents {
        @SuppressWarnings("unlikely-arg-type")
		@SubscribeEvent
        public static void onLootLoad(LootTableLoadEvent event) throws IllegalAccessException {
            ResourceLocation name = event.getName();
            if (name.equals(LootContextParamSets.FISHING)) {
                LootPool pool = event.getTable().getPool("main");
                addEntry(pool, getInjectEntry(new ResourceLocation("bettas:inject/fishing"), 10, 1));
            }
        }
    }

    private static LootPoolEntryContainer getInjectEntry(ResourceLocation location, int weight, int quality) {
        return LootTableReference.lootTableReference(location).setWeight(weight).setQuality(quality).build();
    }

    @SuppressWarnings("unchecked")
	private static void addEntry(LootPool pool, LootPoolEntryContainer entry) throws IllegalAccessException {
        List<LootPoolEntryContainer> lootEntries = (List<LootPoolEntryContainer>) ObfuscationReflectionHelper.findField(LootPool.class, "entries").get(pool);
        if (lootEntries.stream().anyMatch(e -> e == entry)) {
            throw new RuntimeException("Attempted to add a duplicate entry to pool: " + entry);
        }
        lootEntries.add(entry);
    }
}