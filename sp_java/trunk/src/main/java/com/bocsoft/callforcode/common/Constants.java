package com.bocsoft.callforcode.common;

import java.nio.charset.Charset;

public class Constants {
	
	public static final String DONATE_BY_CASH = "cash";
	
	public static final String DONATE_BY_CREDIT = "credit";
	
	public static final String DONATE_CREDIT_LEVERAGE = "10";
	
	public static final String DONATE_SERVICE = "donate";
	
	public static final String LOAN_SERVICE = "loan";
	
	public static final String REPAYMENT_SERVICE = "repayment";
	
	public static final String SIGN_SERVICE = "sign";
	
	public static final String INFORMATION_GATHERING_SERVICE = "infogathering";
	
	public static final String SUCCESS_RETURN_CODE = "0000";
	
	public static final String ERROR_RETURN_CODE = "1111";
	
	//第一次贷款申请
	public static final String LOAN_FOR_THE_FIRST_TIME = "001";
	
	//在自有额度之内的，非第一次贷款均使用本编码
	public static final String LOAN_FOR_THE_OTHER_TIME = "002";
	
	//超出自有额度的贷款申请
	public static final String LOAN_FOR_THE_DONATE_TIME = "003";
	//捐款成功后，奖励捐款额度100分之1的奖励额度
	public static final String DONATE_BONUS = "100";
	
	public static final Charset UTF_8 = Charset.forName("UTF-8");
	
	public static final String CHAIN_CODE_LANG_GO_LANG = "go";
	
	public static final String CHAIN_CODE_LANG_JAVA = "java";
	
	public static final String CHAIN_CODE_LANG_NODE = "node";
	
	
}
