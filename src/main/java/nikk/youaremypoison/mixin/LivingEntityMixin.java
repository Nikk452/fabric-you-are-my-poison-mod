package nikk.youaremypoison.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtil;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    protected LivingEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "eatFood(Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;",
            at = @At(value = "INVOKE", target = "net/minecraft/item/ItemStack.isFood ()Z", shift = At.Shift.AFTER))
    private void injected(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> ci) {
        if(stack.isFood()){
            if(stack.getOrCreateNbt().getBoolean("effects")) {
                List<StatusEffectInstance> t = PotionUtil.getPotionEffects(stack);
                for (StatusEffectInstance statusEffectInstance : t) {
                    ((LivingEntity) (Object) this).addStatusEffect(statusEffectInstance);
                }
            }
        }
    }
}
