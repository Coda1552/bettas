package teamdraco.bettas.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fmllegacy.RegistryObject;
import teamdraco.bettas.Bettas;
import teamdraco.bettas.entity.BettaFishEntity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BettasEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Bettas.MOD_ID);

    public static final RegistryObject<EntityType<BettaFishEntity>> BETTA_FISH = create("betta_fish", EntityType.Builder.of(BettaFishEntity::new, MobCategory.WATER_AMBIENT).sized(0.3f, 0.3f));

    private static <T extends Entity> RegistryObject<EntityType<T>> create(String name, EntityType.Builder<T> builder) {
        return ENTITIES.register(name, () -> builder.build(Bettas.MOD_ID + "." + name));
    }
}