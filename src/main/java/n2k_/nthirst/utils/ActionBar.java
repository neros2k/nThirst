package n2k_.nthirst.utils;
import me.clip.placeholderapi.PlaceholderAPI;
import n2k_.nthirst.base.IEngine;
import n2k_.nthirst.base.model.main.MainModel;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
public final class ActionBar {
    @Contract(pure = true) @NotNull
    public static String get(@NotNull String FORMAT, @NotNull IEngine ENGINE) {
        String STR = ENGINE.getInteractor().getMainConfig().AB_MESSAGE.replace("{level}", FORMAT)
                                                                      .replace("{line}", ActionBar.getLine(ENGINE));
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            STR = PlaceholderAPI.setPlaceholders(ENGINE.getPlayer(), STR);
        }
        return STR;
    }

    @NotNull
    public static String getLine(@NotNull IEngine ENGINE) {
        StringBuilder BUILDER = new StringBuilder();
        MainModel MAIN_MODEL = ENGINE.getInteractor().getMainConfig();
        int SIZE = MAIN_MODEL.LINE_SIZE;
        int A = ENGINE.getWaterLevel().intValue();
        int B = ENGINE.getInteractor().getMainConfig().DEFAULT_WATER_LEVEL-A;
        for(int I = 0; I<A/SIZE; I++) {
            BUILDER.append(MAIN_MODEL.LINE_FILLED);
        }
        for(int I = 0; I<B/SIZE; I++) {
            BUILDER.append(MAIN_MODEL.LINE_EMPTY);
        }
        return BUILDER.toString();
    }
}
