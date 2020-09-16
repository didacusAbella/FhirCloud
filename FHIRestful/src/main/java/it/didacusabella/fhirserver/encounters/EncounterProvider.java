package it.didacusabella.fhirserver.encounters;

import ca.uhn.fhir.rest.api.MethodOutcome;
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
import org.hl7.fhir.r4.model.Encounter;
import org.hl7.fhir.r4.model.IdType;

/**
 *
 * @author didacus
 */
public class EncounterProvider extends RestfulProvider<Encounter> {

    public EncounterProvider(DataSource ds) {
        super(ds);
    }

    @Override
    public Encounter getResourceById(IdType id) throws SQLException {
        Encounter encounter = new JdbcSession(ds)
                .sql("SELECT * FROM [dbo].[encounters] WHERE Id = ?")
                .set(id.getIdPart())
                .select(new Outcome<Encounter>() {
            @Override
            public Encounter handle(ResultSet rs, Statement stmnt) throws SQLException {
                final Encounter enc = (rs.next()) ? EncounterMapper.mapEncounter(rs) : null;
                return enc;
            }
        });
        return encounter;
    }

    @Override
    public List<Encounter> getAll() throws SQLException {
        List<Encounter> encounters = new JdbcSession(ds)
                .sql("SELECT TOP 10 * FROM [dbo].[encounters]")
                .select(new Outcome<List<Encounter>>() {
            @Override
            public List<Encounter> handle(ResultSet rs, Statement stmnt) throws SQLException {
                final List<Encounter> ecns = new LinkedList<>();
                while(rs.next()){
                    ecns.add(EncounterMapper.mapEncounter(rs));
                }
                return ecns;
            }
        });
        return encounters;
    }

    @Override
    public Class<? extends IBaseResource> getResourceType() {
       return Encounter.class;
    }
    
}
