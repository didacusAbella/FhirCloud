package it.didacusabella.fhirserver.patients;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.Outcome;
import it.didacusabella.fhirserver.resource.RestfulProvider;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Patient;

/**
 *
 * @author didacus
 */
public class PatientProvider extends RestfulProvider<Patient> {

    public PatientProvider(DataSource ds) {
        super(ds);
    }


    @Override
    public Patient getResourceById(IdType id) throws SQLException {
        Patient patient = new JdbcSession(ds).sql("SELECT * FROM [dbo].[patients] WHERE Id = ?")
                .set(id.getIdPart())
                .select(new Outcome<Patient>() {
            @Override
            public Patient handle(ResultSet rs, Statement stmnt) throws SQLException {
                Patient tmpPatient = null;
                if(rs.next()){
                    tmpPatient = PatientMapper.mapPatient(rs);
                }
                return tmpPatient;
            }
        });
        return patient;
    }

    @Override
    public List<Patient> getAll() throws SQLException {
        List<Patient> patients = new JdbcSession(ds).sql("SELECT TOP 100 * FROM [dbo].[patients]")
                .select(new Outcome<List<Patient>>() {
            @Override
            public List<Patient> handle(ResultSet rs, Statement stmnt) throws SQLException {
                final List<Patient> list = new LinkedList<>();
                while(rs.next()){
                   list.add(PatientMapper.mapPatient(rs));
                }
                return list;
            }
        });
        return patients;
    }

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Patient.class;
    }

}
