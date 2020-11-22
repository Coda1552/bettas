package mod.teamdraco.bettas.init;

import mod.teamdraco.bettas.Bettas;
import mod.teamdraco.bettas.item.BettasSpawnEggItem;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class BettasItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Bettas.MOD_ID);

    public static final RegistryObject<Item> BETTA_FISH = ITEMS.register("betta_fish", () -> new Item(new Item.Properties().group(ItemGroup.FOOD).food(new Food.Builder().hunger(1).saturation(0.1F).build())));
    public static final RegistryObject<Item> BETTA_FISH_BUCKET = ITEMS.register("betta_fish_bucket", () -> new FishBucketItem(BettasEntities.BETTA_FISH, () -> Fluids.WATER, new Item.Properties().group(ItemGroup.MISC).maxStackSize(1)));
    public static final RegistryObject<Item> BLACKWATER_BOTTLE = ITEMS.register("blackwater_bottle", () -> new Item(new Item.Properties().group(ItemGroup.MISC).maxStackSize(1)));

    public static final RegistryObject<BettasSpawnEggItem> BETTA_FISH_SPAWN_EGG = ITEMS.register("betta_fish_spawn_egg", () -> new BettasSpawnEggItem(BettasEntities.BETTA_FISH, 0x613d2a, 0x2dceff, new Item.Properties().group(ItemGroup.MISC)));

    public static final RegistryObject<BlockItem> MOSS_BALL = ITEMS.register("moss_ball", () -> new BlockItem(BettasBlocks.MOSS_BALL.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<BlockItem> MOSS_BALL_BLOCK = ITEMS.register("moss_ball_block", () -> new BlockItem(BettasBlocks.MOSS_BALL_BLOCK.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<BlockItem> DRIED_LEAVES = ITEMS.register("dried_leaves", () -> new BlockItem(BettasBlocks.DRIED_LEAVES.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
}