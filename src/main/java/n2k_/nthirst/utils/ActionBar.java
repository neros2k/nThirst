package n2k_.nthirst.utils;
import me.clip.placeholderapi.PlaceholderAPI;
import n2k_.nthirst.base.IEngine;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
public final class ActionBar {
    @Contract(pure = true) @NotNull
    public static String get(@NotNull String FORMAT, @NotNull IEngine ENGINE) {
        String STR = ENGINE.getInteractor().getConfig().AB_MESSAGE.replace("{level}", FORMAT);
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            STR = PlaceholderAPI.setPlaceholders(ENGINE.getPlayer(), STR);
        }
        return STR;
    }
}
