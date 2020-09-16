package it.didacusabella.fhirserver.resource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author didacus
 */
public class ResourceMapper {
    
    protected static String extractString(ResultSet rs, String label) throws SQLException {
        String result = rs.getString(label);
        return (rs.wasNull()) ? "" : result;
    }
    
    protected static Date extractDate(ResultSet rs, String label) throws SQLException {
        Date result = rs.getDate(label);
        return (rs.wasNull()) ? null : result;
    }
}
