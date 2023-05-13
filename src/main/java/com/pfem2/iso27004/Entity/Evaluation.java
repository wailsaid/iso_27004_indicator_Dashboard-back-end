package com.pfem2.iso27004.Entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double value;

    @Column(nullable = false)
    private Double performance;

    @Column(nullable = false)
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date evaluationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date nextEvaluationDate;

    @ManyToOne
    @JoinColumn(name = "indicator_id")
    private Indicator indicator;

    public Evaluation() {
    }

    public Evaluation(Long id, Double value, Double performance, String status, Date evaluationDate,
            Date nextEvaluationDate, Indicator indicator) {
        this.id = id;
        this.value = value;
        this.performance = performance;
        this.status = status;
        this.evaluationDate = evaluationDate;
        this.nextEvaluationDate = nextEvaluationDate;
        this.indicator = indicator;
    }

    public Evaluation(Long id, Double value, String status, Date evaluationDate, Date nextEvaluationDate,
            Indicator indicator) {
        this.id = id;
        this.value = value;
        this.status = status;
        this.evaluationDate = evaluationDate;
        this.nextEvaluationDate = nextEvaluationDate;
        this.indicator = indicator;
    }

    public Evaluation(Double value, Date evaluationDate, Indicator indicator) {
        this.value = value;
        this.evaluationDate = evaluationDate;
        this.indicator = indicator;
    }

    public Evaluation(Double value, String status, Date evaluationDate, Date nextEvaluationDate,
            Indicator indicator) {
        this.value = value;
        this.status = status;
        this.evaluationDate = evaluationDate;
        this.nextEvaluationDate = nextEvaluationDate;
        this.indicator = indicator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(Date evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public Date getNextEvaluationDate() {
        return nextEvaluationDate;
    }

    public void setNextEvaluationDate(Date nextEvaluationDate) {
        this.nextEvaluationDate = nextEvaluationDate;
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }

    public Double getPerformance() {
        return performance;
    }

    public void setPerformance(Double performance) {
        this.performance = performance;
    }

}
