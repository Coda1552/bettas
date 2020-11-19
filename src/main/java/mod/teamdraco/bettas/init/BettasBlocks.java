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

public class BettasBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Bettas.MOD_ID);

    public static final RegistryObject<MossBallBlock> MOSS_BALL = BLOCKS.register("moss_ball", () -> new MossBallBlock(Block.Properties.create(Material.MISCELLANEOUS, MaterialColor.GREEN).sound(SoundType.WET_GRASS).hardnessAndResistance(0).notSolid()));
    public static final RegistryObject<Block> MOSS_BALL_BLOCK = BLOCKS.register("moss_ball_block", () -> new Block(Block.Properties.create(Material.ORGANIC, MaterialColor.GREEN).sound(SoundType.WET_GRASS).hardnessAndResistance(0.5f)));
    public static final RegistryObject<DriedLeavesBlock> DRIED_LEAVES = BLOCKS.register("dried_leaves", () -> new DriedLeavesBlock(Block.Properties.create(Material.MISCELLANEOUS, MaterialColor.ADOBE).sound(SoundType.PLANT).hardnessAndResistance(0).notSolid()));

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> object = BLOCKS.register(name, block);
        BettasItems.ITEMS.register(name, () -> new BlockItem(object.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
        return object;
    }
}
