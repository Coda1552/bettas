package mod.teamdraco.bettas;

import mod.teamdraco.bettas.entity.BettaFishEntity;
import mod.teamdraco.bettas.init.BettasBlocks;
import mod.teamdraco.bettas.init.BettasEntities;
import mod.teamdraco.bettas.init.BettasFeatures;
import mod.teamdraco.bettas.init.BettasItems;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.List;
import java.util.Random;

@Mod(Bettas.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Bettas.MOD_ID)
public class Bettas {
    public static final String MOD_ID = "bettas";
    public static Bettas instance;

    public Bettas() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::registerCommon);

        BettasItems.ITEMS.register(bus);
        BettasBlocks.BLOCKS.register(bus);
        BettasEntities.ENTITIES.register(bus);
        BettasFeatures.FEATURES.register(bus);

        bus.addGenericListener(EntityType.class, BettasEntities::init);

        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerBiomes(BiomeLoadingEvent event) {
        Biome.Climate climate = event.getClimate();
        switch (event.getCategory()) {
            case SWAMP:
                event.getSpawns().getSpawner(EntityClassification.WATER_CREATURE).add(new MobSpawnInfo.Spawners(BettasEntities.BETTA_FISH.get(), 1, 1, 2));event.getGeneration().getFeatures(GenerationStage.Decoration.SURFACE_STRUCTURES).add(() -> BettasFeatures.MOSS_BALL.get().configured(new FeatureSpreadConfig(5)).decorated(Features.Placements.TOP_SOLID_HEIGHTMAP_SQUARE).chance(16));
                break;
        }
    }

    private void registerCommon(FMLCommonSetupEvent event) {
        registerEntityAttributes();
        EntitySpawnPlacementRegistry.register(BettasEntities.BETTA_FISH.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BettaFishEntity::checkFishSpawnRules);
    }

    private void registerEntityAttributes() {
        GlobalEntityTypeAttributes.put(BettasEntities.BETTA_FISH.get(), BettaFishEntity.createAttributes().build());
    }

    //Thanks to BlueDuck for the help with the loot table code :)
    @Mod.EventBusSubscriber(modid = Bettas.MOD_ID)
    public static class LootEvents {
        @SubscribeEvent
        public static void onLootLoad(LootTableLoadEvent event) throws IllegalAccessException {
            ResourceLocation name = event.getName();
            if (name.equals(LootTables.FISHING)) {
                LootPool pool = event.getTable().getPool("main");
                if (pool != null) {
                    addEntry(pool, getInjectEntry(new ResourceLocation("bettas:inject/fishing"), 10, 1));
                }
            }
        }
    }

    private static LootEntry getInjectEntry(ResourceLocation location, int weight, int quality) {
        return TableLootEntry.lootTableReference(location).setWeight(weight).setQuality(quality).build();
    }

    private static void addEntry(LootPool pool, LootEntry entry) throws IllegalAccessException {
        List<LootEntry> lootEntries = (List<LootEntry>) ObfuscationReflectionHelper.findField(LootPool.class, "entries").get(pool);
        if (lootEntries.stream().anyMatch(e -> e == entry)) {
            throw new RuntimeException("Attempted to add a duplicate entry to pool: " + entry);
        }
        lootEntries.add(entry);
    }
}