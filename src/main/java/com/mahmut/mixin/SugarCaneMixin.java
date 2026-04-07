package com.mahmut.mixin;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SugarCaneBlock.class)
public abstract class SugarCaneMixin extends Block implements Fertilizable {
    public SugarCaneMixin(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockPos root = pos;
        while (world.getBlockState(root.down()).isOf(Blocks.SUGAR_CANE)) {
            root = root.down();
        }
        
        for (int i = 1; i < 3; i++) {
            BlockPos target = root.up(i);
            if (world.getBlockState(target).isAir()) {
                world.setBlockState(target, Blocks.SUGAR_CANE.getDefaultState());
            }
        }
    }
}
