package com.wardenfar.mediscreen.patient.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddRecordResponse {
    public boolean success;
    public Long id;
}
