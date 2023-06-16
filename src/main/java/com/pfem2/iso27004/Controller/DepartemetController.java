package com.pfem2.iso27004.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfem2.iso27004.Entity.Departement;
import com.pfem2.iso27004.Service.DepartementService;

@RestController
@RequestMapping("api/v1/depr")
@CrossOrigin("*")
public class DepartemetController {
    private final DepartementService departementService;

    public DepartemetController(DepartementService departementService) {
        this.departementService = departementService;
    }

    @GetMapping
    public List<Departement> getDeps() {
        return this.departementService.getDepartements();
    }

    @PostMapping
    public Departement addDepartement(@RequestBody Departement dep) {
        return this.departementService.saveDepartement(dep);

    }

    @DeleteMapping(path = "{DepId}")
    public void removeDepartement(@PathVariable("DepId") Long DepId) {
        this.departementService.delDepartement(DepId);
    }
}
