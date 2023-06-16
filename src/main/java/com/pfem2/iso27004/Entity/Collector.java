package com.pfem2.iso27004.Entity;

import java.util.List;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

public class Collector extends User {

    @ManyToMany
    @JoinTable(name = "collector_indicator", joinColumns = @JoinColumn(name = "indicator_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Indicator> indicator;
}
