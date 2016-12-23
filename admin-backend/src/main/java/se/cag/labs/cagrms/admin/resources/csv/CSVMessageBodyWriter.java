package se.cag.labs.cagrms.admin.resources.csv;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Jackson provides MessageBodyWriters for several formats, but it does not provide an out of the box solution for CSV.
 * This is a MessageBodyWriter that allows to output a List of objects as CSV from a JAX-RS webservice.
 */
@Provider
@Produces("text/csv")
public class CSVMessageBodyWriter implements MessageBodyWriter {


    @Override
    public boolean isWriteable(Class aClass, Type type, Annotation[] annotations, MediaType mediaType) {

        boolean ret = List.class.isAssignableFrom(aClass);
        return ret;
    }

    @Override
    public long getSize(Object o, Class aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return 0;
    }

    @Override
    public void writeTo(Object data, Class aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {

        List list = (List) data;

        if (list!= null && list.size() > 0) {
            CsvMapper mapper = new CsvMapper();
            Object o = list.get(0);
            CsvSchema schema = mapper.schemaFor(o.getClass()).withHeader();
            mapper.writer(schema).writeValue(outputStream,list);
        }
    }
}