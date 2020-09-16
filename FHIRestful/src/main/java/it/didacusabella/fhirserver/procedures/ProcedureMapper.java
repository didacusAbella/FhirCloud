package it.didacusabella.fhirserver.procedures;

import it.didacusabella.fhirserver.resource.ResourceMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.Procedure;
import org.hl7.fhir.r4.model.Reference;

/**
 *
 * @author didacus
 */
public class ProcedureMapper extends ResourceMapper {
    
    public static Procedure mapProcedure(ResultSet rs) throws SQLException {
        Procedure procedure = new Procedure();
        procedure.setId(extractString(rs, "code"));
        procedure.setSubject(new Reference(extractString(rs, "patient")));
        procedure.setEncounter(new Reference(extractString(rs, "encounter")));
        procedure.setPerformed(new DateTimeType(extractDate(rs, "date")));
        procedure.addReasonCode().addCoding(new Coding("http://snomed.info/sct", extractString(rs, "reasoncode"), 
                extractString(rs, "reasondescription")));
        procedure.setCode(new CodeableConcept(new Coding("http://snomed.info/sct", 
                extractString(rs, "code"), extractString(rs, "description"))));
        return procedure;
    }
}
