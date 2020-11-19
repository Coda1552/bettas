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
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

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

        EntitySpawnPlacementRegistry.register(BettasEntities.BETTA_FISH, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractFishEntity::func_223363_b);

        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerBiomes(BiomeLoadingEvent event) {
        Biome.Climate climate = event.getClimate();
        switch (event.getCategory()) {
            case SWAMP:
                float temperature = climate.temperature;
                if (climate.temperature >= 0.5f) {
                    event.getSpawns().getSpawner(EntityClassification.WATER_CREATURE).add(new MobSpawnInfo.Spawners(BettasEntities.BETTA_FISH, 30, 1, 2));
                    event.getGeneration().getFeatures(GenerationStage.Decoration.SURFACE_STRUCTURES).add(() -> BettasFeatures.MOSS_BALL.get().withConfiguration(new FeatureSpreadConfig(5)).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT).chance(16));
                }
                break;
        }
    }

    private void registerCommon(FMLCommonSetupEvent event) {
        registerEntityAttributes();
    }

    private void registerEntityAttributes() {
        GlobalEntityTypeAttributes.put(BettasEntities.BETTA_FISH, BettaFishEntity.func_234176_m_().create());
    }
}