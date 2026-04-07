package com.mahmut.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class SugarCaneMixin {

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private void useBoneMealOnSugarCane(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if (state.isOf(Blocks.SUGAR_CANE)) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (itemStack.isOf(Items.BONE_MEAL)) {
                
                if (!world.isClient) {
                    BlockPos topPos = pos;
                    while (world.getBlockState(topPos.up()).isOf(Blocks.SUGAR_CANE)) {
                        topPos = topPos.up();
                    }

                    if (world.getBlockState(topPos.up()).isAir() && (topPos.getY() - pos.getY() < 2)) {
                        world.setBlockState(topPos.up(), Blocks.SUGAR_CANE.getDefaultState());
                        if (!player.isCreative()) {
                            itemStack.decrement(1);
                        }
                        world.syncWorldEvent(2005, topPos.up(), 0); 
                        cir.setReturnValue(ActionResult.SUCCESS);
                    }
                } else {
                    cir.setReturnValue(ActionResult.SUCCESS);
                }
            }
        }
    }
}
