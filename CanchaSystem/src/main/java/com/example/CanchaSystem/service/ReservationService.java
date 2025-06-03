package com.example.CanchaSystem.service;


import com.example.CanchaSystem.exception.cancha.CanchaNotFoundException;
import com.example.CanchaSystem.exception.reservation.IllegalReservationDateException;
import com.example.CanchaSystem.exception.reservation.NoReservationsException;
import com.example.CanchaSystem.exception.reservation.ReservationNotFoundException;
import com.example.CanchaSystem.model.Cancha;
import com.example.CanchaSystem.model.Reservation;
import com.example.CanchaSystem.repository.CanchaRepository;
import com.example.CanchaSystem.repository.ClientRepository;
import com.example.CanchaSystem.repository.ReservationRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRespository reservationRespository;

    @Autowired
    private CanchaRepository canchaRepository;

    @Autowired
    private ClientRepository clientRepository;


    public Reservation insertReservation(Reservation reservation)
            throws IllegalReservationDateException {
        if(!reservationRespository.existsBymatchDate(reservation.getMatchDate())) // pensar cancha_id
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

    public Reservation updateReservation(Reservation reservation) throws ReservationNotFoundException {
        if(reservationRespository.existsById(reservation.getId())){
           return reservationRespository.save(reservation);
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

    public List<LocalTime> getAvailableHours(Long canchaId, LocalDate day)throws CanchaNotFoundException{
        Cancha canchaAux = canchaRepository.findById(canchaId)
                .orElseThrow(() -> new CanchaNotFoundException("Cancha no encontrada"));

        LocalTime firstHour = canchaAux.getOpeningHour();
        List<LocalTime> allHours = new ArrayList<>();

        while (firstHour.isBefore(canchaAux.getClosingHour())){
            allHours.add(firstHour);
            firstHour = firstHour.plusHours(1);
        }

        LocalDateTime from = day.atTime(canchaAux.getOpeningHour());
        LocalDateTime until = day.atTime(canchaAux.getClosingHour());

        List<Reservation> reservations = reservationRespository
                .findByCanchaIdAndMatchDateBetween(canchaId,from,until)
                .get();

        Set<LocalTime> reservedHours = reservations.stream()
                .map(reservation -> reservation.getMatchDate().toLocalTime())
                .collect(Collectors.toSet());

        return allHours.stream()
                .filter(availableHour -> !reservedHours.contains(availableHour))
                .collect(Collectors.toList());
    }

}


