package com.bocsoft.callforcode.service;

import com.bocsoft.callforcode.common.BCSDKException;

public interface JMSService {
	public void doService(String msg) throws BCSDKException;
}
