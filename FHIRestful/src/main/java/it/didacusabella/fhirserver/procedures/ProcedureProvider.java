package it.didacusabella.fhirserver.procedures;

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
import org.hl7.fhir.r4.model.Procedure;

/**
 *
 * @author didacus
 */
public class ProcedureProvider extends RestfulProvider<Procedure> {

    public ProcedureProvider(DataSource ds) {
        super(ds);
    }

    @Override
    public Procedure getResourceById(IdType id) throws SQLException {
        Procedure procedure = new JdbcSession(ds)
                .sql("SELECT * FROM [dbo].[procedures] WHERE Id = ?")
                .set(id.getId())
                .select(new Outcome<Procedure>() {
                    @Override
                    public Procedure handle(ResultSet rs, Statement stmnt) throws SQLException {
                        final Procedure proc = (rs.next()) ? ProcedureMapper.mapProcedure(rs) : null;
                        return proc;
                    }
                });
        return procedure;
    }
    
    @Override
    public List<Procedure> getAll() throws SQLException {
        List<Procedure> procedures = new JdbcSession(ds)
                .sql("SELECT * FROM [dbo].[procedures]")
                .select(new Outcome<List<Procedure>>() {
                    @Override
                    public List<Procedure> handle(ResultSet rs, Statement stmnt) throws SQLException {
                        final List<Procedure> procs = new LinkedList<>();
                        while (rs.next()) {
                            procs.add(ProcedureMapper.mapProcedure(rs));
                        }
                        return procs;
                    }
                });
        return procedures;
    }
    
    @Search
    public List<Procedure> findByEncounter(@RequiredParam(name = Procedure.SP_ENCOUNTER) StringParam encounter) throws SQLException {
        List<Procedure> procedures = new JdbcSession(ds)
                .sql("SELECT * FROM [dbo].[procedures] WHERE encounter = ?")
                .set(encounter.getValue())
                .select(new Outcome<List<Procedure>>() {
                    @Override
                    public List<Procedure> handle(ResultSet rs, Statement stmnt) throws SQLException {
                        final List<Procedure> procs = new LinkedList<>();
                        while (rs.next()) {
                            procs.add(ProcedureMapper.mapProcedure(rs));
                        }
                        return procs;
                    }
                });
        return procedures;
    }

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Procedure.class;
    }

}
