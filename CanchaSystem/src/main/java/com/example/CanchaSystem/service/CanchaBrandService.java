package com.example.CanchaSystem.service;

import com.example.CanchaSystem.exception.cancha.CanchaNotFoundException;
import com.example.CanchaSystem.exception.cancha.IllegalCanchaAddressException;
import com.example.CanchaSystem.exception.canchaBrand.CanchaBrandNameAlreadyExistsException;
import com.example.CanchaSystem.exception.canchaBrand.CanchaBrandNotFoundException;
import com.example.CanchaSystem.exception.canchaBrand.NoCanchaBrandsException;
import com.example.CanchaSystem.model.Cancha;
import com.example.CanchaSystem.model.CanchaBrand;
import com.example.CanchaSystem.repository.CanchaBrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CanchaBrandService {

    @Autowired
    private CanchaBrandRepository canchaBrandRepository;

    public CanchaBrand insertCanchaBrand(CanchaBrand canchaBrand) throws CanchaBrandNameAlreadyExistsException {
        if (!canchaBrandRepository.existsByBrandName(canchaBrand.getBrandName())) {
            return canchaBrandRepository.save(canchaBrand);
        } else throw new CanchaBrandNameAlreadyExistsException("El nombre de la Marca ya existe");
    }

    public List<CanchaBrand> getAllCanchaBrands() throws NoCanchaBrandsException {
        List<CanchaBrand> brands = canchaBrandRepository.findAll();
        if (brands.isEmpty())
            throw new NoCanchaBrandsException("Todavia no hay Marcas registradas");
        return brands;
    }

    public CanchaBrand updateCanchaBrand(CanchaBrand canchaBrand) throws CanchaBrandNotFoundException {
        if (canchaBrandRepository.existsById(canchaBrand.getId())){
            return canchaBrandRepository.save(canchaBrand);

        } else throw new CanchaBrandNotFoundException("Marca no encontrada");
    }

    public void deleteCanchaBrand(Long id) throws CanchaNotFoundException {
        if (canchaBrandRepository.existsById(id)) {
            canchaBrandRepository.deleteById(id);
        } else throw new CanchaNotFoundException("Marca no encontrada");
    }

    public CanchaBrand findCanchaBrandById(Long id) throws CanchaBrandNotFoundException {
        return canchaBrandRepository.findById(id).orElseThrow(()-> new CanchaBrandNotFoundException("Marca no encontrada"));
    }
}
