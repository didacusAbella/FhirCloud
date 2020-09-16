package it.didacusabella.fhirserver.immunizations;

import it.didacusabella.fhirserver.resource.ResourceMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.Immunization;
import org.hl7.fhir.r4.model.Reference;

/**
 *
 * @author didacus
 */
public class ImmunizationMapper extends ResourceMapper {
    
    public static Immunization mapImmunization(ResultSet rs) throws SQLException {
        Immunization immunization = new Immunization();
        immunization.setId(extractString(rs, "code"));
        immunization.setPatient(new Reference(extractString(rs, "patient")));
        immunization.setEncounter(new Reference(extractString(rs, "encounter")));
        immunization.setOccurrence(new DateTimeType(extractDate(rs, "date")));
        immunization.setVaccineCode(new CodeableConcept(new Coding("http://hl7.org/fhir/sid/cvx", 
                extractString(rs, "code"), extractString(rs, "description"))));
        return immunization;
    }
}
