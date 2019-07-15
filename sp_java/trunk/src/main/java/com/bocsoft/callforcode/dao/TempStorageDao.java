package com.bocsoft.callforcode.dao;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.bocsoft.callforcode.common.Constants;
import com.bocsoft.callforcode.common.TempStorage;
import com.bocsoft.callforcode.vo.CustomerVo;

@Component
public class TempStorageDao {
	
	
	public CustomerVo getCustomerByID(String identityID) {
		return (CustomerVo)TempStorage.customers.get(identityID);
	}
	
	public void storeInfo(String identityID, Object info) {
		TempStorage.informations.put(identityID, info);
	}
	
	public void updateCusCredit(String identityID, String credit) {
		CustomerVo customer = (CustomerVo)TempStorage.customers.get(identityID);
		BigDecimal gap = customer.getCreditAmountOrig().subtract(customer.getCreditAmount());
		customer.setCreditAmountOrig(new BigDecimal(credit));
		customer.setCreditAmount((new BigDecimal(credit)).subtract(gap));
	}
	
	public void donate(String identityID, BigDecimal donateAmount) {
		TempStorage.waitDonate.add(identityID);
		TempStorage.cashPool = TempStorage.cashPool.add(donateAmount);
	}
	
	public void loan(String identityID, BigDecimal loanAmount) {
		TempStorage.waitLoan.add(identityID);
		TempStorage.loanPool = TempStorage.loanPool.add(loanAmount);
	}
	
	public void match(CustomerVo loanCustomer, CustomerVo donateCustomer, BigDecimal loanAmount) {
		
		loanCustomer.setWaitLoan(loanCustomer.getWaitLoan().subtract(loanAmount));
		donateCustomer.setWaitDonate(donateCustomer.getWaitDonate().subtract(loanAmount));
		
		TempStorage.cashPool = TempStorage.cashPool.subtract(loanAmount);
		TempStorage.loanPool = TempStorage.loanPool.subtract(loanAmount);
	}
	
	public void afterDonate(CustomerVo customerVo) {
		//TempStorage.waitDonate.remove(customer.getIdentityID());
		//捐款成功奖励
		customerVo.setCreditAmount(customerVo.getCreditAmount().divide(new BigDecimal(Constants.DONATE_BONUS)));
	}
	
	public void afterLoan(CustomerVo customerVo, BigDecimal loanAmount) {
		//TempStorage.waitLoan.remove(customer.getIdentityID());
		customerVo.setrLoanLimit(customerVo.getrLoanLimit().subtract(loanAmount));
	}
	

}
