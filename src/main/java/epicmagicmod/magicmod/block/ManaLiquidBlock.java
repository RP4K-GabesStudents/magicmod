package epicmagicmod.magicmod.block;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.function.Supplier;

public class ManaLiquidBlock extends LiquidBlock {

    public final MobEffectInstance[] EFFECTS;
    public final int MANA_CHANGE;

    public ManaLiquidBlock(Supplier<? extends FlowingFluid> p_54694_, Properties p_54695_, int MANA_CHANGE, MobEffectInstance [] effects) {
        super(p_54694_, p_54695_);
        EFFECTS = effects;
        this.MANA_CHANGE = MANA_CHANGE;
    }
}
