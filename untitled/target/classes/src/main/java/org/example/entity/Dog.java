package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Table(name="dogs")
@AllArgsConstructor
@NoArgsConstructor
public class Dog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    @Column
    private String name;

    @Column
    private String breed;

    @Column
    @Enumerated(EnumType.STRING)
    private Suppliers supplier;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long badgeId;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private LocalDate birthDate;
    @Column
    private LocalDate dateAcquired;

    @Column
    @Enumerated(EnumType.STRING)
    private Status currentStatus;

    @Column
    private LocalDate leavingDate;

    @Column
    @Enumerated(EnumType.STRING)
    private LeavingReason leavingReason;

    @Column
    @Enumerated(EnumType.STRING)
    private Kenneling kennellingCharacteristic;

    private boolean deleted = false;

}

