package it.didacusabella.fhirserver.imagingstudies;

import ca.uhn.fhir.rest.annotation.RequiredParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.api.MethodOutcome;
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
import org.hl7.fhir.r4.model.ImagingStudy;

/**
 *
 * @author didacus
 */
public class ImagingStudyProvider extends RestfulProvider<ImagingStudy> {

    public ImagingStudyProvider(DataSource ds) {
        super(ds);
    }

    @Override
    public ImagingStudy getResourceById(IdType id) throws SQLException {
        ImagingStudy study = new JdbcSession(ds)
                .sql("SELECT * FROM [dbo].[imagingstudies] LEFT JOIN ON [dbo].[imagingdetails] ON Id = imaging WHERE Id = ?")
                .set(id.getId())
                .select(new Outcome<ImagingStudy>() {
                    @Override
                    public ImagingStudy handle(ResultSet rs, Statement stmnt) throws SQLException {
                        final ImagingStudy is = (rs.next()) ? ImagingStudyMapper.mapStudy(rs) : null;
                        return is;
                    }
                });
        return study;
    }


    @Override
    public List<ImagingStudy> getAll() throws SQLException {
        List<ImagingStudy> studies = new JdbcSession(ds)
                .sql("SELECT * FROM [dbo].[imagingstudies] LEFT JOIN [dbo].[imagingdetails] ON Id = imaging")
                .select(new Outcome<List<ImagingStudy>>() {
                    @Override
                    public List<ImagingStudy> handle(ResultSet rs, Statement stmnt) throws SQLException {
                        final List<ImagingStudy> sts = new LinkedList<>();
                        while (rs.next()) {
                            sts.add(ImagingStudyMapper.mapStudy(rs));
                        }
                        return sts;
                    }
                });
        return studies;
    }
    
    @Search
    public List<ImagingStudy> findByPatient(@RequiredParam(name = ImagingStudy.SP_PATIENT) StringParam patient) throws SQLException {
        List<ImagingStudy> studies = new JdbcSession(ds)
                .sql("SELECT * FROM [dbo].[imagingstudies] LEFT JOIN [dbo].[imagingdetails] ON Id = imaging WHERE patient = ?")
                .set(patient.getValue())
                .select(new Outcome<List<ImagingStudy>>() {
                    @Override
                    public List<ImagingStudy> handle(ResultSet rs, Statement stmnt) throws SQLException {
                        final List<ImagingStudy> sts = new LinkedList<>();
                        while (rs.next()) {
                            sts.add(ImagingStudyMapper.mapStudy(rs));
                        }
                        return sts;
                    }
                });
        return studies;
    }

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return ImagingStudy.class;
    }

}
