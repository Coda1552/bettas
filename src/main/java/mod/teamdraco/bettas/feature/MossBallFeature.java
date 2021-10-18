package mod.teamdraco.bettas.feature;

import com.mojang.serialization.Codec;
import mod.teamdraco.bettas.init.BettasBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SeaPickleBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;

import java.util.Random;

public class MossBallFeature extends Feature<FeatureSpreadConfig> {
    public MossBallFeature(Codec<FeatureSpreadConfig> p_i231987_1_) {
        super(p_i231987_1_);
    }

    public boolean place(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, FeatureSpreadConfig config) {
        int i = 0;
        int j = config.count().sample(rand);

        for(int k = 0; k < j; ++k) {
            int l = rand.nextInt(8) - rand.nextInt(8);
            int i1 = rand.nextInt(8) - rand.nextInt(8);
            int j1 = reader.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX() + l, pos.getZ() + i1);
            BlockPos blockpos = new BlockPos(pos.getX() + l, j1, pos.getZ() + i1);
            BlockState blockstate = BettasBlocks.MOSS_BALL.get().defaultBlockState().setValue(SeaPickleBlock.PICKLES, Integer.valueOf(rand.nextInt(4) + 1));
            if (reader.getBlockState(blockpos).is(Blocks.WATER) && blockstate.canSurvive(reader, blockpos)) {
                reader.setBlock(blockpos, blockstate, 2);
                ++i;
            }
        }

        return i > 0;
    }
}
