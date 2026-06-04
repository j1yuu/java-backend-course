package kkashin.dev;

import java.util.UUID;

public class Task {
    private final String name;
    private final long duration;
    private final String uuid = UUID.randomUUID().toString();

    public Task() {
        this.name = "Task";
        this.duration = 60L;
    }

    public Task(String name, long duration) {
        this.name = name;
        this.duration = duration;
    }


    public String getName() {
        return name;
    }

    public long getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Task{\n" +
                "name=" + name + ",\n"
                + "duration=" + duration + ",\n"
                + "id=" + uuid + "\n"
                + "}";
    }
}
