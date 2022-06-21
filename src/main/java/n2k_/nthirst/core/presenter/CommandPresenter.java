package n2k_.nthirst.core.presenter;
import n2k_.nthirst.base.AbstractPresenter;
import n2k_.nthirst.base.IEngine;
import n2k_.nthirst.base.IInteractor;
import n2k_.nthirst.base.model.main.MessagesModel;
import n2k_.nthirst.base.modifier.EModifierType;
import n2k_.nthirst.base.modifier.Modifier;
import n2k_.nthirst.nThirst;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.List;
public final class CommandPresenter extends AbstractPresenter implements CommandExecutor {
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
        MessagesModel MESSAGES_MODEL = this.getInteractor().getMessagesConfig();
        if(ARGS.length == 0 || ARGS[0].equals("help")) {
            List.of(MESSAGES_MODEL.HELP_MESSAGE).forEach(SENDER::sendMessage);
            return true;
        }
        if(ARGS[0].equals("reload")) {
            if(notEnoughPermission("nthirst.command.reload", SENDER)) {
                SENDER.sendMessage(MESSAGES_MODEL.PERM_ERR);
                return true;
            }
            ((nThirst) this.getInteractor().getPlugin()).getMainConfig().reload();
            SENDER.sendMessage(MESSAGES_MODEL.RELOAD_CMD);
            return true;
        }
        if(ARGS[0].equals("set")) {
            if(notEnoughPermission("nthirst.command.set", SENDER)) {
                SENDER.sendMessage(MESSAGES_MODEL.PERM_ERR);
                return true;
            }
            if(ARGS.length == 3) {
                Player PLAYER = this.getPlayerByName(ARGS[1]);
                float VALUE = Float.parseFloat(ARGS[2]);
                if(PLAYER != null && !Float.isNaN(VALUE)) {
                    IEngine ENGINE = this.getInteractor().getEngine(PLAYER.getName());
                    ENGINE.addModifier(
                            new Modifier(
                                    EModifierType.SET,
                                    ARG_ENGINE -> (VALUE - ENGINE.getWaterLevel())/20,
                                    ARG_ENGINE -> 20L,
                                    ARG_ENGINE -> false
                            )
                    );
                    SENDER.sendMessage(MESSAGES_MODEL.SET_CMD.replace("{value}", Float.toString(VALUE))
                                                             .replace("{player}", PLAYER.getName()));
                    return true;
                }
            }
            SENDER.sendMessage(MESSAGES_MODEL.SET_CMD_ERR);
            return true;
        }
        if(ARGS[0].equals("get")) {
            if(notEnoughPermission("nthirst.command.get", SENDER)) {
                SENDER.sendMessage(MESSAGES_MODEL.PERM_ERR);
                return true;
            }
            if(ARGS.length == 2) {
                Player PLAYER = this.getPlayerByName(ARGS[1]);
                if(PLAYER != null) {
                    Float VALUE = this.getInteractor().getEngine(PLAYER.getName()).getWaterLevel();
                    String MESSAGE = MESSAGES_MODEL.GET_CMD.replace("{value}", VALUE.toString())
                                                           .replace("{player}", PLAYER.getName());
                    SENDER.sendMessage(MESSAGE);
                    return true;
                }
            }
            SENDER.sendMessage(MESSAGES_MODEL.GET_CMD_ERR);
            return true;
        }
        SENDER.sendMessage(MESSAGES_MODEL.UNK_CMD_ERR);
        return false;
    }
    private Player getPlayerByName(String NAME) {
        return this.getInteractor().getPlugin().getServer().getPlayer(NAME);
    }
    private boolean notEnoughPermission(String NAME, @NotNull CommandSender SENDER) {
        return !(SENDER.hasPermission("nthirst.admin") && SENDER.hasPermission(NAME));
    }
}