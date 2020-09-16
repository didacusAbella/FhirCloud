package it.didacusabella.fhirserver.conditions;

import it.didacusabella.fhirserver.resource.ResourceMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Condition;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.Reference;

/**
 *
 * @author didacus
 */
public class ConditionMapper extends ResourceMapper {
    
    public static Condition mapCondition(ResultSet rs) throws SQLException {
        Condition condition = new Condition();
        condition.setId(extractString(rs, "code"));
        condition.setSubject(new Reference(extractString(rs, "patient")));
        condition.setEncounter(new Reference(extractString(rs, "encounter")));
        condition.setOnset(new DateTimeType(extractDate(rs, "start")));
        condition.setAbatement(new DateTimeType(extractDate(rs, "stop")));
        condition.setCode(new CodeableConcept(new Coding("http://snomed.info/sct", extractString(rs, "code"), extractString(rs, "description"))));
        return condition;
    }
}
