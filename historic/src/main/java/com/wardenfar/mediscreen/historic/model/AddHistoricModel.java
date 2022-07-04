package com.wardenfar.mediscreen.historic.model;

import com.wardenfar.mediscreen.historic.document.Historic;
import lombok.Data;

@Data
public class AddHistoricModel {
    Integer patId;
    String e;

    public Historic convertToDocument() {
        Historic doc = new Historic();
        doc.setPatientId(patId);
        doc.setNotes(e);
        return doc;
    }
}
