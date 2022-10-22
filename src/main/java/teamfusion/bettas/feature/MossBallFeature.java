package teamfusion.bettas.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import teamfusion.bettas.init.BettasBlocks;

import java.util.Random;

public class MossBallFeature extends Feature<CountConfiguration> {

    public MossBallFeature(Codec<CountConfiguration> p_i231987_1_) {
        super(p_i231987_1_);
    }

    public boolean place(FeaturePlaceContext<CountConfiguration> context) {
        WorldGenLevel reader = context.level();
        Random rand = context.random();
        CountConfiguration config = context.config();
        BlockPos pos = context.origin();

        int i = 0;
        int j = config.count().sample(rand);

        for(int k = 0; k < j; ++k) {
            int l = rand.nextInt(8) - rand.nextInt(8);
            int i1 = rand.nextInt(8) - rand.nextInt(8);
            int j1 = reader.getHeight(Heightmap.Types.OCEAN_FLOOR, pos.getX() + l, pos.getZ() + i1);
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
