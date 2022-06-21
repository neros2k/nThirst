package n2k_.nthirst;
import n2k_.jcapi.JCApi;
import n2k_.jcapi.JSONConfig;
import n2k_.nthirst.base.IInteractor;
import n2k_.nthirst.base.model.main.MainModel;
import n2k_.nthirst.base.model.main.ItemsModel;
import n2k_.nthirst.base.model.main.MessagesModel;
import n2k_.nthirst.base.model.main.ModifiersModel;
import n2k_.nthirst.core.Interactor;
import n2k_.nthirst.items.ClearWaterItem;
import n2k_.nthirst.utils.PAPIExpansion;
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
    private JSONConfig<MainModel> MAIN_CONFIG;
    private JSONConfig<ItemsModel> ITEMS_CONFIG;
    private JSONConfig<MessagesModel> MESSAGES_CONFIG;
    private JSONConfig<ModifiersModel> MODIFIERS_CONFIG;
    public nThirst() {
        this.INTERACTOR = new Interactor(this);
    }
    @Override
    public void onEnable() {
        List.of("nThirst v1.0 by n2k_",
                "GitHub: https://github.com/neros2k",
                "Discord: n2k_#9665").forEach(this.getLogger()::info);
        if(Bukkit.getPluginManager().isPluginEnabled("JSONConfigAPI")) {
            Optional<JSONConfig<MainModel>> MAIN_CONFIG_OPT = JCApi.getNew(
                    this, MainModel.class, "main_config.json");
            Optional<JSONConfig<ItemsModel>> ITEMS_CONFIG_OPT = JCApi.getNew(
                    this, ItemsModel.class, "items_config.json");
            Optional<JSONConfig<MessagesModel>> MESSAGES_CONFIG_OPT = JCApi.getNew(
                    this, MessagesModel.class, "messages_config.json");
            Optional<JSONConfig<ModifiersModel>> MODIFIERS_CONFIG_OPT = JCApi.getNew(
                    this, ModifiersModel.class, "modifiers_config.json");
            if(MAIN_CONFIG_OPT.isPresent() && ITEMS_CONFIG_OPT.isPresent() &&
               MESSAGES_CONFIG_OPT.isPresent() && MODIFIERS_CONFIG_OPT.isPresent()) {
                this.MAIN_CONFIG = MAIN_CONFIG_OPT.get().reload();
                this.ITEMS_CONFIG = ITEMS_CONFIG_OPT.get().reload();
                this.MESSAGES_CONFIG = MESSAGES_CONFIG_OPT.get().reload();
                this.MODIFIERS_CONFIG = MODIFIERS_CONFIG_OPT.get().reload();
            } else return;
            MainModel MODEL = this.MAIN_CONFIG.getJson();
            NamespacedKey KEY = NamespacedKey.fromString("clear_water");
            assert KEY != null;
            ItemStack ITEM = new ClearWaterItem(this.ITEMS_CONFIG.getJson());
            RecipeChoice.MaterialChoice MATERIAL_CHOICE = new RecipeChoice.MaterialChoice(Material.POTION);
            FurnaceRecipe RECIPE = new FurnaceRecipe(
                    KEY, ITEM, MATERIAL_CHOICE,
                    MODEL.CLEAR_WATER_EXPERIENCE,
                    MODEL.CLEAR_WATER_COOKING_TIME);
            this.getServer().addRecipe(RECIPE);
            // О́тче наш, И́же еси́ на небесе́х!
            // Да святи́тся имя Твое́,
            // Да прии́дет Ца́рствие Твое́,
            // Да бу́дет во́ля Твоя,
            // Я́ко на небеси́ и на земли́.
            // Хлеб наш насу́щный даждь нам днесь;
            // И оста́ви нам до́лги наша,
            // Якоже и мы оставля́ем должнико́м нашим;
            // И не введи́ нас во искуше́ние,
            // Но изба́ви нас от лука́ваго.
            this.INTERACTOR.init();
        }
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            this.getLogger().info("Hooked into PlaceholderAPI");
            new PAPIExpansion(this.INTERACTOR).register();
        }
    }
    public JSONConfig<MainModel> getMainConfig() {
        assert this.MAIN_CONFIG != null;
        return this.MAIN_CONFIG;
    }
    public JSONConfig<ItemsModel> getItemsConfig() {
        assert this.ITEMS_CONFIG != null;
        return this.ITEMS_CONFIG;
    }
    public JSONConfig<MessagesModel> getMessagesConfig() {
        assert this.MESSAGES_CONFIG != null;
        return this.MESSAGES_CONFIG;
    }
    public JSONConfig<ModifiersModel> getModifiersConfig() {
        assert this.MODIFIERS_CONFIG != null;
        return this.MODIFIERS_CONFIG;
    }
}
