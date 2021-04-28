/*
 * Copyright (C) Evergreen [2020 - 2021]
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/lgpl-3.0.en.html
 *
 * If you have any questions or concerns, please create
 * an issue on the github page that can be found here
 * https://github.com/Evergreen-Client/EvergreenHUD
 *
 * If you have a private concern, please contact
 * isXander @ business.isxander@gmail.com
 */

package co.uk.isxander.evergreenhud.command;

import club.sk1er.mods.core.gui.notification.Notifications;
import co.uk.isxander.evergreenhud.gui.screens.impl.GuiMain;
import co.uk.isxander.xanderlib.XanderLib;
import co.uk.isxander.xanderlib.utils.Multithreading;
import co.uk.isxander.xanderlib.utils.Version;
import co.uk.isxander.evergreenhud.EvergreenHUD;
import co.uk.isxander.evergreenhud.update.UpdateChecker;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EvergreenHudCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "evergreenhud";
    }

    @Override
    public List<String> getCommandAliases() {
        return new ArrayList<>(Arrays.asList(
                "evergreenhud",
                "hud",
                "evergreen",
                "hudmod"
        ));
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/evergreenhud [update|check|version]";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("update") || args[0].equalsIgnoreCase("check")) {
                Multithreading.runAsync(() -> {
                    if (EvergreenHUD.getInstance().isDevelopment()) {
                        Notifications.INSTANCE.pushNotification("EvergreenHUD", "You are on a development version. There are no updates available.");
                    } else {
                        Version latest = UpdateChecker.getLatestVersion();
                        if (latest.newerThan(EvergreenHUD.PARSED_VERSION)) {
                            EvergreenHUD.notifyUpdate(latest);
                        } else {
                            Notifications.INSTANCE.pushNotification("EvergreenHUD", "There are no updates available.");
                        }
                    }
                });
            } else if (args[0].equalsIgnoreCase("version")) {
                Notifications.INSTANCE.pushNotification("EvergreenHUD", "You are running on version " + EvergreenHUD.MOD_VERSION + "\nIf you want to check for updates, use \"/evergreenhud update\"");
            }
        } else {
            XanderLib.getInstance().getGuiHandler().open(new GuiMain());
        }
    }

}