package it.didacusabella.fhirserver.observations;

import it.didacusabella.fhirserver.resource.ResourceMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Period;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.StringType;

/**
 *
 * @author didacus
 */
public class ObservationMapper extends ResourceMapper {
    
    public static Observation mapObservation(ResultSet rs) throws SQLException {
        Observation observation = new Observation();
        observation.setId(extractString(rs, "code"));
        observation.setSubject(new Reference(extractString(rs, "patient")));
        observation.setEncounter(new Reference(extractString(rs, "encounter")));
        observation.setEffective(new DateTimeType(extractDate(rs, "date")));
        observation.setCode(new CodeableConcept(new Coding("http://loinc.org", extractString(rs, "code"), extractString(rs, "description"))));
        observation.setValue(new StringType(String.format("%s %s", 
                extractString(rs, "value"), extractString(rs, "units"))));
        return observation;
    }
}
