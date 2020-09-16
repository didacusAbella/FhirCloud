package it.didacusabella.fhirserver.allergies;

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
import org.hl7.fhir.r4.model.AllergyIntolerance;
import org.hl7.fhir.r4.model.IdType;

/**
 *
 * @author didacus
 */
public class AllergyProvider extends RestfulProvider<AllergyIntolerance> {

    public AllergyProvider(DataSource ds) {
        super(ds);
    }

    @Override
    public AllergyIntolerance getResourceById(IdType id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<AllergyIntolerance> getAll() throws SQLException {
        List<AllergyIntolerance> allergies = new JdbcSession(ds)
                .sql("SELECT * FROM [dbo].[allergies]")
                .select(new Outcome<List<AllergyIntolerance>>() {
                    @Override
                    public List<AllergyIntolerance> handle(ResultSet rs, Statement stmnt) throws SQLException {
                        final List<AllergyIntolerance> allgs = new LinkedList<>();
                        while (rs.next()) {
                            allgs.add(AllergyMapper.mapAllergy(rs));
                        }
                        return allgs;
                    }

                });
        return allergies;
    }
    
    @Search
    public List<AllergyIntolerance> findByPatient(@RequiredParam(name = AllergyIntolerance.SP_PATIENT) StringParam patient) throws SQLException{
        List<AllergyIntolerance> allergies = new JdbcSession(ds)
                .sql("SELECT * FROM [dbo].[allergies] WHERE patient=?")
                .set(patient.getValue())
                .select(new Outcome<List<AllergyIntolerance>>() {
                    @Override
                    public List<AllergyIntolerance> handle(ResultSet rs, Statement stmnt) throws SQLException {
                        final List<AllergyIntolerance> allgs = new LinkedList<>();
                        while (rs.next()) {
                            allgs.add(AllergyMapper.mapAllergy(rs));
                        }
                        return allgs;
                    }

                });
        return allergies;
    }

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return AllergyIntolerance.class;
    }

}
