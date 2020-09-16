package it.didacusabella.fhirserver.careplans;

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
import org.hl7.fhir.r4.model.CarePlan;
import org.hl7.fhir.r4.model.IdType;

/**
 *
 * @author didacus
 */
public class CarePlanProvider extends RestfulProvider<CarePlan> {

    public CarePlanProvider(DataSource ds) {
        super(ds);
    }

    @Override
    public CarePlan getResourceById(IdType id) throws SQLException {
        CarePlan careplan = new JdbcSession(ds)
                .sql("SELECT * FROM [dbo].[careplans] WHERE Id=?")
                .set(id.getId())
                .select(new Outcome<CarePlan>() {
            @Override
            public CarePlan handle(ResultSet rs, Statement stmnt) throws SQLException {
             CarePlan pl = (rs.next()) ? CareplanMapper.mapCareplan(rs) : null;
             return pl;
            }
        });
        return careplan;
    }

    @Override
    public List<CarePlan> getAll() throws SQLException {
       List<CarePlan> careplans = new JdbcSession(ds)
               .sql("SELECT * FROM [dbo].[careplans]")
               .select(new Outcome<List<CarePlan>>() {
           @Override
           public List<CarePlan> handle(ResultSet rs, Statement stmnt) throws SQLException {
               final List<CarePlan> cps = new LinkedList<>();
               while(rs.next()){
                   cps.add(CareplanMapper.mapCareplan(rs));
               }
               return cps;
           }
       });
       return careplans;
    }
    
    @Search
    public List<CarePlan> findByPatient(@RequiredParam(name = CarePlan.SP_PATIENT) StringParam patient) throws SQLException {
         List<CarePlan> careplans = new JdbcSession(ds)
               .sql("SELECT * FROM [dbo].[careplans] WHERE patient=?")
                .set(patient.getValue())
               .select(new Outcome<List<CarePlan>>() {
           @Override
           public List<CarePlan> handle(ResultSet rs, Statement stmnt) throws SQLException {
               final List<CarePlan> cps = new LinkedList<>();
               while(rs.next()){
                   cps.add(CareplanMapper.mapCareplan(rs));
               }
               return cps;
           }
       });
       return careplans;
    }
    

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return CarePlan.class;
    }
    
}
