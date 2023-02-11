package virtusa.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataReader {
	public List<HashMap<String, String>> getJsonDataMap(String filePath) throws IOException
	{
		//read json to string
		String jsonContent = FileUtils.readFileToString(
				new File(filePath),StandardCharsets.UTF_8);
		
		//convert string to hashmap -> Jackson databind jar is used to do this. This jar can be available in mvn repository.
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>(){});
		return data;
	}
}
