package com.example.CanchaSystem.controller;

import com.example.CanchaSystem.exception.client.ClientNotFoundException;
import com.example.CanchaSystem.exception.client.NoClientsException;
import com.example.CanchaSystem.exception.misc.BankAlreadyLinkedException;
import com.example.CanchaSystem.exception.misc.CellNumberAlreadyAddedException;
import com.example.CanchaSystem.exception.misc.MailAlreadyRegisteredException;
import com.example.CanchaSystem.exception.misc.UsernameAlreadyExistsException;
import com.example.CanchaSystem.exception.review.NoReviewsException;
import com.example.CanchaSystem.exception.review.ReviewNotFoundException;
import com.example.CanchaSystem.model.Client;
import com.example.CanchaSystem.model.Review;
import com.example.CanchaSystem.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/review")
@PreAuthorize("hasRole('CLIENT')")

public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/insert")
    public ResponseEntity<?> insertReview(@Validated @RequestBody Review review) {
            return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.insertReview(review));
    }


    @GetMapping("/findall")
    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN') or hasRole('CLIENT')")
    public List<Review> getReviews() {
        try {
            return reviewService.getAllReviews();
        } catch (NoReviewsException e) {
            System.err.println(e.getMessage());
            return null;
        }

    }

    @PutMapping("/update")
    public void updateReview(@RequestBody Review review) {
        try {
            reviewService.updateReview(review);
        } catch (ReviewNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteReview(@PathVariable Long id) {
        try {
            reviewService.deleteReview(id);
        } catch (ReviewNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN') or hasRole('CLIENT')")
    public Optional<Review> findReviewById(@PathVariable Long id) {
        try {
            return Optional.ofNullable(reviewService.findReviewById(id));
        } catch (ReviewNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return Optional.empty();
    }
}
