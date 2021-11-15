package teamdraco.bettas;

import net.minecraft.data.worldgen.Features;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import teamdraco.bettas.entity.BettaFishEntity;
import teamdraco.bettas.init.BettasBlocks;
import teamdraco.bettas.init.BettasEntities;
import teamdraco.bettas.init.BettasFeatures;
import teamdraco.bettas.init.BettasItems;

import java.util.List;

@Mod(Bettas.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Bettas.MOD_ID)
public class Bettas {
    public static final String MOD_ID = "bettas";

    public Bettas() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::registerCommon);

        BettasItems.ITEMS.register(bus);
        BettasBlocks.BLOCKS.register(bus);
        BettasEntities.ENTITIES.register(bus);
        BettasFeatures.FEATURES.register(bus);

        bus.addListener(this::registerEntityAttributes);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerBiomes(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.BiomeCategory.SWAMP) {
            event.getSpawns().getSpawner(MobCategory.WATER_CREATURE).add(new MobSpawnSettings.SpawnerData(BettasEntities.BETTA_FISH.get(), 1, 1, 2));
            event.getGeneration().getFeatures(GenerationStep.Decoration.SURFACE_STRUCTURES).add(() -> BettasFeatures.MOSS_BALL.get().configured(new CountConfiguration(5)).decorated(Features.Decorators.TOP_SOLID_HEIGHTMAP_SQUARE).count(1));
        }
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

    private static void addEntry(LootPool pool, LootPoolEntryContainer entry) throws IllegalAccessException {
        List<LootPoolEntryContainer> lootEntries = (List<LootPoolEntryContainer>) ObfuscationReflectionHelper.findField(LootPool.class, "entries").get(pool);
        if (lootEntries.stream().anyMatch(e -> e == entry)) {
            throw new RuntimeException("Attempted to add a duplicate entry to pool: " + entry);
        }
        lootEntries.add(entry);
    }
}