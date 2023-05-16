package com.pfem2.iso27004.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pfem2.iso27004.Entity.Indicator;

@Repository
public interface IndicatorRepository extends JpaRepository<Indicator, Long> {
    @Query("SELECT i FROM Indicator i LEFT JOIN Evaluation e ON i.id = e.indicator.id WHERE e.id IS NULL")
    List<Indicator> findIndicatorsWithoutEvaluation();

}
