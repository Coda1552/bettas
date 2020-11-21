package mod.teamdraco.bettas.init;

import mod.teamdraco.bettas.Bettas;
import mod.teamdraco.bettas.entity.BettaFishEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BettasEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Bettas.MOD_ID);

    public static final EntityType<BettaFishEntity> BETTA_FISH = create("betta_fish", BettaFishEntity::new, EntityClassification.WATER_CREATURE, 0.3f, 0.3f, 0x613d2a, 0x2dceff);

    private static <T extends CreatureEntity> EntityType<T> create(String name, EntityType.IFactory<T> factory, EntityClassification classification, float width, float height, int pri, int sec) {
        final Item.Properties properties = new Item.Properties().group(ItemGroup.MISC);
        EntityType<T> type = create(name, factory, classification, width, height);
        BettasItems.ITEMS.register(name + "_spawn_egg", () -> new SpawnEggItem(type, pri, sec, properties));
        return type;
    }

    private static <T extends Entity> EntityType<T> create(String name, EntityType.IFactory<T> factory, EntityClassification classification, float width, float height) {
        EntityType<T> type = EntityType.Builder.create(factory, classification).size(width, height).setTrackingRange(128).build(name);
        BettasEntities.ENTITIES.register(name, () -> type);
        return type;
    }

    public static void init(RegistryEvent.Register<EntityType<?>> event) {
    }
}