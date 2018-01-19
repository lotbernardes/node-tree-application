package br.com.lotbernardes.tree.api.dto.utils;

import br.com.lotbernardes.tree.api.exception.JsonParsingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.h2.util.StringUtils;

import java.io.IOException;

public class JsonParser {
  public static <T> T deserialize(String json, Class<T> type) throws JsonParsingException {
    try {
      if (StringUtils.isNullOrEmpty(json)) {
        throw JsonParsingException.DESERIALIZATION(new Exception("Json received for deserialization is either null or empty."));
      }

      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(json, type);
    } catch (IOException exception) {
      throw JsonParsingException.DESERIALIZATION(exception);
    }
  }

  public static String serialize(Object instance) throws JsonParsingException {
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.writeValueAsString(instance);
    } catch (IOException exception) {
      throw JsonParsingException.SERIALIZATION(exception);
    }
  }
}
