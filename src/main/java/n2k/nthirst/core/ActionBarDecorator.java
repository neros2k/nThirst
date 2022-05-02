package n2k.nthirst.core;
import n2k.nthirst.base.IEngine;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
public class ActionBarDecorator {
    @Contract(pure = true) @NotNull
    public static String get(@NotNull String FORMAT, @NotNull IEngine ENGINE) {
        return ENGINE.getInteractor().getConfig().AB_MESSAGE.replace("%w", FORMAT);
    }
}
