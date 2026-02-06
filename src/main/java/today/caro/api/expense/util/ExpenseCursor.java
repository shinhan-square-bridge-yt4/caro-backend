package today.caro.api.expense.util;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;

public record ExpenseCursor(
    LocalDate date,
    Long id
) {

    private static final String DELIMITER = "_";

    public String encode() {
        String raw = date.toString() + DELIMITER + id;

        return Base64.getUrlEncoder().withoutPadding().encodeToString(
            raw.getBytes(StandardCharsets.UTF_8)
        );
    }

    public static ExpenseCursor decode(String encoded) {
        if (encoded == null || encoded.isBlank()) {
            return null;
        }

        try {
            String raw = new String(
                Base64.getUrlDecoder().decode(encoded),
                StandardCharsets.UTF_8
            );

            String[] parts = raw.split(DELIMITER);

            return new ExpenseCursor(
                LocalDate.parse(parts[0]),
                Long.parseLong(parts[1])
            );
        } catch (Exception e) {
            return null;
        }
    }

}
