package teamdraco.bettas.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import teamdraco.bettas.init.BettasBlocks;

import javax.annotation.Nullable;
import java.util.Random;

public class MossBallBlock extends BushBlock implements SimpleWaterloggedBlock, BonemealableBlock {
    public static final IntegerProperty BALLS = BlockStateProperties.PICKLES;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape ONE_SHAPE = Block.box(0, 0, 0, 16, 7, 16);
    protected static final VoxelShape TWO_SHAPE = Block.box(0, 0, 0, 16, 7, 16);
    protected static final VoxelShape THREE_SHAPE = Block.box(0, 0, 0, 16, 7, 16);
    protected static final VoxelShape FOUR_SHAPE = Block.box(0, 0, 0, 16, 8, 16);

    public MossBallBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(BALLS, 1).setValue(WATERLOGGED, Boolean.TRUE));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        if (blockstate.is(this)) {
            return blockstate.setValue(BALLS, Math.min(4, blockstate.getValue(BALLS) + 1));
        } else {
            FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
            boolean flag = fluidstate.getType() == Fluids.WATER;
            return super.getStateForPlacement(context).setValue(WATERLOGGED, flag);
        }
    }

    public static boolean isInBadEnvironment(BlockState p_204901_0_) {
        return !p_204901_0_.getValue(WATERLOGGED);
    }

    protected boolean mayPlaceOn(BlockState state, LevelReader worldIn, BlockPos pos) {
        return !state.getCollisionShape(worldIn, pos).getFaceShape(Direction.UP).isEmpty() || state.isFaceSturdy(worldIn, pos, Direction.UP);
    }

    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.below();
        return this.mayPlaceOn(worldIn.getBlockState(blockpos), worldIn, blockpos);
    }

    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (!stateIn.canSurvive(worldIn, currentPos)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            if (stateIn.getValue(WATERLOGGED)) {
                worldIn.m_183324_().m_183588_(currentPos, Fluids.WATER);
            }

            return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        }
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return useContext.getItemInHand().getItem() == this.asItem() && state.getValue(BALLS) < 4 || super.canBeReplaced(state, useContext);
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(BALLS)) {
            case 1:
            default:
                return ONE_SHAPE;
            case 2:
                return TWO_SHAPE;
            case 3:
                return THREE_SHAPE;
            case 4:
                return FOUR_SHAPE;
        }
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BALLS, WATERLOGGED);
    }

    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    public void performBonemeal(ServerLevel worldIn, Random rand, BlockPos pos, BlockState state) {
        if (!isInBadEnvironment(state) && worldIn.getBlockState(pos.below()).getBlock() == BettasBlocks.MOSS_BALL_BLOCK.get()) {
            int i = 5;
            int j = 1;
            int k = 2;
            int l = 0;
            int i1 = pos.getX() - 2;
            int j1 = 0;

            for(int k1 = 0; k1 < 5; ++k1) {
                for(int l1 = 0; l1 < j; ++l1) {
                    int i2 = 2 + pos.getY() - 1;

                    for(int j2 = i2 - 2; j2 < i2; ++j2) {
                        BlockPos blockpos = new BlockPos(i1 + k1, j2, pos.getZ() - j1 + l1);
                        if (blockpos != pos && rand.nextInt(6) == 0 && worldIn.getBlockState(blockpos).is(Blocks.WATER)) {
                            BlockState blockstate = worldIn.getBlockState(blockpos.below());
                            if (blockstate.getBlock() == BettasBlocks.MOSS_BALL_BLOCK.get()) {
                                worldIn.setBlock(blockpos, BettasBlocks.MOSS_BALL.get().defaultBlockState().setValue(BALLS, Integer.valueOf(rand.nextInt(4) + 1)), 3);
                            }
                        }
                    }
                }

                if (l < 2) {
                    j += 2;
                    ++j1;
                } else {
                    j -= 2;
                    --j1;
                }

                ++l;
            }

            worldIn.setBlock(pos, state.setValue(BALLS, Integer.valueOf(4)), 2);
        }

    }
}
