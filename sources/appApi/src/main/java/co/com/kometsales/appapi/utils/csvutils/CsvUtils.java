package co.com.kometsales.appapi.utils.csvutils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

/**
 * Clase utilitira para el procesamiento de CSV
 * @author ED
 *
 */
public class CsvUtils {
	
	private CsvUtils() {}
	
	/**
	 * Metodo que lee todos los datos enviados en el CSV
	 * @param clazz
	 * @param stream
	 * @return
	 * @throws IOException
	 */
	public static <T> List<T> read(Class<T> clazz, InputStream stream) throws IOException {
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = mapper.schemaFor(clazz).withoutEscapeChar().withColumnReordering(true);
		ObjectReader reader = mapper.readerFor(clazz).with(schema);
		return reader.<T>readValues(stream).readAll();
	}
}
