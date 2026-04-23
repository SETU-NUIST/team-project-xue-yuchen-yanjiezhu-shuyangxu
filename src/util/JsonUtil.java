package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 实现对象与JSON文件的序列化/反序列化
 */
public class JsonUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // 确保数据目录存在
    static {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }

    /**
     * 将对象写入JSON文件
     */
    public static <T> void writeObjectToFile(T obj, String filePath) {
        File file = new File(filePath);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            OBJECT_MAPPER.writeValue(fos, obj);
        } catch (IOException e) {
            throw new RuntimeException("写入JSON文件失败：" + filePath, e);
        }
    }


    public static <T> T readObjectFromFile(String filePath, Class<T> clazz) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        try (FileInputStream fis = new FileInputStream(file)) {
            return OBJECT_MAPPER.readValue(fis, clazz);
        } catch (IOException e) {
            throw new RuntimeException("读取JSON文件失败：" + filePath, e);
        }
    }

    /**
     * 从JSON文件读取List集合
     */
    public static <T> List<T> readListFromFile(String filePath, Class<T> clazz) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        try (FileInputStream fis = new FileInputStream(file)) {
            JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(List.class, clazz);
            return OBJECT_MAPPER.readValue(fis, javaType);
        } catch (IOException e) {
            throw new RuntimeException("读取JSON文件(List)失败：" + filePath, e);
        }
    }

    /**
     * 对象转JSON字符串
     */
    public static String toJsonString(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("对象转JSON失败", e);
        }
    }
}
