package it.didacusabella.fhirserver.allergies;

import it.didacusabella.fhirserver.resource.ResourceMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.hl7.fhir.r4.model.AllergyIntolerance;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Period;
import org.hl7.fhir.r4.model.Reference;

/**
 *
 * @author didacus
 */
public class AllergyMapper extends ResourceMapper {
    
   public static AllergyIntolerance mapAllergy(ResultSet rs) throws SQLException {
       AllergyIntolerance allergy = new AllergyIntolerance();
       allergy.setId(extractString(rs, "code"));
       allergy.setPatient(new Reference(extractString(rs, "patient")));
       allergy.setEncounter(new Reference(extractString(rs, "encounter")));
       allergy.setOnset(new Period().setStart(extractDate(rs, "start")).setEnd(extractDate(rs, "stop")));
       allergy.setCode(new CodeableConcept(new Coding("http://snomed.info/sct", extractString(rs, "code"), extractString(rs, "description"))));
       return allergy;
   }
}
