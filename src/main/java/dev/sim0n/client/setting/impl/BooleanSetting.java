package dev.sim0n.client.setting.impl;

import dev.sim0n.client.setting.Setting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class BooleanSetting implements Setting<Boolean> {
    private final String name, description;

    private Boolean value;
}
