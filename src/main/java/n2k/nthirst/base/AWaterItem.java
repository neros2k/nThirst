package n2k.nthirst.base;
import n2k.nthirst.base.model.WaterModel;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.Objects;
public abstract class AWaterItem extends ItemStack {
    public AWaterItem(@NotNull WaterModel WATER_MODEL) {
        super(Material.POTION);
        PotionMeta META = (PotionMeta) this.getItemMeta();
        assert META != null;
        META.setDisplayName(WATER_MODEL.NAME);
        META.setLore(List.of(WATER_MODEL.LORE));
        META.setColor(Color.fromBGR(WATER_MODEL.COLOR[2],
                                    WATER_MODEL.COLOR[1],
                                    WATER_MODEL.COLOR[0]));
        List.of(WATER_MODEL.EFFECTS).forEach(EFFECT -> META.addCustomEffect(new PotionEffect(
                Objects.requireNonNull(PotionEffectType.getByName(EFFECT.TYPE)),
                EFFECT.DURATION, EFFECT.AMPLIFIER
        ), true));
    }
}
