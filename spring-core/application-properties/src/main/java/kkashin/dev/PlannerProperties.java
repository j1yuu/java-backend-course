package kkashin.dev;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PlannerProperties {
    @Value("${planner.default-duration}")
    private Long defaultDuration;

    @Value("${planner.batch-size}")
    private Integer batchSize;

    public Long getDefaultDuration() {
        return defaultDuration;
    }

    public Integer getBatchSize() {
        return batchSize;
    }
}
