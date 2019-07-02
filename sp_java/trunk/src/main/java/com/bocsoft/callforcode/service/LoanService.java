package com.bocsoft.callforcode.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bocsoft.callforcode.common.Constants;
import com.bocsoft.callforcode.dao.TempStorageDao;
import com.bocsoft.callforcode.vo.CustomerVo;
import com.bocsoft.callforcode.vo.HttpCommonResponse;

/**
 * 贷款申请处理，由渠道完成客户身份验证
 * @author hou
 *
 */
@Service
public class LoanService implements HTTPService {
	
	@Autowired
	TempStorageDao tempStorageDao;

	public HttpCommonResponse loanService(String identityID, String channel, String uuid, String loanCode, String amount) {
		
		BigDecimal loanAmount = new BigDecimal(amount);
		
		CustomerVo customerVo = tempStorageDao.getCustomerByID(identityID);
		//如果是第一次贷款，无需审批，直接放款。审批放在前端完成。
		
		//TODO 记录存入数据库
		
		HttpCommonResponse httpCommonResponse = new HttpCommonResponse();
		httpCommonResponse.setIdentityID(identityID);
		httpCommonResponse.setChannel(channel);
		httpCommonResponse.setServiceName(Constants.LOAN_SERVICE);
		httpCommonResponse.setReturnCode(Constants.SUCCESS_RETURN_CODE);
		httpCommonResponse.setErrMsg("loan request accept,waiting for match...");
		
		//FOR SHOW
		if(Constants.LOAN_FOR_THE_FIRST_TIME.equals(loanCode)) {
			customerVo.setCreditAmount(customerVo.getCreditAmount().subtract(loanAmount));
			httpCommonResponse.setErrMsg("loan success.");
			//TODO 调用核心，调用区块链
			
			return httpCommonResponse;
		}
		
		if(Constants.LOAN_FOR_THE_OTHER_TIME.equals(loanCode)) {
			if(loanAmount.compareTo(customerVo.getCreditAmount()) == -1) {
				customerVo.setCreditAmount(customerVo.getCreditAmount().subtract(loanAmount));
				httpCommonResponse.setErrMsg("loan success.");
				//TODO 调用核心，调用区块链
				
				return httpCommonResponse;
			}else {
				httpCommonResponse.setReturnCode(Constants.ERROR_RETURN_CODE);
				httpCommonResponse.setErrMsg("loan request error, not sufficient credit amount...");
				return httpCommonResponse;
			}
		}
		
		if(Constants.LOAN_FOR_THE_DONATE_TIME.equals(loanCode)) {
			if(loanAmount.compareTo(customerVo.getrLoanLimit()) != 1) {
				tempStorageDao.loan(identityID, loanAmount);
				customerVo.setWaitLoan(loanAmount);
			}else {
				httpCommonResponse.setReturnCode(Constants.ERROR_RETURN_CODE);
				httpCommonResponse.setErrMsg("loan request error, not sufficient credit amount...");
			}
			
		}
		//FOR SHOW
		
		return httpCommonResponse;
	}
}
