package n2k.nthirst;
import me.clip.placeholderapi.PlaceholderAPI;
import n2k.nthirst.base.IEngine;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
public class ActionBar {
    @Contract(pure = true) @NotNull
    public static String get(@NotNull String STR, @NotNull IEngine ENGINE) {
        STR = STR.replace("{level}", STR);
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            STR = PlaceholderAPI.setPlaceholders(ENGINE.getPlayer(), STR);
        }
        return STR;
    }
}
