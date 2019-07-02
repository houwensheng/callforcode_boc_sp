package com.bocsoft.callforcode.common;

import static com.bocsoft.callforcode.common.Constants.UTF_8;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	private final static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	
	/**
	 * 转换对象到json串，指定字符集
	 * @param pojo
	 * @param charset
	 * @return
	 */
	public static String obj2Json(Object pojo, Charset charset) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(pojo);
		} catch (JsonProcessingException e) {
			logger.error("error on serialize object to json:", e);
			throw new BCSDKException(ExceptionEnums.JSON_SERIALIZATION_EXCEPTION, e);
		}
	}
	
	/**
	 * 转换对象到json串，字符集为UTF8
	 * @param pojo
	 * @param charset
	 * @return
	 */
	public static String obj2Json(Object pojo) {
		return obj2Json(pojo, UTF_8);
	}
	
	/**
	 * 转换json到对象
	 * @param jsonStr
	 * @param valueType
	 * @return
	 */
	public static  <T> T jsonToObj(String jsonStr, Class<T> valueType) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
	    try {
			return mapper.readValue(jsonStr, valueType);
		} catch (Exception e) {
			logger.error("error on de-serialize json to object: " + jsonStr, e);
			throw new BCSDKException(ExceptionEnums.JSON_DESERIALIZATION_EXCEPTION, e);
		}
	}
	
	/**
	 * @param jsonString
	 * @return
	 */
	public static JsonNode parseJson(String jsonString) {
		ObjectMapper mapper = new ObjectMapper();
	    try {
			JsonNode actualObj = mapper.readTree(jsonString);
			return actualObj;
		} catch (Exception e) {
			logger.error("error on de-serialize json to object: " + jsonString, e);
			throw new BCSDKException(ExceptionEnums.JSON_DESERIALIZATION_EXCEPTION, e);
		}
	}
	
	/**
	 * @param jsonString
	 * @param fieldName
	 * @return
	 */
	public static String getTextValOfFirstLevelField(String jsonString, String fieldName) {
		JsonNode node = parseJson(jsonString);
		if (node.get(fieldName) == null) {
			return null;
		}
		return node.get(fieldName).asText();
	}
}
