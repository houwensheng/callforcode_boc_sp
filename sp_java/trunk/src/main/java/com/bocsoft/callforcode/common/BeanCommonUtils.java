package com.bocsoft.callforcode.common;


import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class BeanCommonUtils {

	public static void copyProperties(Object dest, Object orig) {

		try {
			BeanUtils.copyProperties(dest, orig);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new BCSDKException(ExceptionEnums.BEAN_COPY_EXCEPTION,e);
		}
		
	}
	
}
