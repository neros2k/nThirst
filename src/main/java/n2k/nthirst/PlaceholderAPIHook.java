package n2k.nthirst;
import me.clip.placeholderapi.PlaceholderHook;
import n2k.nthirst.base.IInteractor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
public class PlaceholderAPIHook extends PlaceholderHook {
    private final IInteractor INTERACTOR;
    public PlaceholderAPIHook(IInteractor INTERACTOR) {
        this.INTERACTOR = INTERACTOR;
    }
    @Override
    public String onPlaceholderRequest(Player PLAYER, @NotNull String identifier) {
        if(identifier.equals("water_level")) {
            Float LEVEL = INTERACTOR.getEngine(PLAYER.getName()).getWaterLevel();
            String FORMAT = "%.1f";
            return String.valueOf(String.format(FORMAT, LEVEL));
        }
        return null;
    }
}
