package com.pfem2.iso27004.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfem2.iso27004.Entity.App;
import com.pfem2.iso27004.Entity.Collector;
import com.pfem2.iso27004.Entity.Departement;
import com.pfem2.iso27004.Entity.Evaluation;
import com.pfem2.iso27004.Entity.Indicator;
import com.pfem2.iso27004.Repository.IndicatorRepository;
import com.pfem2.iso27004.Security.Errors.BadrequestException;

@Service
public class IndicatorService {

    private final IndicatorRepository indicatorRepository;
    private final EvaluationService evaluationService;
    private final UserService userService;
    private final DepartementService departementService;

    @Autowired
    public IndicatorService(IndicatorRepository indicatorRepository, EvaluationService evaluationService,
            UserService userService, DepartementService departementService) {
        this.indicatorRepository = indicatorRepository;
        this.evaluationService = evaluationService;
        this.userService = userService;
        this.departementService = departementService;
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
            // throw new IllegalStateException("indicator with id: " + id + " does not
            // exists");
            throw new BadrequestException("indicator with id: " + id + " does not exists");
        }
        Indicator indicator = indicatorRepository.findById(id).orElseThrow();

        List<Collector> collectors = this.userService.getCollectors();
        for (Collector c : collectors) {
            c.getIndicator().remove(indicator);
        }
        List<Departement> departements = this.departementService.getDepartements();
        for (Departement d : departements) {
            d.getIndicators().remove(indicator);
        }
        this.userService.saveALLCollectors(collectors);
        this.departementService.saveAllDepartement(departements);
        this.evaluationService.deleteAllEvaluationsByIndicator(id);

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
            // throw new IllegalStateException("indicator with id: " + id + " does not
            // exists");
            throw new BadrequestException("indicator with id: " + id + " does not exists");
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
        return this.indicatorRepository.findIndicatorsWithoutEvaluation();
    }

    public List<Indicator> nextMonthIndicator() {
        List<Indicator> dueIndicators = new ArrayList<>();
        List<Evaluation> evalaution = evaluationService.getLatestEvaluations();

        Calendar nextMonth = Calendar.getInstance();
        nextMonth.add(Calendar.MONTH, 1);

        for (Evaluation iv : evalaution) {
            Calendar nextEvalDate = Calendar.getInstance();
            nextEvalDate.setTime(iv.getNextEvaluationDate());

            if (nextEvalDate.get(Calendar.YEAR) == nextMonth.get(Calendar.YEAR)
                    && nextEvalDate.get(Calendar.MONTH) == nextMonth.get(Calendar.MONTH)) {
                Indicator indicator = iv.getIndicator();

                if (!dueIndicators.contains(indicator)) {
                    dueIndicators.add(indicator);

                }
            }
        }
        return dueIndicators;
    }

    public List<Indicator> nextWeekIndicator() {
        List<Indicator> dueIndicators = new ArrayList<>();
        List<Evaluation> evaluations = evaluationService.getLatestEvaluations();

        Calendar nextWeek = Calendar.getInstance();
        nextWeek.add(Calendar.WEEK_OF_YEAR, 1);

        for (Evaluation evaluation : evaluations) {
            Calendar nextEvalDate = Calendar.getInstance();
            nextEvalDate.setTime(evaluation.getNextEvaluationDate());

            if (nextEvalDate.get(Calendar.YEAR) == nextWeek.get(Calendar.YEAR)
                    && nextEvalDate.get(Calendar.WEEK_OF_YEAR) == nextWeek.get(Calendar.WEEK_OF_YEAR)) {
                Indicator indicator = evaluation.getIndicator();

                if (!dueIndicators.contains(indicator)) {
                    dueIndicators.add(indicator);
                }
            }
        }

        return dueIndicators;
    }

    public List<Indicator> overdueIndicator() {
        List<Indicator> overdueIndicators = new ArrayList<>();
        List<Evaluation> evaluations = evaluationService.getLatestEvaluations();

        Calendar today = Calendar.getInstance();

        for (Evaluation evaluation : evaluations) {
            Calendar nextEvalDate = Calendar.getInstance();
            nextEvalDate.setTime(evaluation.getNextEvaluationDate());

            if (nextEvalDate.before(today)) {
                Indicator indicator = evaluation.getIndicator();

                if (!overdueIndicators.contains(indicator)) {
                    overdueIndicators.add(indicator);
                }
            }
        }

        return overdueIndicators;
    }

    /*
     * public List<Indicator> nextWeekIndicator() {
     * List<Indicator> dueIndicators = new ArrayList<>();
     * List<Evaluation> evalaution = evaluationService.getLatestEvaluations();
     *
     * Calendar nextMonth = Calendar.getInstance();
     * nextMonth.add(Calendar.DAY_OF_WEEK, 1);
     *
     * for (Evaluation iv : evalaution) {
     * Calendar nextEvalDate = Calendar.getInstance();
     * nextEvalDate.setTime(iv.getNextEvaluationDate());
     *
     * if (nextEvalDate.get(Calendar.YEAR) == nextMonth.get(Calendar.YEAR)
     * && nextEvalDate.get(Calendar.MONTH) == nextMonth.get(Calendar.)) {
     * Indicator indicator = iv.getIndicator();
     *
     * if (!dueIndicators.contains(indicator)) {
     * dueIndicators.add(indicator);
     *
     * }
     * }
     * }
     * return dueIndicators;
     * // return null;
     * }
     */

}
