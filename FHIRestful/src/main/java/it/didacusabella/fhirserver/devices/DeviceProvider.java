package it.didacusabella.fhirserver.devices;

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
import org.hl7.fhir.r4.model.Device;
import org.hl7.fhir.r4.model.IdType;

/**
 *
 * @author didacus
 */
public class DeviceProvider extends RestfulProvider<Device> {

    public DeviceProvider(DataSource ds) {
        super(ds);
    }

    @Override
    public Device getResourceById(IdType id) throws SQLException {
        Device device = new JdbcSession(ds)
                .sql("SELECT * FROM [dbo].[devices] WHERE id = ?")
                .set(id.getId())
                .select(new Outcome<Device>() {
                    @Override
                    public Device handle(ResultSet rs, Statement stmnt) throws SQLException {
                        final Device dvs = (rs.next()) ? DeviceMapper.mapDevice(rs) : null;
                        return dvs;
                    }
                });
        return device;
    }

    @Override
    public List<Device> getAll() throws SQLException {
        List<Device> devices = new JdbcSession(ds)
                .sql("SELECT * FROM [dbo].[devices]")
                .select(new Outcome<List<Device>>() {
                    @Override
                    public List<Device> handle(ResultSet rs, Statement stmnt) throws SQLException {
                        final List<Device> dvcs = new LinkedList<>();
                        while (rs.next()) {
                            dvcs.add(DeviceMapper.mapDevice(rs));
                        }
                        return dvcs;
                    }
                });
        return devices;
    }
    
    @Search
    public List<Device> findByPatient(@RequiredParam(name = Device.SP_IDENTIFIER) StringParam encounter) throws SQLException {
         List<Device> devices = new JdbcSession(ds)
                .sql("SELECT * FROM [dbo].[devices] WHERE encounter = ?")
                .set(encounter.getValue())
                .select(new Outcome<List<Device>>() {
                    @Override
                    public List<Device> handle(ResultSet rs, Statement stmnt) throws SQLException {
                        final List<Device> dvcs = new LinkedList<>();
                        while (rs.next()) {
                            dvcs.add(DeviceMapper.mapDevice(rs));
                        }
                        return dvcs;
                    }
                });
        return devices;
    }

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Device.class;
    }

}
