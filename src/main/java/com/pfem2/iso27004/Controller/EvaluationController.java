package com.pfem2.iso27004.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfem2.iso27004.Entity.Evaluation;
import com.pfem2.iso27004.Service.EvaluationService;

@RestController
@RequestMapping("api/v1/evaluation")
@CrossOrigin(origins = "*")

public class EvaluationController {

    private final EvaluationService evaluationService;

    @Autowired
    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @GetMapping(path = "/dashboard")
    public List<Evaluation> getDashboard() {
        return evaluationService.getDashboardIndicator();
    }

    @GetMapping(path = "/all/{indicatorID}")
    public List<Evaluation> getAllInicatorEvaluation(@PathVariable("indicatorID") Long id) {
        return this.evaluationService.getAllInicatorEvaluations(id);
    }

    @GetMapping
    public List<Evaluation> getLatestEvaluation() {
        return this.evaluationService.getLatestEvaluations();
    }

    @GetMapping(path = "/{indicatorID}")
    public Evaluation getLatestInicatorEvaluation(@PathVariable("indicatorID") Long id) {
        return this.evaluationService.getLatestInicatorEvaluation(id);
    }

    @PostMapping
    public Evaluation evaluate(@RequestBody Evaluation evaluation) {

        return evaluationService.addevalution(evaluation);
    }

}
