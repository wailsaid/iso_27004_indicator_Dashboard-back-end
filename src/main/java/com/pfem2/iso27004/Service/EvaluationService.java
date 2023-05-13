package com.pfem2.iso27004.Service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfem2.iso27004.Entity.Evaluation;
import com.pfem2.iso27004.Entity.Indicator;
import com.pfem2.iso27004.Repository.EvaluationRepository;

import jakarta.transaction.Transactional;

@Service
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;

    @Autowired
    public EvaluationService(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    public Evaluation SaveEvaluation(Evaluation evaluation) {

        return this.evaluationRepository.save(evaluation);
    }

    public Evaluation addevalution(Evaluation evaluation) {

        Indicator indicator = evaluation.getIndicator();
        String frequency = indicator.getFrequency();
        double acceptableValue = indicator.getAcceptableValue();
        double targetValue = indicator.getTargetValue();
        String performance = indicator.getperformance();

        Calendar nextEvaluationDate = Calendar.getInstance();
        nextEvaluationDate.setTime(evaluation.getEvaluationDate());

        switch (frequency) {
            case "monthly":
                nextEvaluationDate.add(Calendar.MONTH, 1);
                break;
            case "quarterly":
                nextEvaluationDate.add(Calendar.MONTH, 3);
                break;
            case "annually":
                nextEvaluationDate.add(Calendar.YEAR, 1);
                break;
            default:
                throw new IllegalArgumentException("Invalid frequency: " + frequency);
        }

        evaluation.setNextEvaluationDate(nextEvaluationDate.getTime());

        double performanceValue = 0.0;
        Evaluation previousEvaluation = this.evaluationRepository
                .findLatestEvaluationByIndicatorId(evaluation.getIndicator().getId());

        if (previousEvaluation != null) {
            double previousValue = previousEvaluation.getValue();
            double currentValue = evaluation.getValue();
            if (performance.equals("asc")) {
                performanceValue = currentValue - previousValue;
            } else if (performance.equals("desc")) {
                performanceValue = previousValue - currentValue;
            }
        }
        evaluation.setPerformance(performanceValue);

        double value = evaluation.getValue();
        String status;
        if ((performance.equals("asc") && value >= targetValue) ||
                (performance.equals("desc") && value <= targetValue)) {
            status = "Target-achieved";
        } else if ((performance.equals("asc") && value >= acceptableValue && value < targetValue) ||
                (performance.equals("desc") && value <= acceptableValue && value > targetValue)) {
            status = "Acceptable";
        } else {
            status = "Bad";
        }

        evaluation.setStatus(status);

        return this.evaluationRepository.save(evaluation);
    }

    public List<Evaluation> getLatestEvaluations() {

        return this.evaluationRepository.findLatestEvaluations();
    }

    @Transactional
    public void deleteAllEvaluationsByIndicator(Long id) {
        this.evaluationRepository.deleteAllEvaluationsByIndicatorId(id);
    }

    public List<Evaluation> getAllInicatorEvaluations(Long id) {
        return this.evaluationRepository.findAllEvaluationsByIndicatorId(id);
    }

    public Evaluation getLatestInicatorEvaluation(Long id) {
        return this.evaluationRepository.findLatestEvaluationByIndicatorId(id);
    }

    public List<Evaluation> getDashboardIndicator() {
        return this.evaluationRepository.findDashboardIndicator();
    }

    public void updateEvaluations(Indicator indicator) {
        List<Evaluation> list = this.getAllInicatorEvaluations(indicator.getId());

        String performance = indicator.getperformance();
        double acceptableValue = indicator.getAcceptableValue();
        double targetValue = indicator.getTargetValue();
        for (Evaluation e : list) {
            if (!indicator.getperformance().equals(e.getIndicator().getperformance())) {
                e.setPerformance(-e.getPerformance());
            }
            double value = e.getValue();
            String status;
            if ((performance.equals("asc") && value >= targetValue) ||
                    (performance.equals("desc") && value <= targetValue)) {
                status = "Target-achieved";
            } else if ((performance.equals("asc") && value >= acceptableValue && value < targetValue) ||
                    (performance.equals("desc") && value <= acceptableValue && value > targetValue)) {
                status = "Acceptable";
            } else {
                status = "Bad";
            }

            e.setStatus(status);

            this.evaluationRepository.save(e);
        }
    }
}