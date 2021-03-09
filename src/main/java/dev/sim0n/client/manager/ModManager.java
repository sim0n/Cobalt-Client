package dev.sim0n.client.manager;

import dev.sim0n.client.mod.Mod;
import dev.sim0n.client.mod.impl.combat.AutoClicker;
import dev.sim0n.client.mod.impl.combat.Reach;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ModManager {
    private static final List<Class<? extends Mod>> MOD_CLASSES = Arrays.asList(
            AutoClicker.class, Reach.class
    );

    @Getter
    private final Set<Mod> mods = new HashSet<>();

    public ModManager() {
        MOD_CLASSES.forEach(clazz -> {
            try {
                mods.add(clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        mods.forEach(Mod::toggle);
    }

    public <T extends Mod> T getMod(Class<T> clazz) {
        return (T) mods.stream()
                .filter(mod -> mod.getClass() == clazz)
                .findFirst()
                .orElse(null);
    }
}
