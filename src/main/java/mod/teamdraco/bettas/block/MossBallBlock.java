package mod.teamdraco.bettas.block;

import mod.teamdraco.bettas.init.BettasBlocks;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class MossBallBlock extends BushBlock implements IWaterLoggable, IGrowable {
    public static final IntegerProperty BALLS = BlockStateProperties.PICKLES_1_4;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape ONE_SHAPE = Block.makeCuboidShape(0, 0, 0, 16, 7, 16);
    protected static final VoxelShape TWO_SHAPE = Block.makeCuboidShape(0, 0, 0, 16, 7, 16);
    protected static final VoxelShape THREE_SHAPE = Block.makeCuboidShape(0, 0, 0, 16, 7, 16);
    protected static final VoxelShape FOUR_SHAPE = Block.makeCuboidShape(0, 0, 0, 16, 8, 16);

    public MossBallBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(BALLS, Integer.valueOf(1)).with(WATERLOGGED, Boolean.valueOf(true)));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = context.getWorld().getBlockState(context.getPos());
        if (blockstate.isIn(this)) {
            return blockstate.with(BALLS, Integer.valueOf(Math.min(4, blockstate.get(BALLS) + 1)));
        } else {
            FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
            boolean flag = fluidstate.getFluid() == Fluids.WATER;
            return super.getStateForPlacement(context).with(WATERLOGGED, Boolean.valueOf(flag));
        }
    }

    public static boolean isInBadEnvironment(BlockState p_204901_0_) {
        return !p_204901_0_.get(WATERLOGGED);
    }

    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return !state.getCollisionShape(worldIn, pos).project(Direction.UP).isEmpty() || state.isSolidSide(worldIn, pos, Direction.UP);
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.down();
        return this.isValidGround(worldIn.getBlockState(blockpos), worldIn, blockpos);
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (!stateIn.isValidPosition(worldIn, currentPos)) {
            return Blocks.AIR.getDefaultState();
        } else {
            if (stateIn.get(WATERLOGGED)) {
                worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
            }

            return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        }
    }

    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return useContext.getItem().getItem() == this.asItem() && state.get(BALLS) < 4 ? true : super.isReplaceable(state, useContext);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(BALLS)) {
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
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BALLS, WATERLOGGED);
    }

    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        if (!isInBadEnvironment(state) && worldIn.getBlockState(pos.down()).getBlock() == BettasBlocks.MOSS_BALL_BLOCK.get()) {
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
                        if (blockpos != pos && rand.nextInt(6) == 0 && worldIn.getBlockState(blockpos).isIn(Blocks.WATER)) {
                            BlockState blockstate = worldIn.getBlockState(blockpos.down());
                            if (blockstate.getBlock() == BettasBlocks.MOSS_BALL_BLOCK.get()) {
                                worldIn.setBlockState(blockpos, BettasBlocks.MOSS_BALL.get().getDefaultState().with(BALLS, Integer.valueOf(rand.nextInt(4) + 1)), 3);
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

            worldIn.setBlockState(pos, state.with(BALLS, Integer.valueOf(4)), 2);
        }

    }
}
