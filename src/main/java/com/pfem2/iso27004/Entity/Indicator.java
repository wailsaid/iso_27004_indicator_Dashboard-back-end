package com.pfem2.iso27004.Entity;

import jakarta.persistence.JoinColumn;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "indicator")
public class Indicator {
    @Id
    @SequenceGenerator(name = "indicator_sequence", sequenceName = "indicator_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "indicator_sequence")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false, length = 1000)
    private String howtomeasure;

    @Column(nullable = false)
    private String benefit;

    @Column(nullable = false)
    private Double targetValue;

    @Column(nullable = false)
    private Double acceptableValue;

    @Column(nullable = false)
    private String frequency;

    @Column(nullable = false)
    private String valueUnit;

    @Column(nullable = false)
    private String performance;

    @Column(nullable = false)
    private String infoOwner;

    @Column(nullable = false)
    private String infoCollector;

    @Column(nullable = false)
    private String infoCustomer;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean checked = false;

    @ManyToMany
    @JoinTable(name = "app-indicator", joinColumns = @JoinColumn(name = "indicator_id"), inverseJoinColumns = @JoinColumn(name = "app_id"))
    private Set<App> apps;

    public Indicator() {
    }

    public Indicator(Long id, String name, String category, String type, String description, String howtomeasure,
            String benefit, Double targetValue, Double acceptableValue, String frequency, String valueUnit,
            String performance, String infoOwner, String infoCollector, String infoCustomer, boolean checked)

    {
        this.id = id;
        this.name = name;
        this.category = category;
        this.type = type;
        this.description = description;
        this.howtomeasure = howtomeasure;
        this.benefit = benefit;
        this.targetValue = targetValue;
        this.acceptableValue = acceptableValue;
        this.frequency = frequency;
        this.valueUnit = valueUnit;
        this.performance = performance;
        this.infoOwner = infoOwner;
        this.infoCollector = infoCollector;
        this.infoCustomer = infoCustomer;
        this.checked = checked;
    }

    public Indicator(String name, String category, String type, String description, String howtomeasure, String benefit,
            Double targetValue, Double acceptableValue, String frequency, String valueUnit, String performance,
            String infoOwner, String infoCollector, String infoCustomer) {
        this.name = name;
        this.category = category;
        this.type = type;
        this.description = description;
        this.howtomeasure = howtomeasure;
        this.benefit = benefit;
        this.targetValue = targetValue;
        this.acceptableValue = acceptableValue;
        this.frequency = frequency;
        this.valueUnit = valueUnit;
        this.performance = performance;
        this.infoOwner = infoOwner;
        this.infoCollector = infoCollector;
        this.infoCustomer = infoCustomer;
    }

    public Indicator(Long id, String name, String category, String type, String description, String howtomeasure,
            String benefit, Double targetValue, Double acceptableValue, String frequency, String valueUnit,
            String performance, String infoOwner, String infoCollector, String infoCustomer, boolean checked,
            Set<App> apps) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.type = type;
        this.description = description;
        this.howtomeasure = howtomeasure;
        this.benefit = benefit;
        this.targetValue = targetValue;
        this.acceptableValue = acceptableValue;
        this.frequency = frequency;
        this.valueUnit = valueUnit;
        this.performance = performance;
        this.infoOwner = infoOwner;
        this.infoCollector = infoCollector;
        this.infoCustomer = infoCustomer;
        this.checked = checked;
        this.apps = apps;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHowtomeasure() {
        return howtomeasure;
    }

    public void setHowtomeasure(String howtomeasure) {
        this.howtomeasure = howtomeasure;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public Double getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(Double targetValue) {
        this.targetValue = targetValue;
    }

    public Double getAcceptableValue() {
        return acceptableValue;
    }

    public void setAcceptableValue(Double acceptableValue) {
        this.acceptableValue = acceptableValue;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getValueUnit() {
        return valueUnit;
    }

    public void setValueUnit(String valueUnit) {
        this.valueUnit = valueUnit;
    }

    public String getperformance() {
        return performance;
    }

    public void setPeformence(String peformence) {
        this.performance = peformence;
    }

    public String getInfoOwner() {
        return infoOwner;
    }

    public void setInfoOwner(String infoOwner) {
        this.infoOwner = infoOwner;
    }

    public String getInfoCollector() {
        return infoCollector;
    }

    public void setInfoCollector(String infoCollector) {
        this.infoCollector = infoCollector;
    }

    public String getInfoCustomer() {
        return infoCustomer;
    }

    public void setInfoCustomer(String infoCustomer) {
        this.infoCustomer = infoCustomer;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Set<App> getApps() {
        return this.apps;
    }

    public void setApps(Set<App> apps) {
        this.apps = apps;
    }

}
