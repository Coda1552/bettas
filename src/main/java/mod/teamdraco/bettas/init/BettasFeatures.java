package mod.teamdraco.bettas.init;

import mod.teamdraco.bettas.Bettas;
import mod.teamdraco.bettas.feature.MossBallFeature;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.SeaPickleFeature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BettasFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Bettas.MOD_ID);

    public static final RegistryObject<Feature<FeatureSpreadConfig>> MOSS_BALL = FEATURES.register("moss_ball", () -> new MossBallFeature(FeatureSpreadConfig.CODEC));
}
