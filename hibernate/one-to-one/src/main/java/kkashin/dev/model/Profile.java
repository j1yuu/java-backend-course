package kkashin.dev.model;

import jakarta.persistence.*;

@Entity
@Table(name="profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name="profile_bio")
    private String bio;

    @OneToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    public Profile() {}
    public Profile(String bio, User user) {
        this.bio = bio;
        this.user = user;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Profile{id=" + id + ", " +
                "bio=" + bio + "}";
    }
}
