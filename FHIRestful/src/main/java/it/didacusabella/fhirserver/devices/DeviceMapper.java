package it.didacusabella.fhirserver.devices;

import it.didacusabella.fhirserver.resource.ResourceMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Device;
import org.hl7.fhir.r4.model.Reference;

/**
 *
 * @author didacus
 */
public class DeviceMapper extends ResourceMapper {
    
    public static Device mapDevice(ResultSet rs) throws SQLException {
        Device device = new Device();
        device.setId(extractString(rs, "udi"));
        device.setManufactureDate(extractDate(rs, "start"));
        device.setExpirationDate(extractDate(rs, "stop"));
        device.setPatient(new Reference(extractString(rs, "patient")));
        device.addUdiCarrier().setDeviceIdentifier(extractString(rs, "udi"));
        device.setType(new CodeableConcept(new Coding("http://snomed.info/sct", 
                extractString(rs, "code"), extractString(rs, "description"))));
        return device;
    }
    
}
