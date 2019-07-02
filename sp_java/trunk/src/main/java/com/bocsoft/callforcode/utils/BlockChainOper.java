package com.bocsoft.callforcode.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bocsoft.callforcode.common.BCSDKException;
import com.bocsoft.callforcode.common.Constants;
import com.bocsoft.callforcode.common.ExceptionEnums;
import com.bocsoft.callforcode.vo.RequestBaseVo;

import static java.lang.String.format;
import static org.hyperledger.fabric.sdk.Channel.DiscoveryOptions.createDiscoveryOptions;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.QueryByChaincodeRequest;
import org.hyperledger.fabric.sdk.ServiceDiscovery;
import org.hyperledger.fabric.sdk.TransactionProposalRequest;
import org.hyperledger.fabric.sdk.TransactionRequest;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.ServiceDiscoveryException;

//@Component
public class BlockChainOper {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Channel serviceDisoverChannel;
	
	private Channel queryChannel;
		
	private HFClient hfClientSD;
	
	private HFClient hfClientQuery;
	
	private Long proposalWaitTime;
	
	private String chainCodeLanguageStr;
	
	private ChaincodeID chaincodeID;
	
	private TransactionRequest.Type chainCodeLanguage;
	
	public BlockChainOper(HFClient hfClientSD, HFClient hfClientQuery,Channel serviceDisoverChannel, Channel queryChannel, Long proposalWaitTime, String chainCodeLanguageStr, ChaincodeID chaincodeID) {
		this.hfClientSD = hfClientSD;
		this.hfClientQuery = hfClientQuery;
		this.serviceDisoverChannel = serviceDisoverChannel;
		this.queryChannel = queryChannel;
		this.proposalWaitTime = proposalWaitTime;
		this.chainCodeLanguageStr = chainCodeLanguageStr;
		this.chaincodeID = chaincodeID;
		initChainCodeLang();
	}
	
	private void initChainCodeLang() {
		if (chainCodeLanguageStr.equals(Constants.CHAIN_CODE_LANG_GO_LANG)) {
        	chainCodeLanguage = TransactionRequest.Type.GO_LANG;
		} else if(chainCodeLanguageStr.equals(Constants.CHAIN_CODE_LANG_JAVA)) {
			chainCodeLanguage = TransactionRequest.Type.JAVA;
		} else if(chainCodeLanguageStr.equals(Constants.CHAIN_CODE_LANG_NODE)) {
			chainCodeLanguage = TransactionRequest.Type.NODE;
		} else {
			throw new BCSDKException(ExceptionEnums.CONFIG_ERROR_CHAINCODE_LANG);
		}
	}
	
	public void invoke(String function, RequestBaseVo requestVo) {
        TransactionProposalRequest transactionProposalRequest = hfClientSD.newTransactionProposalRequest();
        transactionProposalRequest.setChaincodeID(chaincodeID);
        if (chainCodeLanguage == null) {
        	initChainCodeLang();
		}
        transactionProposalRequest.setChaincodeLanguage(chainCodeLanguage);
        //transactionProposalRequest.setFcn(function);
        transactionProposalRequest.setFcn("invoke");
        transactionProposalRequest.setProposalWaitTime(proposalWaitTime);
        ArrayList<String> args = requestVo.getArgs();
        args.add(0, function);
        transactionProposalRequest.setArgs(args);
        logger.info(format("send invoke to chaincode,with function [%s],args [%s]",function,args.toString()));
//        Map<String, byte[]> tm2 = new HashMap<>();
//        tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8)); //Just some extra junk in transient map
//        tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8)); // ditto
//        tm2.put("result", ":)".getBytes(UTF_8));  // This should be returned see chaincode why.
////        tm2.put(EXPECTED_EVENT_NAME, EXPECTED_EVENT_DATA);  //This should trigger an event see chaincode why.
//        try {
//			transactionProposalRequest.setTransientMap(tm2);
//		} catch (InvalidArgumentException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
        
        
        try {
			Collection<ProposalResponse> transactionPropResp =
					serviceDisoverChannel.sendTransactionProposalToEndorsers(transactionProposalRequest,
			                createDiscoveryOptions().setEndorsementSelector(ServiceDiscovery.EndorsementSelector.ENDORSEMENT_SELECTION_RANDOM)
			                        .setForceDiscovery(false));
			serviceDisoverChannel.sendTransaction(transactionPropResp);
		} catch (ProposalException | InvalidArgumentException | ServiceDiscoveryException e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			throw new BCSDKException(ExceptionEnums.CHAIN_CODE_INVOKE_EXCEPTION, e);
		}
	}

	public String query(String function, RequestBaseVo requestVo) {

        Collection<Peer> peersCollection = queryChannel.getPeers();
        Peer[] peersArray = peersCollection.toArray(new Peer[peersCollection.size()]);
        String data = null;
        
        ArrayList<String> args = requestVo.getArgs();
        args.add(0, function);
        
        for (int i = 0; i < peersArray.length; i++) {
			Peer peer = peersArray[i];
			ArrayList<Peer> peers = new ArrayList<Peer>();
			peers.add(peer);
			try {
		        QueryByChaincodeRequest queryByChaincodeRequest = hfClientQuery.newQueryProposalRequest();
		        //ArrayList<String> args = requestVo.getArgs();
		        //args.add(0, function);
		        queryByChaincodeRequest.setArgs(args);
		        queryByChaincodeRequest.setFcn("query");
		        queryByChaincodeRequest.setChaincodeID(chaincodeID);
		        queryByChaincodeRequest.setProposalWaitTime(proposalWaitTime);

		        logger.info(format("send query to chaincode,with function [%s],args [%s]",function,args.toString()));
//		        Map<String, byte[]> tm2 = new HashMap<>();
//		        tm2.put("HyperLedgerFabric", "QueryByChaincodeRequest:JavaSDK".getBytes(UTF_8));
//		        tm2.put("method", "QueryByChaincodeRequest".getBytes(UTF_8));
//		        try {
//					queryByChaincodeRequest.setTransientMap(tm2);
//				} catch (InvalidArgumentException e) {
//				}
				Collection<ProposalResponse> queryProposalResponses = queryChannel.queryByChaincode(queryByChaincodeRequest, peers);
				if (queryProposalResponses != null) {
//			        ProposalResponse proposalResponse = queryProposalResponses.iterator().next();
					for (ProposalResponse proposalResponse : queryProposalResponses) {
			            if ((!proposalResponse.isVerified()) || proposalResponse.getStatus() != ProposalResponse.Status.SUCCESS) {
			                String msg = String.format("Failed query proposal from peer %s status: %s. Messages: %s. Was verified : %s",
			                        					proposalResponse.getPeer().getName(),
			                        					proposalResponse.getStatus(),
			                        					proposalResponse.getMessage(),
			                        					proposalResponse.isVerified());
			                logger.warn(msg);
			                
			                if(proposalResponse.getMessage().contains("content is empty")) {
			                	return null;
			                }
			                
			            } else {
			            	data = proposalResponse.getProposalResponse().getResponse().getPayload().toStringUtf8();
			                logger.debug("Query payload from peer: " + proposalResponse.getPeer().getName());
			                logger.debug("TransactionID: " + proposalResponse.getTransactionID());
			                logger.debug("payload: " + data);
			            }
			        }
				}
			} catch (Exception e) {
				logger.warn("Failed query proposal:", e);
			}
			if (data != null) {
				break;
			}
		}
        if (data == null) {
        	throw new BCSDKException(ExceptionEnums.CHAIN_CODE_INVOKE_EXCEPTION);
		}
		return data;
	}
}
