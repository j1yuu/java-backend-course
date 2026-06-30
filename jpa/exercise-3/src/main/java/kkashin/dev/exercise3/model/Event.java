package kkashin.dev.exercise3.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer availableTickets;
    @OneToMany(mappedBy = "event_id")
    private Set<Booking> bookings;

    public Event() {}
    public Event(
            Long id,
            String name,
            Integer availableTickets,
            Set<Booking> bookings
    ) {
        this.id = id;
        this.name = name;
        this.availableTickets = availableTickets;
        this.bookings = bookings;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(Integer availableTickets) {
        this.availableTickets = availableTickets;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }
}
