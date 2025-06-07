package com.example.CanchaSystem.service;


import com.example.CanchaSystem.exception.cancha.CanchaNotFoundException;
import com.example.CanchaSystem.exception.client.ClientNotFoundException;
import com.example.CanchaSystem.exception.reservation.IllegalReservationDateException;
import com.example.CanchaSystem.exception.reservation.NoReservationsException;
import com.example.CanchaSystem.exception.reservation.ReservationNotFoundException;
import com.example.CanchaSystem.model.Cancha;
import com.example.CanchaSystem.model.Client;
import com.example.CanchaSystem.model.Reservation;
import com.example.CanchaSystem.model.ReservationStatus;
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
import java.util.Optional;
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
        if(!reservationRespository.existsBymatchDateAndCanchaId(reservation.getMatchDate(),reservation.getCancha().getId()))
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
        Reservation existing = reservationRespository.findById(reservation.getId())
                .orElseThrow(() ->  new ReservationNotFoundException("Reserva no encontrada"));
        existing.setStatus(reservation.getStatus());
        existing.setMatchDate(reservation.getMatchDate());
        return reservationRespository.save(existing);

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

    public List<Reservation> findReservationsByClient(String username) throws NoReservationsException {
        Optional<Client> clientOpt = clientRepository.findByUsername(username);

        if (clientOpt.isEmpty()) {
            throw new ClientNotFoundException("Cliente no encontrado");
        }

        Client client = clientOpt.get();

        List<Reservation> reservations = reservationRespository.findByClientId(client.getId());

        if (!reservations.isEmpty()) {
            return reservations;
        } else {
            throw new NoReservationsException("Todavia no hay reservas hechas por el cliente");
        }

    }

    public List<Reservation> findReservationsByCanchaId(Long canchaId){
        return reservationRespository.findByCanchaId(canchaId);
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
                .findByCanchaIdAndMatchDateBetweenAndStatus(canchaId,from,until,ReservationStatus.PENDING);

        Set<LocalTime> reservedHours = reservations.stream()
                .map(reservation -> reservation.getMatchDate().toLocalTime())
                .collect(Collectors.toSet());

        return allHours.stream()
                .filter(availableHour -> !reservedHours.contains(availableHour))
                .collect(Collectors.toList());
    }

    public Reservation completeReservation(Reservation reservation){
        if(reservationRespository.existsById(reservation.getId())){
            reservation.setStatus(ReservationStatus.COMPLETED);
            return reservationRespository.save(reservation);
        }else
            throw new ReservationNotFoundException("Reserva no encontrada");
    }

    public Reservation cancelReservation(Reservation reservation){
        if(reservationRespository.existsById(reservation.getId())){
            reservation.setStatus(ReservationStatus.CANCELED);
            return reservationRespository.save(reservation);
        }else
            throw new ReservationNotFoundException("Reserva no encontrada");
    }

    public List<Reservation> getReservationsByBrandId(Long brandId) throws NoReservationsException{
        List<Reservation> reservations = reservationRespository.findAllByBrandId(brandId);
        if (reservations.isEmpty())
            throw new NoReservationsException("Todavía no hay reseñas hechas");
        return reservations;
    }

    public List<Reservation> getReservationsByOwnerId(Long ownerId) throws NoReservationsException{
        List<Reservation> reservations = reservationRespository.findAllByOwnerId(ownerId);
        if (reservations.isEmpty())
            throw new NoReservationsException("Todavía no hay reseñas hechas");
        return reservations;
    }

}


