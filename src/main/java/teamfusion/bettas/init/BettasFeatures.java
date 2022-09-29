package teamfusion.bettas.init;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import teamfusion.bettas.Bettas;
import teamfusion.bettas.feature.MossBallFeature;

public class BettasFeatures {
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Bettas.MOD_ID);

	public static final RegistryObject<Feature<CountConfiguration>> MOSS_BALL = FEATURES.register("moss_ball", () -> new MossBallFeature(CountConfiguration.CODEC));

}