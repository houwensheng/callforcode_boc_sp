package com.bocsoft.callforcode.vo;

import java.math.BigDecimal;

/**
 * 客户信息类
 * @author hou
 *
 */
public class CustomerVo {
	
	//身份证号
	private String identityID;
	//原信用额度
	private BigDecimal creditAmountOrig;
	//信用额度
	private BigDecimal creditAmount;
	//捐款奖励
	private BigDecimal donateBonus;
	//签约渠道
	private String channel;
	//待捐赠金额
	private BigDecimal waitDonate;
	//待贷款金额
	private BigDecimal waitLoan;
	//超额度后的贷款申请限额
	private BigDecimal loanLimit;
	//超额度后的贷款申请限额(实时),rLoanLimit=rLoanLimit-loanAmount
	private BigDecimal rLoanLimit;
	
	public BigDecimal getCreditAmountOrig() {
		return creditAmountOrig;
	}
	public void setCreditAmountOrig(BigDecimal creditAmountOrig) {
		this.creditAmountOrig = creditAmountOrig;
	}
	public BigDecimal getDonateBonus() {
		return donateBonus;
	}
	public void setDonateBonus(BigDecimal donateBonus) {
		this.donateBonus = donateBonus;
	}
	public BigDecimal getLoanLimit() {
		return loanLimit;
	}
	public void setLoanLimit(BigDecimal loanLimit) {
		this.loanLimit = loanLimit;
	}
	public BigDecimal getrLoanLimit() {
		return rLoanLimit;
	}
	public void setrLoanLimit(BigDecimal rLoanLimit) {
		this.rLoanLimit = rLoanLimit;
	}
	public BigDecimal getWaitDonate() {
		return waitDonate;
	}
	public void setWaitDonate(BigDecimal waitDonate) {
		this.waitDonate = waitDonate;
	}
	public BigDecimal getWaitLoan() {
		return waitLoan;
	}
	public void setWaitLoan(BigDecimal waitLoan) {
		this.waitLoan = waitLoan;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getIdentityID() {
		return identityID;
	}
	public void setIdentityID(String identityID) {
		this.identityID = identityID;
	}
	public BigDecimal getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}
	
	
}
