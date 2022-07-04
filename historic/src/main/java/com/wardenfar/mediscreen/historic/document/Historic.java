package com.wardenfar.mediscreen.historic.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("historic")
@Getter
@Setter
@NoArgsConstructor
public class Historic {
    @Id
    private String id;

    private Integer patientId;
    private String notes;
}
