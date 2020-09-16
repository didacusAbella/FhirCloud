package it.didacusabella.fhirserver.careplans;

import it.didacusabella.fhirserver.resource.ResourceMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.hl7.fhir.r4.model.CarePlan;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Period;
import org.hl7.fhir.r4.model.Reference;

/**
 *
 * @author didacus
 */
public class CareplanMapper extends ResourceMapper {
    
    public static CarePlan mapCareplan(ResultSet rs) throws SQLException {
        CarePlan careplan = new CarePlan();
        careplan.setId(extractString(rs, "Id"));
        careplan.setPeriod(new Period().setStart(extractDate(rs, "start")).setEnd(extractDate(rs, "stop")));
        careplan.setDescription(extractString(rs, "description"));
        careplan.addCategory().addCoding(new Coding("http://snomed.info/sct", extractString(rs, "code"), extractString(rs, "description")));
        careplan.setEncounter(new Reference(extractString(rs, "encounter")));
        careplan.setSubject(new Reference(extractString(rs, "patient")));
        careplan.addCategory().addCoding(new Coding("http://snomed.info/sct", extractString(rs, "reasoncode"), extractString(rs, "reasondescription")));
        return careplan;
    }
}
