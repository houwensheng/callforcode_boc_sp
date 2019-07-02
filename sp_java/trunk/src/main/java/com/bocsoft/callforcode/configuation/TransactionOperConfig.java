package com.bocsoft.callforcode.configuation;


import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bocsoft.callforcode.utils.BlockChainOper;

//@Configuration
public class TransactionOperConfig {
	@Autowired
	@Qualifier("hfClientSD")
	private HFClient hfClientSD;
	
	@Autowired
	@Qualifier("hfClientQuery")
	private HFClient hfClientQuery;
	
	@Autowired
	@Qualifier("serviceDisoverChannel")
	private Channel serviceDisoverChannel;
	
	@Autowired
	@Qualifier("queryChannel")
	private Channel queryChannel;
	
	@Autowired
	private ChaincodeID chaincodeID;
	
	@Value("${fabricsdk.constant.proposalWaitTime}")
	private Long proposalWaitTime;
	
	@Value("${fabricsdk.constant.chainCodeLanguage}")
	private String chainCodeLanguageStr;
	
	@Bean
	public BlockChainOper bcTransactionOper() {
		return new BlockChainOper(hfClientSD, hfClientQuery, serviceDisoverChannel, queryChannel, proposalWaitTime, chainCodeLanguageStr, chaincodeID);
	}

}
