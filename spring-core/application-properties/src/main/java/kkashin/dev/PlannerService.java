package kkashin.dev;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PlannerService {
    private final Boolean enabled;
    private final Long defaultDuration;
    private final Integer batchSize;

    public PlannerService(
            PlannerProperties plannerProperties,
            @Value("${planner.enabled}") Boolean enabled
    ) {
        this.enabled = enabled;
        this.defaultDuration = plannerProperties.getDefaultDuration();
        this.batchSize = plannerProperties.getBatchSize();
    }

    public void printConfig() {
        System.out.println("Planner enabled: " + enabled
                + ", defaultDuration: " + defaultDuration
                + ", batchSize: " + batchSize);
    }
}
