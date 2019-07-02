package com.bocsoft.callforcode.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.bocsoft.callforcode.common.Constants;
import com.bocsoft.callforcode.common.TempStorage;
import com.bocsoft.callforcode.vo.CustomerVo;
import com.bocsoft.callforcode.vo.HttpCommonResponse;

@Service
public class SignSPService implements HTTPService {
	
	public HttpCommonResponse sign(String identityID,String channel) {
		
		String initCreditAmount = "2000";
		String initLoanLimit = "10000";
		//TODO 添加新用户信息写入数据库
		CustomerVo customerVo = new CustomerVo();
		customerVo.setIdentityID(identityID);
		customerVo.setCreditAmount(new BigDecimal(initCreditAmount));
		customerVo.setCreditAmountOrig(new BigDecimal(initCreditAmount));
		customerVo.setChannel(channel);
		customerVo.setLoanLimit(new BigDecimal(initLoanLimit));
		customerVo.setrLoanLimit(new BigDecimal(initLoanLimit));
		customerVo.setDonateBonus(new BigDecimal("0"));
		
		TempStorage.customers.put(identityID, customerVo);
		
		//TODO 新用户信息写入区块链，给予初始额度
		
		HttpCommonResponse httpCommonResponse = new HttpCommonResponse();
		httpCommonResponse.setIdentityID(identityID);
		httpCommonResponse.setChannel(channel);
		httpCommonResponse.setServiceName(Constants.SIGN_SERVICE);
		httpCommonResponse.setReturnCode(Constants.SUCCESS_RETURN_CODE);
		httpCommonResponse.setErrMsg("sign success");
		
		return httpCommonResponse;
		
	}

}
