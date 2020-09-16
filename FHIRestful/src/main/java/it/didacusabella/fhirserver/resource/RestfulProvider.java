package it.didacusabella.fhirserver.resource;

import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.server.IResourceProvider;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.IdType;

/**
 *
 * @author didacus
 * @param <T>
 */
public abstract class RestfulProvider<T extends IBaseResource> implements IResourceProvider {
    
    protected final DataSource ds;

    public RestfulProvider(DataSource ds) {
        this.ds = ds;
    }
    
    @Read()
    public abstract T getResourceById(@IdParam IdType id) throws SQLException;
    
    @Search
    public abstract List<T> getAll() throws SQLException;
    
}
