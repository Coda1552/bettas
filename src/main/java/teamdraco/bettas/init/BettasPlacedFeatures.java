package teamdraco.bettas.init;

import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import teamdraco.bettas.Bettas;

public class BettasPlacedFeatures {
	
	public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Bettas.MOD_ID);

	public static final RegistryObject<PlacedFeature> PLACED_MOSS_BALLS = PLACED_FEATURES.register("placed_moss_balls", () -> new PlacedFeature(BettasConfiguredFeatures.CONFIGURED_MOSS_BALLS.getHolder().orElseThrow(), VegetationPlacements.worldSurfaceSquaredWithCount(2)));
	
}
