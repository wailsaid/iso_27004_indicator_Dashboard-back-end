package com.pfem2.iso27004.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfem2.iso27004.Entity.App;
import com.pfem2.iso27004.Entity.Evaluation;
import com.pfem2.iso27004.Entity.Indicator;
import com.pfem2.iso27004.Repository.IndicatorRepository;

@Service
public class IndicatorService {

    private final IndicatorRepository indicatorRepository;
    private final EvaluationService evaluationService;

    @Autowired
    public IndicatorService(IndicatorRepository indicatorRepository, EvaluationService evaluationService) {
        this.indicatorRepository = indicatorRepository;
        this.evaluationService = evaluationService;
    }

    public List<Indicator> getIndicators() {
        return indicatorRepository.findAll();
    }

    public Indicator addIndicator(Indicator indicator) {
        return indicatorRepository.save(indicator);
    }

    public void deleteIndicator(Long id) {
        indicatorRepository.findById(id);
        if (!indicatorRepository.existsById(id)) {
            throw new IllegalStateException("indicator with id: " + id + " does not exists");
        }
        Indicator indicator = indicatorRepository.findById(id).orElseThrow();

        evaluationService.deleteAllEvaluationsByIndicator(id);
        indicatorRepository.delete(indicator);
    }

    public void deleteIndicatorApp(App app) {
        List<Indicator> lIndicators = this.getIndicators();

        for (Indicator indicator : lIndicators) {
            indicator.getApps().remove(app);
        }
        indicatorRepository.saveAll(lIndicators);

    }

    public Indicator getIndicatorByID(Long id) {
        Optional<Indicator> indicator = indicatorRepository.findById(id);

        if (!indicator.isPresent()) {
            throw new IllegalStateException("indicator with id: " + id + " does not exists");
        }
        return indicator.get();
    }

    public List<Indicator> getRIndicators() {
        List<Evaluation> e = this.evaluationService.getLatestEvaluations();
        List<Indicator> i = this.getIndicators();
        for (Evaluation ev : e) {

            i.removeIf(new Predicate<Indicator>() {

                @Override
                public boolean test(Indicator arg0) {
                    return ev.getIndicator().equals(arg0);
                }

            });
        }
        return i;
    }

    public Indicator editIndicator(Indicator indicator) {
        this.evaluationService.updateEvaluations(indicator);
        return this.indicatorRepository.save(indicator);
    }

    public List<Indicator> getIndicatorNoEval() {
        return this.getIndicatorNoEval();
    }

}
