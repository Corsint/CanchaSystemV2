package com.example.CanchaSystem.service;

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
import com.example.CanchaSystem.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Review insertReview(Review review) {
                        return reviewRepository.save(review);
    }

    public List<Review> getAllReviews() throws NoReviewsException {
        if(!reviewRepository.findAll().isEmpty()){
            return reviewRepository.findAll();
        }else
            throw new NoReviewsException("Todavia no hay reseñas hechas");


    }

    public void updateReview(Review review) throws ReviewNotFoundException {
        if(reviewRepository.existsById(review.getId())){
            reviewRepository.save(review);

        }else
            throw new ReviewNotFoundException("Reseña no encontrada");


    }

    public void deleteReview(Long id) throws ReviewNotFoundException{
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
        }else
            throw new ReviewNotFoundException("Reseña no encontrada");

    }

    public Review findReviewById(Long id) throws ReviewNotFoundException {
        return reviewRepository.findById(id).orElseThrow(()-> new ReviewNotFoundException("Reseña no encontrada"));
    }
}
