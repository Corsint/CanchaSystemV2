package com.example.CanchaSystem.service;
import com.example.CanchaSystem.exception.cancha.CanchaNameAlreadyExistsException;
import com.example.CanchaSystem.exception.cancha.CanchaNotFoundException;
import com.example.CanchaSystem.exception.cancha.IllegalCanchaAddressException;
import com.example.CanchaSystem.exception.cancha.NoCanchasException;
import com.example.CanchaSystem.exception.canchaBrand.NoCanchaBrandsException;
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

    public List<Cancha> getAllCanchas() throws NoCanchasException {
        List<Cancha> canchas =  canchaRepository.findAll();
        if(canchas.isEmpty()){
            throw new NoCanchasException("Todavia no hay Canchas registradas");
        }

        return canchas;
    }

    public List<Cancha> getCanchasByOwner(String username) throws NoCanchasException {
        List<Cancha> canchas = canchaRepository.findByBrandOwnerUsername(username);
        if (canchas.isEmpty()) {
            throw new NoCanchasException("El dueño no tiene canchas registradas");
        }
        return canchas;
    }

    public List<Cancha> getCanchasByOwnerId(Long id) throws NoCanchasException {
        List<Cancha> canchas = canchaRepository.findByBrandOwnerId(id);
        if (canchas.isEmpty()) {
            throw new NoCanchasException("El dueño no tiene canchas");
        }

        return canchas;
    }

    public List<Cancha> getCanchasByBrandId(Long id) throws NoCanchasException {
        List<Cancha> canchas = canchaRepository.findByBrandId(id);
        if (canchas.isEmpty()) {
            throw new NoCanchasException("La marca no tiene canchas");
        }

        return canchas;
    }

    public void updateCancha(Cancha cancha) throws CanchaNotFoundException {
        if(canchaRepository.existsById(cancha.getId())){
            canchaRepository.save(cancha);
        } else
            throw new CanchaNotFoundException("Cancha no encontrada");
    }

    public void updateOwnerCancha(Cancha cancha, String username) throws CanchaNotFoundException {
        if (canchaRepository.existsByIdAndBrandOwnerUsername(cancha.getId(), username)) {
            canchaRepository.save(cancha);
        } else throw new CanchaNotFoundException("Cancha de dueño no encontrada");
    }

    public void deleteCancha(Long id) throws CanchaNotFoundException{
        if (canchaRepository.existsById(id)) {
            canchaRepository.deleteById(id);
        }else
            throw new CanchaNotFoundException("Cancha no encontrada");

    }

    public void deleteOwnerCancha(Long id, String username) throws CanchaNotFoundException {
        if (canchaRepository.existsByIdAndBrandOwnerUsername(id, username)) {
            canchaRepository.deleteById(id);
        } else throw new CanchaNotFoundException("Cancha de dueño no encontrada");
    }

    public Cancha findCanchaById(Long id) throws CanchaNotFoundException {
        return canchaRepository.findById(id).orElseThrow(()-> new CanchaNotFoundException("Cancha no encontrada"));
    }


}
