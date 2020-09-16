package it.didacusabella.fhirserver.conditions;


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
import org.hl7.fhir.r4.model.Condition;
import org.hl7.fhir.r4.model.IdType;

/**
 *
 * @author didacus
 */
public class ConditionProvider extends RestfulProvider<Condition> {

    public ConditionProvider(DataSource ds) {
        super(ds);
    }

    @Override
    public Condition getResourceById(IdType id) throws SQLException {
        Condition condition = new JdbcSession(ds)
                .sql("SELECT * FROM [dbo].[conditions] WHERE Id = ?")
                .set(id.getId())
                .select(new Outcome<Condition>() {
            @Override
            public Condition handle(ResultSet rs, Statement stmnt) throws SQLException {
                final Condition cnd = (rs.next()) ? ConditionMapper.mapCondition(rs) : null;
                return cnd;
            }
        });
        return condition;
    }

    @Override
    public List<Condition> getAll() throws SQLException {
        List<Condition> conditions = new JdbcSession(ds)
                .sql("SELECT * FROM [dbo].[conditions]")
                .select(new Outcome<List<Condition>>() {
            @Override
            public List<Condition> handle(ResultSet rs, Statement stmnt) throws SQLException {
                final List<Condition> cnds = new LinkedList<>();
                while(rs.next()){
                    cnds.add(ConditionMapper.mapCondition(rs));
                }
                return cnds;
            }
        });
         return conditions;
    }
    
    @Search
    public List<Condition> findByEncounter(@RequiredParam(name = Condition.SP_ENCOUNTER) StringParam encounter) throws SQLException {
         List<Condition> conditions = new JdbcSession(ds)
                .sql("SELECT * FROM [dbo].[conditions] WHERE encounter = ?")
                .set(encounter.getValue())
                .select(new Outcome<List<Condition>>() {
            @Override
            public List<Condition> handle(ResultSet rs, Statement stmnt) throws SQLException {
                final List<Condition> cnds = new LinkedList<>();
                while(rs.next()){
                    cnds.add(ConditionMapper.mapCondition(rs));
                }
                return cnds;
            }
        });
         return conditions;
    }

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Condition.class;
    }
    
}
