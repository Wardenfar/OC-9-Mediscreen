package com.wardenfar.mediscreen.assess.model;

import lombok.Data;

@Data
public class AssessResult {

    private String family;
    private String given;
    public Integer age;
    public AssessLevel level;

    public String toString() {
        return "Patient: " + given + " " + family + " (age " + age + ") diabetes assessment is: " + level.toString();
    }
}
