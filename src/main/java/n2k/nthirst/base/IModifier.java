package n2k.nthirst.base;
import org.bukkit.event.Event;
public interface IModifier {
    Float getValue(IEngine ENGINE, Event EVENT);
}
