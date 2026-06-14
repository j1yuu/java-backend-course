package kkashin.dev.model;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name="user_name")
    private String name;

    @Column(name="user_email")
    private String email;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

    public User() {}
    public User(String name, String email, Profile profile) {
        this.name = name;
        this.email = email;
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", " +
                "name=" + name + ", " +
                "email=" + email + ", " +
                "profile=" + profile + "}";
    }
}
