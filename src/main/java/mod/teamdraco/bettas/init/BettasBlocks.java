package mod.teamdraco.bettas.init;

import mod.teamdraco.bettas.Bettas;
import mod.teamdraco.bettas.block.DriedLeavesBlock;
import mod.teamdraco.bettas.block.MossBallBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import net.minecraft.block.AbstractBlock;

public class BettasBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Bettas.MOD_ID);

    public static final RegistryObject<MossBallBlock> MOSS_BALL = BLOCKS.register("moss_ball", () -> new MossBallBlock(AbstractBlock.Properties.of(Material.DECORATION, MaterialColor.COLOR_GREEN).sound(SoundType.WET_GRASS).strength(0).noOcclusion()));
    public static final RegistryObject<Block> MOSS_BALL_BLOCK = BLOCKS.register("moss_ball_block", () -> new Block(AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_GREEN).sound(SoundType.WET_GRASS).strength(0.5f)));
    public static final RegistryObject<DriedLeavesBlock> DRIED_LEAVES = BLOCKS.register("dried_leaves", () -> new DriedLeavesBlock(AbstractBlock.Properties.of(Material.DECORATION, MaterialColor.COLOR_ORANGE).sound(SoundType.GRASS).strength(0).noOcclusion()));

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> object = BLOCKS.register(name, block);
        BettasItems.ITEMS.register(name, () -> new BlockItem(object.get(), new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)));
        return object;
    }
}
