package com.bocsoft.callforcode.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bocsoft.callforcode.common.Constants;
import com.bocsoft.callforcode.dao.TempStorageDao;
import com.bocsoft.callforcode.vo.CustomerVo;
import com.bocsoft.callforcode.vo.HttpCommonResponse;

@Service
public class DonateService implements HTTPService {
	
	@Autowired
	TempStorageDao tempStorageDao;

	public HttpCommonResponse donateService(String identityID, String channel, String uuid, String sort, String amount) {
		
		//客户捐赠的资金
		BigDecimal donateAmount = new BigDecimal(amount);
		
		//TODO 调用核心交易，冻结资金
		//进入资金池，待撮合
		//TODO 记录存入数据库
		
		HttpCommonResponse httpCommonResponse = new HttpCommonResponse();
		httpCommonResponse.setIdentityID(identityID);
		httpCommonResponse.setChannel(channel);
		httpCommonResponse.setServiceName(Constants.DONATE_SERVICE);
		httpCommonResponse.setReturnCode(Constants.SUCCESS_RETURN_CODE);
		httpCommonResponse.setErrMsg("Donate accept,waiting for match...");
		
		//FOR SHOW
		CustomerVo customerVo = tempStorageDao.getCustomerByID(identityID);
		
		tempStorageDao.donate(identityID, donateAmount);
		customerVo.setWaitDonate(donateAmount);
		//FOR SHOW
		

		
		return httpCommonResponse;
	}
}
