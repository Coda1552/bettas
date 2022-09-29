package teamfusion.bettas.init;

import com.mojang.serialization.Codec;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import teamfusion.bettas.Bettas;
import teamfusion.bettas.biome.BettaBiomeModifier;

public class BettaBiomeModifiers {
	public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Bettas.MOD_ID);

	public static final RegistryObject<Codec<BettaBiomeModifier>> BETTA_BIOME_MODIFIER_TYPE = BIOME_MODIFIER_SERIALIZERS.register("betta_biome_modifier", () -> Codec.unit(BettaBiomeModifier.INSTANCE));

}
