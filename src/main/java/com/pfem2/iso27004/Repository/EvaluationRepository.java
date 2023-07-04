package com.pfem2.iso27004.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pfem2.iso27004.Entity.Evaluation;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    @Query("SELECT e FROM Evaluation e WHERE e.evaluationDate = (SELECT MAX(e2.evaluationDate) FROM Evaluation e2 WHERE e2.indicator.id = e.indicator.id)")
    List<Evaluation> findLatestEvaluations();

    @Query("SELECT e FROM Evaluation e WHERE e.indicator.id = :indicatorId ORDER BY e.evaluationDate DESC")
    List<Evaluation> findAllEvaluationsByIndicatorId(@Param("indicatorId") Long indicatorId);

    @Query("SELECT e FROM Evaluation e WHERE e.indicator.id = :indicatorId AND e.evaluationDate = (SELECT MAX(e2.evaluationDate) FROM Evaluation e2 WHERE e2.indicator.id = :indicatorId)")
    Evaluation findLatestEvaluationByIndicatorId(@Param("indicatorId") Long indicatorId);

    @Modifying
    @Query("DELETE FROM Evaluation e WHERE e.indicator.id = :indicatorId")
    void deleteAllEvaluationsByIndicatorId(@Param("indicatorId") Long indicatorId);

    @Query("SELECT e FROM Evaluation e WHERE e.indicator.checked=true and e.evaluationDate = (SELECT MAX(e2.evaluationDate) FROM Evaluation e2 WHERE e2.indicator.id = e.indicator.id)")
    List<Evaluation> findDashboardIndicator();

    @Query("DELETE FROM Evaluation e WHERE e.resp.id = :uid")
    void deletebyResp(@Param("uid") Long id);

    //// @Query("SELECT e FROM Evaluation e WHERE e.nextEvaluationDate =
    //// DATE_ADD(CURRENT_DATE, :days, 'DAY')")
    // List<Evaluation> nextEvaluationByDay(@Param("days") int days);

}
