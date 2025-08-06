package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.model.Payment;
import com.example.modules.ecommerce.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

private final PaymentService paymentService;

public PaymentController(PaymentService paymentService) {
this.paymentService = paymentService;
}

@GetMapping
public ResponseEntity<List<Payment>> getAllPayments() {
return ResponseEntity.ok(paymentService.findAll());
}

@GetMapping("/{id}")
public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
return paymentService.findById(id)
.map(ResponseEntity::ok)
.orElse(ResponseEntity.notFound().build());
}

@PostMapping
public ResponseEntity<Payment> createPayment(
@Valid @RequestBody Payment payment,
UriComponentsBuilder uriBuilder) {

Payment saved = paymentService.save(payment);
URI location = uriBuilder.path("/api/payments/{id}")
.buildAndExpand(saved.getId()).toUri();

return ResponseEntity.created(location).body(saved);
}

@PutMapping("/{id}")
public ResponseEntity<Payment> updatePayment(
@PathVariable Long id,
@Valid @RequestBody Payment paymentRequest) {

try {
Payment updated = paymentService.update(id, paymentRequest);
return ResponseEntity.ok(updated);
} catch (RuntimeException e) {
return ResponseEntity.notFound().build();
}
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
    paymentService.deleteById(id);
    return ResponseEntity.noContent().build();
    }
    }
