package n2k.nthirst.items;
import n2k.nthirst.base.model.ConfigModel;
import n2k.nthirst.base.model.WaterModel;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.jetbrains.annotations.NotNull;
public class ClearWaterItem extends ItemStack {
    public ClearWaterItem(@NotNull ConfigModel MODEL) {
        super(Material.POTION);
        WaterModel WATER_MODEL = MODEL.CLEAR_WATER;
        PotionMeta META = (PotionMeta) this.getItemMeta();
        assert META != null;
        META.setColor(Color.fromBGR(WATER_MODEL.COLOR[2],
                                    WATER_MODEL.COLOR[1],
                                    WATER_MODEL.COLOR[0]));
        META.setDisplayName(WATER_MODEL.NAME);

    }
}
