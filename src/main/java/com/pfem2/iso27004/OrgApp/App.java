package com.pfem2.iso27004.OrgApp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "app")
public class App {

    @Id
    @SequenceGenerator(name = "app_sequence", sequenceName = "app_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_sequence")
    private Long id;

    @Column(nullable = false)
    private String name;

    // @ManyToMany
    // private Set<Indicator> indicators;

    public App() {
    }

    public App(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public App(String name) {
        this.name = name;
    }
    /*
     * public Set<Indicator> getIndicators() {
     * return indicators;
     * }
     *
     * public void setIndicators(Set<Indicator> indicators) {
     * this.indicators = indicators;
     * }
     */

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

}