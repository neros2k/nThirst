package n2k_.nthirst.utils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import n2k_.nthirst.base.IInteractor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
public final class PAPIExpansion extends PlaceholderExpansion {
    private final IInteractor INTERACTOR;
    public PAPIExpansion(IInteractor INTERACTOR) {
        this.INTERACTOR = INTERACTOR;
    }
    @Override @NotNull
    public String getIdentifier() {
        return "nthirst";
    }
    @Override @NotNull
    public String getAuthor() {
        return "n2k";
    }
    @Override @NotNull
    public String getVersion() {
        return "1.0";
    }
    @Override
    public @Nullable String onPlaceholderRequest(@NotNull Player PLAYER, @NotNull String PARAM) {
        if(PARAM.equals("water_level")) {
            Float LEVEL = INTERACTOR.getEngine(PLAYER.getName()).getWaterLevel();
            String FORMAT = "%.1f";
            return String.valueOf(String.format(FORMAT, LEVEL));
        }
        if(PARAM.equals("water_line")) {
            return ActionBar.getLine(INTERACTOR.getEngine(PLAYER.getName()));
        }
        return null;
    }
}
