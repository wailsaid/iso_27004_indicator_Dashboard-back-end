package com.pfem2.iso27004.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfem2.iso27004.Entity.Indicator;

@Repository
public interface IndicatorRepository extends JpaRepository<Indicator, Long> {

}
