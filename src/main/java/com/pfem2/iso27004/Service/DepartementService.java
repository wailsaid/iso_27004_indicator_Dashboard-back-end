package com.pfem2.iso27004.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pfem2.iso27004.Entity.Departement;
import com.pfem2.iso27004.Repository.DepartementRepository;

@Service
public class DepartementService {

    private final DepartementRepository departementRepository;

    public DepartementService(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
    }

    public List<Departement> getDepartements() {
        return this.departementRepository.findAll();
    }

    public Departement saveDepartement(Departement dep) {
        return this.departementRepository.save(dep);
    }

    public void saveAllDepartement(List<Departement> deps) {
        this.departementRepository.saveAll(deps);
    }

    public void delDepartement(Long dep) {
        if (!this.departementRepository.existsById(dep)) {
            System.out.println("dep not exist");
        }
        Departement d = this.departementRepository.findById(dep).orElse(null);
        d.getIndicators().removeAll(d.getIndicators());
        this.departementRepository.deleteById(dep);
    }

}
