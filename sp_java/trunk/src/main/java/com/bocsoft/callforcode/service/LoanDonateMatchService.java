package com.bocsoft.callforcode.service;

import java.math.BigDecimal;
import java.util.Iterator;

import static java.lang.String.format;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.bocsoft.callforcode.common.Constants;
import com.bocsoft.callforcode.common.TempStorage;
import com.bocsoft.callforcode.dao.TempStorageDao;
import com.bocsoft.callforcode.vo.CustomerVo;

@Service
public class LoanDonateMatchService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	TempStorageDao tempStorageDao;
	
	//@Scheduled(fixedRate = 5000) 上一次开始执行时间点之后5秒再执行
	//@Scheduled(cron=” /5 “) ：通过cron表达式定义规则
	@Scheduled(fixedDelay = 60000)
	public void match() {
		
		logger.info("start LoanDonateMatchService...");
		if(TempStorage.loanPool.compareTo(new BigDecimal("0")) == 0) return;
		if(TempStorage.cashPool.compareTo(new BigDecimal("0")) == 0) return;
		
		CustomerVo loanCustomer = null;
		CustomerVo donateCustomer = null;
		
		Iterator<String> iteratorLoan = TempStorage.waitLoan.iterator();
		while(iteratorLoan.hasNext()) {
			String loanID = iteratorLoan.next();
			loanCustomer = tempStorageDao.getCustomerByID(loanID);
			
			//如果贷款多于总捐赠
			if(loanCustomer.getWaitLoan().compareTo(TempStorage.cashPool) == 1) {
				logger.info("LoanDonateMatchService: not sufficient donate.");
				return;
			}
			
			Iterator<String> iteratorDonate = TempStorage.waitDonate.iterator();
			while(iteratorDonate.hasNext()) {
				String donateID = iteratorDonate.next();
				donateCustomer = tempStorageDao.getCustomerByID(donateID);
				BigDecimal loanAmount = new BigDecimal("0");
				
				//如果贷款多于捐赠
				if(loanCustomer.getWaitLoan().compareTo(donateCustomer.getWaitDonate()) == 1) {
					loanAmount = donateCustomer.getWaitDonate();
					tempStorageDao.match(loanCustomer, donateCustomer, loanAmount);
					tempStorageDao.afterDonate(donateCustomer);
					iteratorDonate.remove();
					logger.info(format("Donate success! identityID:[%s] Donate:[%s] Bonus:[%s]",
							donateCustomer.getIdentityID(),loanAmount,Constants.DONATE_BONUS));
					logger.info(format("Loan success! identityID:[%s] Loan:[%s]",
							loanCustomer.getIdentityID(),loanAmount));
				}else {//如果贷款少于等于捐赠
					loanAmount = loanCustomer.getWaitLoan();
					tempStorageDao.match(loanCustomer, donateCustomer, loanAmount);
					tempStorageDao.afterLoan(loanCustomer,loanAmount);
					iteratorLoan.remove();
					//在贷款等于捐赠的情况下，此时捐赠客户已经完成捐赠
					if(donateCustomer.getWaitDonate().compareTo(new BigDecimal("0")) == 0) {//该捐款客户任务完成
						tempStorageDao.afterDonate(donateCustomer);
						iteratorDonate.remove();
					}
					logger.info(format("Donate success! identityID:[%s] Donate:[%s] Bonus:[%s]",
							donateCustomer.getIdentityID(),loanAmount,Constants.DONATE_BONUS));
					logger.info(format("Loan success! identityID:[%s] Loan:[%s]",
							loanCustomer.getIdentityID(),loanAmount));
					break;
				}
			}
		}
	}

}
