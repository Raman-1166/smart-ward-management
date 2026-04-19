package com.ward.system.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "wards")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String area;

    private Integer population;

    @Column(name = "officer_name")
    private String officerName;

    @Column(name = "cleanliness_score")
    private Double cleanlinessScore;
}
