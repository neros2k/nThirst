package n2k.nthirst;
import n2k.nthirst.base.IInteractor;
import n2k.nthirst.core.Interactor;
import org.bukkit.plugin.java.JavaPlugin;
public final class nThirst extends JavaPlugin {
    private final IInteractor INTERACTOR;
    public nThirst() {
        this.INTERACTOR = new Interactor(this);
    }
    @Override
    public void onEnable() {
        this.INTERACTOR.init();
    }
}
