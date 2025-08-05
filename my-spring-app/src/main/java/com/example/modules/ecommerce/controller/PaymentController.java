package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.model.Payment;
import com.example.modules.ecommerce.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
return ResponseEntity.ok(paymentService.save(payment));
}

@PutMapping("/{id}")
public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody Payment payment) {
return ResponseEntity.ok(paymentService.save(payment));
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
    paymentService.deleteById(id);
    return ResponseEntity.noContent().build();
    }
    }
