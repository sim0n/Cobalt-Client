package dev.sim0n.client.command.impl;

import dev.sim0n.client.command.Command;
import dev.sim0n.client.mod.Mod;
import dev.sim0n.client.setting.Setting;
import dev.sim0n.client.setting.impl.BooleanSetting;
import dev.sim0n.client.setting.impl.DoubleSetting;
import dev.sim0n.client.setting.impl.IntegerSetting;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;
import java.util.Optional;

public class ModCommand extends Command {
    private final Mod mod;

    public ModCommand(Mod mod) {
        super(mod.getName());

        this.mod = mod;
    }

    @Override
    public void handle(List<String> args) {
        int length = args.size();

        if (length != 2) {
            StringBuilder sb = new StringBuilder();

            sb.append(" \n").append(String.format(EnumChatFormatting.BLUE + "%s", mod.getName())).append("\n").append(EnumChatFormatting.GRAY);

            mod.getSettings().forEach(setting -> {
                sb.append(EnumChatFormatting.GRAY).append(setting.getName()).append(" - ").append(setting.getDescription()).append("\n");

                sb.append(EnumChatFormatting.BLUE).append(setting.getValue()).append(" ").append(EnumChatFormatting.GRAY);
                if (setting instanceof DoubleSetting) {
                    DoubleSetting doubleSetting = (DoubleSetting) setting;

                    sb.append(doubleSetting.getMin()).append("-").append(doubleSetting.getMax());
                } else if (setting instanceof IntegerSetting) {
                    IntegerSetting doubleSetting = (IntegerSetting) setting;

                    sb.append(doubleSetting.getMin()).append("-").append(doubleSetting.getMax());
                }

                sb.append("\n");
            });

            sendMessage(sb.toString());
        } else {
            String settingName = args.get(0);

            Optional<Setting> setting = mod.getSettings().stream()
                    .filter(s -> s.getName().replace(" ", "").equalsIgnoreCase(settingName))
                    .findFirst();

            if (setting.isPresent()) {
                Setting handle = setting.get();

                String value = args.get(1);

                try {
                    if (handle instanceof BooleanSetting) {
                        handle.setValue(Boolean.parseBoolean(value));
                    } else if (handle instanceof DoubleSetting) {
                        handle.setValue(Double.parseDouble(value));
                    } else if (handle instanceof IntegerSetting) {
                        handle.setValue(Integer.parseInt(value));
                    }

                    sendMessage(String.format("%sSet %s to %s", EnumChatFormatting.GREEN, settingName, value));
                } catch (Exception e) {
                    sendMessage(String.format("%sUnable to set %s to %s",EnumChatFormatting.RED, handle.getName(), value));
                }

            } else {
                sendMessage(String.format("%sCouldn't find a setting by the name of %s",EnumChatFormatting.RED, settingName));
            }
        }
    }
}
