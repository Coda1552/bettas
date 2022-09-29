package teamfusion.bettas.biome;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import teamfusion.bettas.init.BettaBiomeModifiers;
import teamfusion.bettas.init.BettasEntities;
import teamfusion.bettas.init.BettasPlacedFeatures;

public class BettaBiomeModifier implements BiomeModifier {
	public static final BettaBiomeModifier INSTANCE = new BettaBiomeModifier();

	@Override
	public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
		if (phase == Phase.ADD) {
			if (biome.is(BiomeTags.IS_OVERWORLD) && biome.is(Tags.Biomes.IS_SWAMP)) {
				builder.getMobSpawnSettings().addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(BettasEntities.BETTA_FISH.get(), 1, 1, 2));
				builder.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BettasPlacedFeatures.PLACED_MOSS_BALLS.getHolder().get());
			}
		}
	}

	@Override
	public Codec<? extends BiomeModifier> codec() {
		return BettaBiomeModifiers.BETTA_BIOME_MODIFIER_TYPE.get();
	}
}