package it.didacusabella.fhirserver.immunizations;

import ca.uhn.fhir.rest.annotation.RequiredParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.param.StringParam;
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
import org.hl7.fhir.r4.model.Immunization;

/**
 *
 * @author didacus
 */
public class ImmunizationProvider extends RestfulProvider<Immunization> {

    public ImmunizationProvider(DataSource ds) {
        super(ds);
    }

    @Override
    public Immunization getResourceById(IdType id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Immunization> getAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    @Search
    public List<Immunization> findByPatient(@RequiredParam(name = Immunization.SP_PATIENT) StringParam patient) throws SQLException {
          List<Immunization> immunizations = new JdbcSession(ds)
                .sql("SELECT * FROM [dbo].[immunizations]  WHERE patient = ?")
                .set(patient.getValue())
                .select(new Outcome<List<Immunization>>() {
                    @Override
                    public List<Immunization> handle(ResultSet rs, Statement stmnt) throws SQLException {
                        final List<Immunization> sts = new LinkedList<>();
                        while (rs.next()) {
                            sts.add(ImmunizationMapper.mapImmunization(rs));
                        }
                        return sts;
                    }
                });
        return immunizations;
    }

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Immunization.class;
    }
    
}
