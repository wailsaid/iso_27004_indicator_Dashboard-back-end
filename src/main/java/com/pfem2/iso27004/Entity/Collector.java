package com.pfem2.iso27004.Entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "collector")
public class Collector {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User collector;

    @ManyToMany
    @JoinTable(name = "collector_indicator", joinColumns = @JoinColumn(name = "indicator_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Indicator> indicator;
}
