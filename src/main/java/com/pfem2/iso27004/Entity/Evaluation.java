package com.pfem2.iso27004.Entity;

import java.util.Date;

import jakarta.persistence.CascadeType;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User resp;

}
