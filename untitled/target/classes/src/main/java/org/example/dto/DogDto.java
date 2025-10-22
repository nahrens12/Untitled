package org.example.dto;

import org.example.entity.Gender;
import org.example.entity.Status;
import org.example.entity.LeavingReason;
import java.time.LocalDate;

public class DogDto {
    public Long id;
    public String name;
    public String breed;
    public String supplier;
    public String badgeId;
    public Gender gender;
    public LocalDate birthDate;
    public LocalDate dateAcquired;
    public Status currentStatus;
    public LocalDate leavingDate;
    public LeavingReason leavingReason;
    public String kennellingCharacteristic;
}
