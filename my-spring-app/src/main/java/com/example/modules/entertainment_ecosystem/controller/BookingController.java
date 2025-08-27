package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.BookingDto;
import com.example.modules.entertainment_ecosystem.dtosimple.BookingSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Booking;
import com.example.modules.entertainment_ecosystem.mapper.BookingMapper;
import com.example.modules.entertainment_ecosystem.service.BookingService;
import com.example.core.controller.BaseController;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Controller for managing Booking entities.
 */
@RestController
@RequestMapping("/api/bookings")
public class BookingController extends BaseController<Booking, BookingDto, BookingSimpleDto> {

    public BookingController(BookingService bookingService,
                                    BookingMapper bookingMapper) {
        super(bookingService, bookingMapper);
    }

    @GetMapping
    public ResponseEntity<Page<BookingDto>> getAllBookings(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BookingDto>> searchBookings(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Booking.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<BookingDto> createBooking(
            @Valid @RequestBody BookingDto bookingDto,
            UriComponentsBuilder uriBuilder) {

        Booking entity = mapper.toEntity(bookingDto);
        Booking saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/bookings/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<BookingDto>> createAllBookings(
            @Valid @RequestBody List<BookingDto> bookingDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Booking> entities = mapper.toEntityList(bookingDtoList);
        List<Booking> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/bookings").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDto> updateBooking(
            @PathVariable Long id,
            @Valid @RequestBody BookingDto bookingDto) {

        Booking entityToUpdate = mapper.toEntity(bookingDto);
        Booking updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        return doDelete(id);
    }
}