package dev.sim0n.client.setting.impl;

import dev.sim0n.client.setting.Setting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class IntegerSetting implements Setting<Integer> {
    private final String name, description;

    private final Integer min, max;

    private Integer value;
}
