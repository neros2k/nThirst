package n2k.nthirst.core.presenter;
import n2k.nthirst.base.APresenter;
import n2k.nthirst.base.IInteractor;
import n2k.nthirst.base.model.ConfigModel;
import n2k.nthirst.nThirst;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.List;
public final class CommandPresenter extends APresenter implements CommandExecutor {
    public CommandPresenter(IInteractor INTERACTOR) {
        super(INTERACTOR);
    }
    @Override
    public void init() {
        PluginCommand COMMAND = super.getInteractor().getPlugin().getCommand("nthirst");
        assert COMMAND != null;
        COMMAND.setExecutor(this);
    }
    @Override
    public boolean onCommand(@NotNull CommandSender SENDER, @NotNull Command COMMAND, @NotNull String STR, @NotNull String @NotNull [] ARGS) {
        ConfigModel MODEL = this.getInteractor().getConfig();
        if(ARGS.length == 0 || ARGS[0].equals("help")) {
            List.of(MODEL.MESSAGES.HELP_MESSAGE).forEach(SENDER::sendMessage);
            return true;
        }
        if(ARGS[0].equals("reload")) {
            if(notEnoughPermission("nthirst.command.reload", SENDER)) {
                SENDER.sendMessage(MODEL.MESSAGES.PERM_ERR);
                return true;
            }
            ((nThirst) this.getInteractor().getPlugin()).getJsonConfig().reload();
            SENDER.sendMessage(MODEL.MESSAGES.RELOAD_CMD);
            return true;
        }
        if(ARGS[0].equals("set")) {
            if(notEnoughPermission("nthirst.command.set", SENDER)) {
                SENDER.sendMessage(MODEL.MESSAGES.PERM_ERR);
                return true;
            }
            if(ARGS.length == 3) {
                Player PLAYER = this.getPlayerByName(ARGS[1]);
                float VALUE = Float.parseFloat(ARGS[2]);
                if(PLAYER != null && !Float.isNaN(VALUE)) {
                    this.getInteractor().getEngine(PLAYER.getName()).setWaterLevel(VALUE);
                    SENDER.sendMessage(MODEL.MESSAGES.SET_CMD.replace("{value}", Float.toString(VALUE))
                                                             .replace("{player}", PLAYER.getName()));
                    return true;
                }
            }
            SENDER.sendMessage(MODEL.MESSAGES.SET_CMD_ERR);
            return true;
        }
        if(ARGS[0].equals("get")) {
            if(notEnoughPermission("nthirst.command.get", SENDER)) {
                SENDER.sendMessage(MODEL.MESSAGES.PERM_ERR);
                return true;
            }
            if(ARGS.length == 2) {
                Player PLAYER = this.getPlayerByName(ARGS[1]);
                if(PLAYER != null) {
                    Float VALUE = this.getInteractor().getEngine(PLAYER.getName()).getWaterLevel();
                    String MESSAGE = MODEL.MESSAGES.GET_CMD.replace("{value}", VALUE.toString())
                                                           .replace("{player}", PLAYER.getName());
                    SENDER.sendMessage(MESSAGE);
                    return true;
                }
            }
            SENDER.sendMessage(MODEL.MESSAGES.GET_CMD_ERR);
            return true;
        }
        SENDER.sendMessage(MODEL.MESSAGES.UNK_CMD_ERR);
        return false;
    }
    private Player getPlayerByName(String NAME) {
        return this.getInteractor().getPlugin().getServer().getPlayer(NAME);
    }
    private boolean notEnoughPermission(String NAME, @NotNull CommandSender SENDER) {
        return !(SENDER.hasPermission("nthirst.admin") && SENDER.hasPermission(NAME));
    }
}