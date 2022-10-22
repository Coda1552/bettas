package teamfusion.bettas.init;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import teamfusion.bettas.Bettas;

public class BettasConfiguredFeatures {
	
	public static final DeferredRegister<ConfiguredFeature<?,?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Bettas.MOD_ID);

    public static final RegistryObject<ConfiguredFeature<?, ?>> CONFIGURED_MOSS_BALLS = CONFIGURED_FEATURES.register("moss_balls", () -> new ConfiguredFeature<>(BettasFeatures.MOSS_BALL.get(), new CountConfiguration(4)));
	
}
