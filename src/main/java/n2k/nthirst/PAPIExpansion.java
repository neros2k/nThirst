package n2k.nthirst;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import n2k.nthirst.base.IInteractor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
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
    public String onPlaceholderRequest(@NotNull Player PLAYER, @NotNull String PARAM) {
        if(PARAM.equals("water_level")) {
            Float LEVEL = INTERACTOR.getEngine(PLAYER.getName()).getWaterLevel();
            String FORMAT = "%.1f";
            return String.valueOf(String.format(FORMAT, LEVEL));
        }
        return null;
    }
}
