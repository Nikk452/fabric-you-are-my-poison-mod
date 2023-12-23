package nikk.youaremypoison.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(AnimalEntity.class)
public abstract class AnimalEntityMixin extends MobEntity {
    protected AnimalEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "eat(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/item/ItemStack;)V", at = @At("TAIL"))
    private void injected(PlayerEntity player, Hand hand, ItemStack stack,CallbackInfo ci) {
        if(stack.getOrCreateNbt().getBoolean("effects")) {
            List<StatusEffectInstance> t = PotionUtil.getPotionEffects(stack);
            for (StatusEffectInstance statusEffectInstance : t) {
                ((AnimalEntity) (Object) this).addStatusEffect(statusEffectInstance);
            }
        }
    }
}