package kkashin.dev.model.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record AccountProperties(
        @Value("${account.default-amount}") int defaultAmount,
        @Value("${account.transfer-commission}") double transferCommission
) {}
