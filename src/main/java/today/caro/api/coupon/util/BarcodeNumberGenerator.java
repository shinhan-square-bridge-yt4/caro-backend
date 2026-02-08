package today.caro.api.coupon.util;

import java.util.concurrent.ThreadLocalRandom;

public final class BarcodeNumberGenerator {

    private BarcodeNumberGenerator() {}

    public static String generate() {
        long number = ThreadLocalRandom.current().nextLong(1_000_000_000_000L, 10_000_000_000_000L);
        return String.valueOf(number);
    }

}
