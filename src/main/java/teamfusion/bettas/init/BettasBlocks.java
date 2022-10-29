package teamfusion.bettas.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import teamfusion.bettas.Bettas;
import teamfusion.bettas.block.DriedLeavesBlock;
import teamfusion.bettas.block.MossBallBlock;

public class BettasBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Bettas.MOD_ID);

    public static final RegistryObject<MossBallBlock> MOSS_BALL = BLOCKS.register("moss_ball", () -> new MossBallBlock(BlockBehaviour.Properties.of(Material.DECORATION, MaterialColor.COLOR_GREEN).sound(SoundType.WET_GRASS).strength(0).noOcclusion()));
    public static final RegistryObject<Block> MOSS_BALL_BLOCK = BLOCKS.register("moss_ball_block", () -> new Block(BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.COLOR_GREEN).sound(SoundType.WET_GRASS).strength(0.5f)));
    public static final RegistryObject<DriedLeavesBlock> DRIED_LEAVES = BLOCKS.register("dried_leaves", () -> new DriedLeavesBlock(BlockBehaviour.Properties.of(Material.DECORATION, MaterialColor.COLOR_ORANGE).sound(SoundType.GRASS).strength(0).noOcclusion()));
}
