package n2k_.nthirst.core.presenter;
import n2k_.nthirst.base.APresenter;
import n2k_.nthirst.base.IInteractor;
import n2k_.nthirst.base.model.main.ItemsModel;
import n2k_.nthirst.items.DirtyWaterItem;
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
        ItemsModel ITEMS_MODEL = INTERACTOR.getItemsConfig();
        Block BLOCK = EVENT.getClickedBlock();
        assert BLOCK != null;
        if(!ITEMS_MODEL.CLEAR_WATER.ENABLED) return;
        if(BLOCK.getType() == Material.WATER || BLOCK.isLiquid()) {
            EVENT.getPlayer().getInventory().addItem(new DirtyWaterItem(ITEMS_MODEL));
        }
    }
}
