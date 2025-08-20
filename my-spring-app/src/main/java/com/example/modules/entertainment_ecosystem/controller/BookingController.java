package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.BookingDto;
import com.example.modules.entertainment_ecosystem.model.Booking;
import com.example.modules.entertainment_ecosystem.mapper.BookingMapper;
import com.example.modules.entertainment_ecosystem.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    public BookingController(BookingService bookingService,
                                    BookingMapper bookingMapper) {
        this.bookingService = bookingService;
        this.bookingMapper = bookingMapper;
    }

    @GetMapping
    public ResponseEntity<List<BookingDto>> getAllBookings() {
        List<Booking> entities = bookingService.findAll();
        return ResponseEntity.ok(bookingMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable Long id) {
        return bookingService.findById(id)
                .map(bookingMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BookingDto> createBooking(
            @Valid @RequestBody BookingDto bookingDto,
            UriComponentsBuilder uriBuilder) {

        Booking entity = bookingMapper.toEntity(bookingDto);
        Booking saved = bookingService.save(entity);
        URI location = uriBuilder.path("/api/bookings/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(bookingMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<BookingDto> updateBooking(
                @PathVariable Long id,
                @RequestBody BookingDto bookingDto) {

                // Transformer le DTO en entity pour le service
                Booking entityToUpdate = bookingMapper.toEntity(bookingDto);

                // Appel du service update
                Booking updatedEntity = bookingService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                BookingDto updatedDto = bookingMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
                    boolean deleted = bookingService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}