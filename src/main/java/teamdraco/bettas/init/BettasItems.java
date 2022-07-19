package teamdraco.bettas.init;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.RegistryObject;
import teamdraco.bettas.Bettas;
import teamdraco.bettas.item.BettasBucketItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BettasItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Bettas.MOD_ID);

    public static final RegistryObject<Item> BETTA_FISH = ITEMS.register("betta_fish", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(1).saturationMod(0.1F).build())));
    public static final RegistryObject<Item> BETTA_FISH_BUCKET = ITEMS.register("betta_fish_bucket", () -> new BettasBucketItem(BettasEntities.BETTA_FISH, () -> Fluids.WATER, Items.BUCKET, false, new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1)));
    public static final RegistryObject<Item> BLACKWATER_BOTTLE = ITEMS.register("blackwater_bottle", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1)));

    public static final RegistryObject<Item> BETTA_FISH_SPAWN_EGG = ITEMS.register("betta_fish_spawn_egg", () -> new ForgeSpawnEggItem(BettasEntities.BETTA_FISH, 0x613d2a, 0x2dceff, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<BlockItem> MOSS_BALL = ITEMS.register("moss_ball", () -> new BlockItem(BettasBlocks.MOSS_BALL.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> MOSS_BALL_BLOCK = ITEMS.register("moss_ball_block", () -> new BlockItem(BettasBlocks.MOSS_BALL_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> DRIED_LEAVES = ITEMS.register("dried_leaves", () -> new BlockItem(BettasBlocks.DRIED_LEAVES.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
}