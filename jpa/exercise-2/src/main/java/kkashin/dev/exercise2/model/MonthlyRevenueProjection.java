package kkashin.dev.exercise2.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface MonthlyRevenueProjection {
    Integer getYear();
    Integer getMonth();
    BigDecimal getRevenue();
}
