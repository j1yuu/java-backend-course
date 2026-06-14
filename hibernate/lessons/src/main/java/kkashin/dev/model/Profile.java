package kkashin.dev.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="bio")
    private String bio;

    @Column(name="last_seen_time")
    private LocalDateTime lastSeenTime;

    @OneToOne
    @JoinColumn(name="student_id", referencedColumnName = "id")
    private Student student;

    public Profile() {};
    public Profile(String bio, LocalDateTime lastSeenTime, Student student) {
        this.bio = bio;
        this.lastSeenTime = lastSeenTime;
        this.student = student;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getLastSeenTime() {
        return lastSeenTime;
    }

    public void setLastSeenTime(LocalDateTime lastSeenTime) {
        this.lastSeenTime = lastSeenTime;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
