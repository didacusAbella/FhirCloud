package it.didacusabella.fhirserver.medications;

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
import org.hl7.fhir.r4.model.Medication;

/**
 *
 * @author didacus
 */
public class MedicationProvider extends RestfulProvider<Medication> {

    public MedicationProvider(DataSource ds) {
        super(ds);
    }
    
    @Override
    public Medication getResourceById(IdType id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Medication> getAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    @Search
    public List<Medication> findByEncounter(@RequiredParam(name = Medication.SP_IDENTIFIER) StringParam encounter) throws SQLException {
          List<Medication> medications = new JdbcSession(ds)
                .sql("SELECT * FROM [dbo].[medications]  WHERE encounter = ?")
                .set(encounter.getValue())
                .select(new Outcome<List<Medication>>() {
                    @Override
                    public List<Medication> handle(ResultSet rs, Statement stmnt) throws SQLException {
                        final List<Medication> sts = new LinkedList<>();
                        while (rs.next()) {
                            sts.add(MedicationMapper.mapMedication(rs));
                        }
                        return sts;
                    }
                });
        return medications;
    }

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Medication.class;
    }
    
}
