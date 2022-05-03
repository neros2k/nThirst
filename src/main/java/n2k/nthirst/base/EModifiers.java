package n2k.nthirst.base;
import n2k.nthirst.base.model.ModifierModel;
import n2k.nthirst.base.model.TypeModel;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
public enum EModifiers {
    BIOMES((ENGINE, ARGS) -> {
        TypeModel[] MODEL = ENGINE.getInteractor().getConfig().MODIFIERS.BIOMES;
        AtomicReference<TypeModel> COINCIDENTAL_TYPE = new AtomicReference<>(null);
        Arrays.stream(MODEL).forEach(BIOME -> {
            if(BIOME.TYPE.equals(ARGS[0])) COINCIDENTAL_TYPE.set(BIOME);
        });
        if(COINCIDENTAL_TYPE.get() != null) {
            return new ModifierData(COINCIDENTAL_TYPE.get().VALUE, COINCIDENTAL_TYPE.get().DURATION, false);
        } else {
            return new ModifierData(0F, 0L, false);
        }
    }),
    FOOD((ENGINE, ARGS) -> {
        TypeModel[] MODEL = ENGINE.getInteractor().getConfig().MODIFIERS.FOOD;
        AtomicReference<TypeModel> COINCIDENTAL_TYPE = new AtomicReference<>(null);
        Arrays.stream(MODEL).forEach(FOOD -> {
            if(FOOD.TYPE.equals(ARGS[0])) COINCIDENTAL_TYPE.set(FOOD);
        });
        if(COINCIDENTAL_TYPE.get() != null) {
            return new ModifierData(COINCIDENTAL_TYPE.get().VALUE, COINCIDENTAL_TYPE.get().DURATION, false);
        } else {
            return new ModifierData(0F, 0L, false);
        }
    }),
    WALK((ENGINE, ARGS) -> {
        ModifierModel MODEL = ENGINE.getInteractor().getConfig().MODIFIERS.WALK;
        return new ModifierData(MODEL.VALUE, MODEL.DURATION, MODEL.PERMANENT);
    }),
    ACTION_BAR((ENGINE, ARGS) -> {
        ModifierModel MODEL = ENGINE.getInteractor().getConfig().MODIFIERS.ACTION_BAR;
        return new ModifierData(MODEL.VALUE, MODEL.DURATION, MODEL.PERMANENT);
    });
    private final IModifier MODIFIER;
    EModifiers(IModifier MODIFIER) {
        this.MODIFIER = MODIFIER;
    }
    public IModifier getModifier() {
        return this.MODIFIER;
    }
}
