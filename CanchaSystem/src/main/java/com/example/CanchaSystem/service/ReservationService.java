package com.example.CanchaSystem.service;


import com.example.CanchaSystem.exception.reservation.IllegalReservationDateException;
import com.example.CanchaSystem.exception.reservation.NoReservationsException;
import com.example.CanchaSystem.exception.reservation.ReservationNotFoundException;
import com.example.CanchaSystem.model.Reservation;
import com.example.CanchaSystem.repository.ReservationRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRespository reservationRespository;


    public Reservation insertReservation(Reservation reservation)
            throws IllegalReservationDateException {
        if(!reservationRespository.existsBymatchDate(reservation.getMatchDate()))
            return reservationRespository.save(reservation);
        else
            throw new IllegalReservationDateException("La fecha ya esta reservada");
    }

    public List<Reservation> getAllReservations() throws NoReservationsException {
        if(!reservationRespository.findAll().isEmpty()){
            return reservationRespository.findAll();
        }else
            throw new NoReservationsException("Todavia no hay reservas registradas");


    }

    public void updateReservation(Reservation reservation) throws ReservationNotFoundException {
        if(reservationRespository.existsById(reservation.getId())){
            reservationRespository.save(reservation);

        }else
            throw new ReservationNotFoundException("Reserva no encontrada");


    }

    public void deleteReservation(Long id) throws ReservationNotFoundException{
        if (reservationRespository.existsById(id)) {
            reservationRespository.deleteById(id);
        }else
            throw new ReservationNotFoundException("Reserva no encontrada");

    }

    public Reservation findReservationById(Long id) throws ReservationNotFoundException {
        return reservationRespository.findById(id).orElseThrow(()-> new ReservationNotFoundException("Reserva no encontrada"));
    }
}
