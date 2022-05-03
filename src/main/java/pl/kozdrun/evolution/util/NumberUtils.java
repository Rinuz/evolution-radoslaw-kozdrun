package pl.kozdrun.evolution.util;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class NumberUtils {

    public static boolean greaterEqualsThanZero(BigDecimal number) {
        return number != null && number.compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean lowerThanZero(BigDecimal number) {
        return number != null && number.compareTo(BigDecimal.ZERO) < 0;
    }

    public static boolean isBetween(BigDecimal number, BigDecimal min, BigDecimal max) {
        return number.compareTo(min) >= 0 && number.compareTo(max) <= 0;
    }
}