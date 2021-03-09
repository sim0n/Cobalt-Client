package dev.sim0n.client.util;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;

@UtilityClass
public class MathUtil {
    public final SecureRandom RANDOM = new SecureRandom();
}
