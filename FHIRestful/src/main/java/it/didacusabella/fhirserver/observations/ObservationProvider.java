package it.didacusabella.fhirserver.observations;

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
import org.hl7.fhir.r4.model.Observation;

/**
 *
 * @author didacus
 */
public class ObservationProvider extends RestfulProvider<Observation> {

    public ObservationProvider(DataSource ds) {
        super(ds);
    }

    @Override
    public Observation getResourceById(IdType id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Observation> getAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Search
    public List<Observation> findByEncounter(@RequiredParam(name = Observation.SP_ENCOUNTER) StringParam encounter) throws SQLException {
          List<Observation> observations = new JdbcSession(ds)
                .sql("SELECT * FROM [dbo].[observations]  WHERE encounter = ?")
                .set(encounter.getValue())
                .select(new Outcome<List<Observation>>() {
                    @Override
                    public List<Observation> handle(ResultSet rs, Statement stmnt) throws SQLException {
                        final List<Observation> sts = new LinkedList<>();
                        while (rs.next()) {
                            sts.add(ObservationMapper.mapObservation(rs));
                        }
                        return sts;
                    }
                });
        return observations;
    } 

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Observation.class;
    }
    
}
