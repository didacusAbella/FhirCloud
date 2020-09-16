package it.didacusabella.fhirserver.encounters;

import it.didacusabella.fhirserver.resource.ResourceMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Encounter;
import org.hl7.fhir.r4.model.Period;
import org.hl7.fhir.r4.model.Reference;

/**
 *
 * @author didacus
 */
public class EncounterMapper extends ResourceMapper {
    
    public static Encounter mapEncounter(ResultSet rs) throws SQLException {
        Encounter encounter = new Encounter();
        encounter.setId(extractString(rs, "Id"));
        encounter.setSubject(new Reference(extractString(rs, "patient")));
        encounter.setPeriod(new Period().setStart(rs.getDate("start")).setEnd(rs.getDate("stop")));
        encounter.setServiceProvider(new Reference(extractString(rs, "organization")));
        encounter.setClass_(new Coding("http://terminology.hl7.org/ValueSet/v3-ActEncounterCode", 
                createClassCode(extractString(rs, "encounterclass")), 
                extractString(rs, "encounterclass")));
        encounter.addReasonCode()
                .addCoding(new Coding("http://snomed.info/sct", extractString(rs, "code"), 
                        extractString(rs, "description")));
        return encounter;
    }
    
    private static String createClassCode(String encounterClass) {
        switch(encounterClass){
            case "ambulatory":
                return "AMB";
            case "emergency":
                return "EMER";
            case "field":
                return "FLD";
            case "home health":
                return "HH";
            case "inpatient encounter":
                return "IMP";
            case "inpatient acute":
                return "ACUTE";
            case "inpatient non-acute":
                return "NONAC";
            case "observation encounter":
                return "OBSENC";
            case "pre-admission":
                return "PRENC";
            case "short stay":
                return "SS";
            case "virtual":
                return "VR";
            default:
                return "UNK";
        }
    }
}
