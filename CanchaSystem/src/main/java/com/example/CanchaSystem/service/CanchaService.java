package com.example.CanchaSystem.service;
import com.example.CanchaSystem.exception.cancha.CanchaNameAlreadyExistsException;
import com.example.CanchaSystem.exception.cancha.CanchaNotFoundException;
import com.example.CanchaSystem.exception.cancha.IllegalCanchaAddressException;
import com.example.CanchaSystem.exception.cancha.NoCanchasExceptions;
import com.example.CanchaSystem.model.Cancha;
import com.example.CanchaSystem.repository.CanchaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CanchaService {

    @Autowired
    private CanchaRepository canchaRepository;

    public Cancha insertCancha(Cancha cancha) throws CanchaNameAlreadyExistsException, IllegalCanchaAddressException {
        if(!canchaRepository.existsByName(cancha.getName()))
            return canchaRepository.save(cancha);
        else throw new CanchaNameAlreadyExistsException("El nombre de la cancha ya existe");
    }

    public List<Cancha> getAllCanchas() throws NoCanchasExceptions {
        if(!canchaRepository.findAll().isEmpty()){
            return canchaRepository.findAll();
        }else
            throw new NoCanchasExceptions("Todavia no hay Canchas registradas");


    }

    public void updateCancha(Cancha cancha) throws CanchaNotFoundException {
        if(canchaRepository.existsById(cancha.getId())){
            canchaRepository.save(cancha);

        }else
            throw new CanchaNotFoundException("Cancha no encontrada");


    }

    public void deleteCancha(Long id) throws CanchaNotFoundException{
        if (canchaRepository.existsById(id)) {
            canchaRepository.deleteById(id);
        }else
            throw new CanchaNotFoundException("Cancha no encontrada");

    }

    public Cancha findCanchaById(Long id) throws CanchaNotFoundException {
        return canchaRepository.findById(id).orElseThrow(()-> new CanchaNotFoundException("Cancha no encontrada"));
    }
}
