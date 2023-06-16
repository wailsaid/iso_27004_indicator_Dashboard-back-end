package com.pfem2.iso27004.Entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "indicator")
public class Indicator {
    @Id
    @GeneratedValue
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

}
