package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Object ↔ JSON
 *
 * @author antop
 */
public class JsonUtils {
    private static final ObjectMapper om = new Jackson2ObjectMapperBuilder().modules(new JavaTimeModule()).build();

    private JsonUtils() {
    }

    /**
     * JSON 문자열을 오브젝트로 변경
     *
     * @param json      JSON 문자열
     * @param valueType 변경할 클래스 타입
     */
    public static <T> T fromJson(String json, Class<T> valueType) throws JsonProcessingException {
        return om.readValue(json, valueType);
    }

    /**
     * 오브젝트를 JSON 문자열로 변경
     *
     * @param o 변경할 오브젝트
     * @return 변경된 문자열
     */
    public static String toJson(Object o) throws JsonProcessingException {
        return om.writeValueAsString(o);
    }
}
