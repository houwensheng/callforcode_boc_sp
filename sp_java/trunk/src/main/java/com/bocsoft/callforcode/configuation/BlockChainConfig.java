package com.bocsoft.callforcode.configuation;


import static org.hyperledger.fabric.sdk.Channel.PeerOptions.createPeerOptions;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.EnumSet;
import java.util.Properties;

import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.Peer.PeerRole;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.bocsoft.callforcode.configuation.vo.BlockchainConfigProperties;
import com.bocsoft.callforcode.configuation.vo.ChannelConfigProperties;
import com.bocsoft.callforcode.configuation.vo.SDKUser;

//@Configuration
//@EnableConfigurationProperties(BlockchainConfigProperties.class)
public class BlockChainConfig {

	@Autowired
	private BlockchainConfigProperties blockchainConfigProperties;
	
	@Value("${fabricsdk.constant.chainCodeName}")
	private String chainCodeName;
	
	@Value("${fabricsdk.constant.chainCodeVersion}")
	private String chainCodeVersion;
	
	@Primary
	@Bean(name="transUser")
	public User getTransUser() throws IOException {
		SDKUser sdkUser = blockchainConfigProperties.getTransUser();
		sdkUser.initEnrollment();
		return sdkUser;
	}
	
	@Bean
    public HFClient hfClientBL() throws CryptoException, InvalidArgumentException, IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IOException {
		HFClient hfClient = HFClient.createNewInstance();
    	hfClient.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
    	hfClient.setUserContext(getTransUser());
		return hfClient;
    }
	
	@Bean
    public HFClient hfClientQuery() throws CryptoException, InvalidArgumentException, IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IOException {
		HFClient hfClient = HFClient.createNewInstance();
    	hfClient.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
    	hfClient.setUserContext(getTransUser());
		return hfClient;
    }
	
	@Bean
    public HFClient hfClientSD() throws CryptoException, InvalidArgumentException, IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IOException {
		HFClient hfClient = HFClient.createNewInstance();
    	hfClient.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
    	hfClient.setUserContext(getTransUser());
		return hfClient;
    }
    
	@Bean
	public Channel blockListenerChannel() throws CryptoException, InvalidArgumentException, IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, TransactionException, IOException {
		HFClient client = hfClientBL();
		ChannelConfigProperties ChannelConfig = blockchainConfigProperties.getChannel();

		Channel channel = client.newChannel(ChannelConfig.getName());
		
		ChannelConfigProperties.Peer[] peers = ChannelConfig.getBlockListenerPeersConfig();
		for (int i = 0; i < peers.length; i++) {
			ChannelConfigProperties.Peer peer = peers[i];
			Properties properties = new Properties();
			properties.setProperty("pemFile", peer.getPemFile());
			properties.setProperty("sslProvider", peer.getSslProvider());
			properties.setProperty("negotiationType", peer.getNegotiationType());
			if (peer.getHostnameOverride() != null && !peer.getHostnameOverride().trim().isEmpty()) {
				properties.setProperty("hostnameOverride", peer.getHostnameOverride().trim());
			}
			Peer blockListenerPeer = client.newPeer(peer.getName(), peer.getEndpoint(), properties);
			channel.addPeer(blockListenerPeer, createPeerOptions().setPeerRoles(EnumSet.of(PeerRole.EVENT_SOURCE)));
		}

        channel.initialize();
        
		return channel;
	}
	
	@Bean
	public Channel queryChannel() throws CryptoException, InvalidArgumentException, IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, TransactionException, IOException {
		HFClient client = hfClientQuery();
		ChannelConfigProperties ChannelConfig = blockchainConfigProperties.getChannel();
		Channel channel = client.newChannel(ChannelConfig.getName());
		
		ChannelConfigProperties.Peer[] peers = ChannelConfig.getQueryPeersConfig();
		for (int i = 0; i < peers.length; i++) {
			ChannelConfigProperties.Peer peer = peers[i];
			Properties properties = new Properties();
			properties.setProperty("pemFile", peer.getPemFile());
			properties.setProperty("sslProvider", peer.getSslProvider());
			properties.setProperty("negotiationType", peer.getNegotiationType());
			if (peer.getHostnameOverride() != null && !peer.getHostnameOverride().trim().isEmpty()) {
				properties.setProperty("hostnameOverride", peer.getHostnameOverride().trim());
			}
			Peer queryPeer = client.newPeer(peer.getName(), peer.getEndpoint(), properties);
			channel.addPeer(queryPeer, createPeerOptions().setPeerRoles(EnumSet.of(PeerRole.LEDGER_QUERY, PeerRole.CHAINCODE_QUERY)));
		}

        channel.initialize();
        
		return channel;
	}
	
	@Bean
	public Channel serviceDisoverChannel() throws CryptoException, InvalidArgumentException, IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, TransactionException, IOException {
		HFClient client = hfClientSD();

		ChannelConfigProperties ChannelConfig = blockchainConfigProperties.getChannel();
		Channel channel = client.newChannel(ChannelConfig.getName());
		
		ChannelConfigProperties.Peer[] peers = ChannelConfig.getServiceDiscoverPeersConfig();
		for (int i = 0; i < peers.length; i++) {
			ChannelConfigProperties.Peer peer = peers[i];
			Properties properties = new Properties();
			properties.setProperty("pemFile", peer.getPemFile());
			properties.setProperty("sslProvider", peer.getSslProvider());
			properties.setProperty("negotiationType", peer.getNegotiationType());
			if (peer.getHostnameOverride() != null && !peer.getHostnameOverride().trim().isEmpty()) {
				properties.setProperty("hostnameOverride", peer.getHostnameOverride().trim());
			}
			Peer discoveryPeer = client.newPeer(peer.getName(), peer.getEndpoint(), properties);
			channel.addPeer(discoveryPeer, createPeerOptions().setPeerRoles(EnumSet.of(PeerRole.SERVICE_DISCOVERY)));
		}
        channel.initialize();
        
		return channel;
	}
		
	@Bean
	public ChaincodeID chaincodeID() {
		return ChaincodeID.newBuilder()
				          .setName(chainCodeName)
				          .setVersion(chainCodeVersion)
				          .build();
	}
	
}
