package com.bocsoft.callforcode.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bocsoft.callforcode.common.Constants;
import com.bocsoft.callforcode.dao.TempStorageDao;
import com.bocsoft.callforcode.vo.CustomerVo;
import com.bocsoft.callforcode.vo.HttpCommonResponse;

@Service
public class RepaymentService implements HTTPService {

	@Autowired
	TempStorageDao tempStorageDao;
	
	public HttpCommonResponse repaymentService(String identityID, String channel, String uuid, String loanCode, String amount) {
		
		BigDecimal repaymentAmount = new BigDecimal(amount);
		
		CustomerVo customerVo = tempStorageDao.getCustomerByID(identityID);

		//TODO 记录存入数据库
		
		HttpCommonResponse httpCommonResponse = new HttpCommonResponse();
		httpCommonResponse.setIdentityID(identityID);
		httpCommonResponse.setChannel(channel);
		httpCommonResponse.setServiceName(Constants.REPAYMENT_SERVICE);
		httpCommonResponse.setReturnCode(Constants.SUCCESS_RETURN_CODE);
		httpCommonResponse.setErrMsg("repayment success.");
		
		//TODO 调用核心交易
		//FOR SHOW
		BigDecimal loanLimitGap = customerVo.getLoanLimit().subtract(customerVo.getrLoanLimit());
		if(loanLimitGap.compareTo(new BigDecimal("0")) == 0) {
			customerVo.setCreditAmount(customerVo.getCreditAmount().add(repaymentAmount));
		}else if(loanLimitGap.compareTo(repaymentAmount) == 1) {
			customerVo.setrLoanLimit(customerVo.getrLoanLimit().add(repaymentAmount));
		}else {
			customerVo.setrLoanLimit(customerVo.getrLoanLimit().add(loanLimitGap));
			customerVo.setCreditAmount(customerVo.getCreditAmount().add(repaymentAmount.subtract(loanLimitGap)));
		}
		//FOR SHOW
		//TODO 调用区块链，更新客户额度信息
		
		return httpCommonResponse;
	}
}
