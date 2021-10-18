package mod.teamdraco.bettas.init;

import mod.teamdraco.bettas.Bettas;
import mod.teamdraco.bettas.entity.BettaFishEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BettasEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Bettas.MOD_ID);

    public static final RegistryObject<EntityType<BettaFishEntity>> BETTA_FISH = ENTITIES.register("betta_fish", () -> EntityType.Builder.<BettaFishEntity>of(BettaFishEntity::new, EntityClassification.WATER_AMBIENT).sized(0.3f, 0.3f).build(new ResourceLocation(Bettas.MOD_ID, "betta_fish").toString()));

    public static void init(RegistryEvent.Register<EntityType<?>> event) {
    }
}