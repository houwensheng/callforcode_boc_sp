package com.bocsoft.callforcode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bocsoft.callforcode.common.Constants;
import com.bocsoft.callforcode.common.JsonUtil;
import com.bocsoft.callforcode.dao.TempStorageDao;
import com.bocsoft.callforcode.vo.CusInfoVo;
import com.bocsoft.callforcode.vo.HttpCommonResponse;

@Service
public class InformationGatheringService implements HTTPService {
	
	@Autowired
	TempStorageDao tempStorageDao;
	
	public HttpCommonResponse infoService(String identityID, String channel, String info) {
		
		//TODO 将信息存入数据库
		
		CusInfoVo cusInfo = JsonUtil.jsonToObj(info,CusInfoVo.class);
		tempStorageDao.storeInfo(identityID, cusInfo);
		
		HttpCommonResponse httpCommonResponse = new HttpCommonResponse();
		httpCommonResponse.setIdentityID(identityID);
		httpCommonResponse.setChannel(channel);
		httpCommonResponse.setServiceName(Constants.INFORMATION_GATHERING_SERVICE);
		httpCommonResponse.setReturnCode(Constants.SUCCESS_RETURN_CODE);
		httpCommonResponse.setErrMsg("InformationGathering success.");
		
		return httpCommonResponse;
	}

}
