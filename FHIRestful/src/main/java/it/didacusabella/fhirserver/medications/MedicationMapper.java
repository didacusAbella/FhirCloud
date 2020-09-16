package it.didacusabella.fhirserver.medications;

import it.didacusabella.fhirserver.resource.ResourceMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Medication;

/**
 *
 * @author didacus
 */
public class MedicationMapper extends ResourceMapper {
    
    public static Medication mapMedication(ResultSet rs) throws SQLException {
        Medication medication = new Medication();
        medication.setId(extractString(rs, "code"));
        medication.setCode(new CodeableConcept(new Coding("http://www.nlm.nih.gov/research/umls/rxnorm",
                extractString(rs, "code"), extractString(rs, "description"))));
        return medication;
    }
}
