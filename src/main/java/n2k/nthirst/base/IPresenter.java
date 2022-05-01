package n2k.nthirst.base;
import org.bukkit.event.Listener;
public interface IPresenter extends IInitializable, Listener {
    IInteractor getInteractor();
}
