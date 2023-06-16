package com.pfem2.iso27004.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfem2.iso27004.Entity.Departement;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Long> {

}
