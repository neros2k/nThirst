package n2k_.nthirst;
import n2k_.nthirst.base.IInteractor;
import n2k_.nthirst.base.model.ConfigModel;
import n2k_.nthirst.core.Interactor;
import n2k_.nthirst.items.ClearWaterItem;
import n2k_.nthirst.utils.PAPIExpansion;
import neros2k.jcapi.JCApi;
import neros2k.jcapi.JSONConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.List;
import java.util.Optional;
public final class nThirst extends JavaPlugin {
    private final IInteractor INTERACTOR;
    private JSONConfig<ConfigModel> JSON_CONFIG;
    public nThirst() {
        this.INTERACTOR = new Interactor(this);
    }
    @Override
    public void onEnable() {
        List.of("nThirst v1.0 by n2k_",
                "GitHub: https://github.com/neros2k",
                "Discord: n2k_#9665").forEach(this.getLogger()::info);
        if(Bukkit.getPluginManager().isPluginEnabled("JSONConfigAPI")) {
            Optional<JSONConfig<ConfigModel>> JSON_CONFIG_OPT = JCApi.getNew(
                    this, ConfigModel.class, "config.json");
            if(JSON_CONFIG_OPT.isPresent()) {
                this.JSON_CONFIG = JSON_CONFIG_OPT.get();
                this.JSON_CONFIG.reload();
            } else return;
            this.INTERACTOR.init();
            ConfigModel MODEL = JSON_CONFIG.getJson();
            NamespacedKey KEY = NamespacedKey.fromString("clear_water");
            assert KEY != null;
            ItemStack ITEM = new ClearWaterItem(MODEL);
            RecipeChoice.MaterialChoice MATERIAL_CHOICE = new RecipeChoice.MaterialChoice(Material.POTION);
            FurnaceRecipe RECIPE = new FurnaceRecipe(
                    KEY, ITEM, MATERIAL_CHOICE,
                    MODEL.CLEAR_WATER_EXPERIENCE,
                    MODEL.CLEAR_WATER_COOKING_TIME);
            this.getServer().addRecipe(RECIPE);
        }
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            this.getLogger().info("Hooked into PlaceholderAPI");
            new PAPIExpansion(this.INTERACTOR).register();
        }
    }
    public JSONConfig<ConfigModel> getJsonConfig() {
        assert this.JSON_CONFIG != null;
        return this.JSON_CONFIG;
    }
}
