package teamfusion.bettas.biome;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.ForgeRegistries;
import teamfusion.bettas.init.BettaBiomeModifiers;
import teamfusion.bettas.init.BettasEntities;

import java.util.List;

public class BettaBiomeModifier implements BiomeModifier {
	public static final BettaBiomeModifier INSTANCE = new BettaBiomeModifier();

	@Override
	public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
		if (phase == Phase.ADD) {
			if (biome.is(BiomeTags.IS_OVERWORLD) && biome.is(Tags.Biomes.IS_SWAMP)) {
				builder.getMobSpawnSettings().addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(BettasEntities.BETTA_FISH.get(), 15, 4, 6));
				//builder.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BettasPlacedFeatures.PLACED_MOSS_BALLS.getHolder().get());
			}
		}
	}

	@Override
	public Codec<? extends BiomeModifier> codec() {
		return BettaBiomeModifiers.BETTA_BIOME_MODIFIER_TYPE.get();
	}
}