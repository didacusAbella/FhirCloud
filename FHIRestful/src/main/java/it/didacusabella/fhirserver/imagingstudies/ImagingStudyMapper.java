package it.didacusabella.fhirserver.imagingstudies;

import it.didacusabella.fhirserver.resource.ResourceMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.ImagingStudy;
import org.hl7.fhir.r4.model.Reference;

/**
 *
 * @author didacus
 */
public class ImagingStudyMapper extends ResourceMapper {
    
    public static ImagingStudy mapStudy(ResultSet rs) throws SQLException {
        ImagingStudy study = new ImagingStudy();
        study.setId(extractString(rs, "Id"));
        study.setStarted(extractDate(rs, "date"));
        study.setSubject(new Reference(extractString(rs, "patient")));
        study.setEncounter(new Reference(extractString(rs, "encounter")));
        study.addSeries()
                .setModality(new Coding("http://dicom.nema.org/resources/ontology/DCM", 
                        extractString(rs, "modality_code"), 
                        extractString(rs, "modality_description")))
                .setBodySite(new Coding("http://snomed.info/sct", 
                        extractString(rs, "bodysite_code"), 
                        extractString(rs, "bodysite_description")))
                .addEndpoint(new Reference(extractString(rs, "path")))
                .addInstance().setSopClass(new Coding("urn:ietf:rfc:3986", 
                        extractString(rs, "sop_code"), 
                        extractString(rs, "sop_description")));
        return study;
    }
}
