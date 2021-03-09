package dev.sim0n.client.setting.impl;

import dev.sim0n.client.setting.Setting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class DoubleSetting implements Setting<Double> {
    private final String name, description;

    private final Double min, max;

    private Double value;
}
