package kkashin.dev.exercise3.service;

import kkashin.dev.exercise3.model.Booking;
import kkashin.dev.exercise3.model.BookingDto;
import kkashin.dev.exercise3.model.BookingStatus;
import kkashin.dev.exercise3.model.Event;
import kkashin.dev.exercise3.repository.BookingRepository;
import kkashin.dev.exercise3.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookingService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Transactional
    public void bookTickets(BookingDto bookingDto) {
        Event event = eventRepository.findEventWithLock(bookingDto.eventId())
                .orElseThrow(() -> new NoSuchElementException("Event with given id was not found: %s".formatted(bookingDto.eventId())));

        if (event.getAvailableTickets() < bookingDto.quantity())
            throw new IllegalStateException(
                    "Tickets available is less than requested. Requested: %s; Available: %s".formatted(
                            bookingDto.quantity(),
                            event.getAvailableTickets()
                    )
            );

        event.setAvailableTickets(event.getAvailableTickets() - bookingDto.quantity());

        bookingRepository.save(new Booking(
                null,
                bookingDto.customerEmail(),
                bookingDto.quantity(),
                BookingStatus.PENDING,
                event
        ));
    }

    @Transactional
    public void cancelBooking(Long bookingId) {
        if (bookingId == null)
            throw new IllegalArgumentException("Booking id must not be null");

        var booking = bookingRepository.findBookingWithLock(bookingId).orElseThrow(
                () -> new NoSuchElementException("Booking with given id was not found: %s".formatted(bookingId))
        );

        if (booking.getStatus().equals(BookingStatus.CANCELLED))
            throw new IllegalStateException("Booking should be not cancelled already");

        booking.setStatus(BookingStatus.CANCELLED);

        var event = eventRepository.findEventWithLock(booking.getEvent().getId())
                .orElseThrow(() -> new NoSuchElementException(
                        "Event with given id was not found: %s".formatted(booking.getEvent().getId())
                ));

        event.setAvailableTickets(event.getAvailableTickets() + booking.getTicketsCount());
    }

    @Transactional
    public Event getEventWithLock(Long eventId) {
        if (eventId == null)
            throw new IllegalArgumentException("Event id must not be null");

        return eventRepository.findEventWithLock(eventId)
                .orElseThrow(() -> new NoSuchElementException("Event with given id was not found: %s".formatted(eventId)));
    }

    @Transactional
    public void processBatchBookings(List<BookingDto> bookings) {
        for (BookingDto bookingDto : bookings) {
            bookTickets(bookingDto);
        }
    }
}
