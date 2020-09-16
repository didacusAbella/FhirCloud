package it.didacusabella.fhirserver.patients;

import it.didacusabella.fhirserver.resource.ResourceMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.DateType;
import org.hl7.fhir.r4.model.Enumerations;
import org.hl7.fhir.r4.model.Extension;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.StringType;

/**
 *
 * @author didacus
 */
public class PatientMapper extends ResourceMapper  {

   public static Patient mapPatient(ResultSet rs) throws SQLException {
       Patient patient = new Patient();
       patient.setId(extractString(rs,"Id"));
       patient.addIdentifier()
               .setSystem("http://hl7.org/fhir/sid/us-ssn")
               .setValue(extractString(rs,"ssn"));
       patient.addExtension("http://acme.org/fhir/StructureDefinition/passport-number", 
               new StringType(extractString(rs,"passport")));
       patient.addExtension(createGeolocation(extractString(rs, "lat"), extractString(rs, "lon")));
       patient.addName()
               .setFamily(extractString(rs,"last"))
               .addGiven(extractString(rs,"first"))
               .addPrefix(extractString(rs,"prefix"))
               .addSuffix(extractString(rs,"suffix"));
       patient.setGender(createGender(extractString(rs,"gender")));
       patient.setBirthDateElement(new DateType(extractDate(rs, "birthdate")));
       patient.setMaritalStatus(createMaritalStatus(extractString(rs,"marital")));
       patient.addExtension("http://hl7.org/fhir/StructureDefinition/patient-birthPlace", 
               new StringType(extractString(rs,"birthplace")));
       patient.addAddress()
               .setCity(extractString(rs,"city"))
               .setCountry(extractString(rs,"country"))
               .setState(extractString(rs,"state"))
               .setPostalCode(extractString(rs,"zip"))
               .setText(extractString(rs,"address"));
       return patient;
   }
   
   private static Enumerations.AdministrativeGender createGender(String gender) {
       switch(gender) {
           case "M":
               return Enumerations.AdministrativeGender.MALE;
           case "F":
               return Enumerations.AdministrativeGender.FEMALE;
           default:
               return Enumerations.AdministrativeGender.UNKNOWN;
       }
   }
 
   private static CodeableConcept createMaritalStatus(String marital) {
       switch(marital){
           case "M":
               return new CodeableConcept().addCoding(new Coding("http://terminology.hl7.org/CodeSystem/v3-MaritalStatus", marital, "Married"));
           default:
               return new CodeableConcept().addCoding(new Coding("http://terminology.hl7.org/CodeSystem/v3-MaritalStatus", marital, "Never Married"));
       }
   }
   
   private static Extension createGeolocation(String latitude, String longitude) {
       Extension geolocation = new Extension("http://hl7.org/fhir/StructureDefinition/geolocation");
       geolocation.addExtension("http://hl7.org/fhir/StructureDefinition/geolocation#latitude", new StringType(latitude));
       geolocation.addExtension("http://hl7.org/fhir/StructureDefinition/geolocation#longitude", new StringType(longitude));
       return geolocation;
   }
}
