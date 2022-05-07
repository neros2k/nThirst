package n2k.nthirst.core.presenter;
import n2k.nthirst.base.APresenter;
import n2k.nthirst.base.IInteractor;
import n2k.nthirst.base.model.ConfigModel;
import n2k.nthirst.items.DirtyWaterItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
public class WaterPresenter extends APresenter implements Listener {
    public WaterPresenter(IInteractor INTERACTOR) {
        super(INTERACTOR);
    }
    @Override
    public void init() {
        JavaPlugin PLUGIN = this.getInteractor().getPlugin();
        PLUGIN.getServer().getPluginManager().registerEvents(this, PLUGIN);
    }
    @EventHandler
    public void onPlayerInteract(@NotNull PlayerInteractEvent EVENT) {
        IInteractor INTERACTOR = super.getInteractor();
        ConfigModel MODEL = INTERACTOR.getConfig();
        Block BLOCK = EVENT.getClickedBlock();
        assert BLOCK != null;
        if(!MODEL.CLEAR_WATER.ENABLED) return;
        if(BLOCK.getType() == Material.WATER || BLOCK.isLiquid()) {
            EVENT.getPlayer().getInventory().addItem(new DirtyWaterItem(MODEL));
        }
    }
}
