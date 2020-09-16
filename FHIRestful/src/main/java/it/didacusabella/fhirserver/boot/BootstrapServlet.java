package it.didacusabella.fhirserver.boot;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.EncodingEnum;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.RestfulServer;
import it.didacusabella.fhirserver.allergies.AllergyProvider;
import it.didacusabella.fhirserver.careplans.CarePlanProvider;
import it.didacusabella.fhirserver.conditions.ConditionProvider;
import it.didacusabella.fhirserver.devices.DeviceProvider;
import it.didacusabella.fhirserver.encounters.EncounterProvider;
import it.didacusabella.fhirserver.imagingstudies.ImagingStudyProvider;
import it.didacusabella.fhirserver.immunizations.ImmunizationProvider;
import it.didacusabella.fhirserver.medications.MedicationProvider;
import it.didacusabella.fhirserver.observations.ObservationProvider;
import it.didacusabella.fhirserver.patients.PatientProvider;
import it.didacusabella.fhirserver.procedures.ProcedureProvider;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.sql.DataSource;
/**
 *
 * @author didacus
 */
@WebServlet(name = "BootstrapServlet", urlPatterns = {"/fhir/*"})
public class BootstrapServlet extends RestfulServer {
    
    @Resource(name = "jdbc/integration")
    private static DataSource ds; 

    @Override
    protected void initialize() throws ServletException {
        this.setFhirContext(FhirContext.forR4());
        this.setDefaultResponseEncoding(EncodingEnum.JSON);
        this.registerProviders(createProviders());
    }

    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Main Servlet";
    }

    private static List<IResourceProvider> createProviders(){
        return Arrays.asList(
                new AllergyProvider(ds), new CarePlanProvider(ds),
                new ConditionProvider(ds), new DeviceProvider(ds), 
                new EncounterProvider(ds), new ImagingStudyProvider(ds),
                new ImmunizationProvider(ds), new MedicationProvider(ds),
                new ObservationProvider(ds), new PatientProvider(ds), 
                new ProcedureProvider(ds));
    }
}
