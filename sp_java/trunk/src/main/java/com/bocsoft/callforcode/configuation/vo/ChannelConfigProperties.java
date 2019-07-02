package com.bocsoft.callforcode.configuation.vo;


import java.util.ArrayList;


public class ChannelConfigProperties {
	private String name;
	
	private Peer[] peers;
	
	private String[] serviceDiscoverPeers;
	private String[] queryPeers;
	private String[] blockListenerPeers;
	
	private Order[] orders;
	
	public Order[] getOrders() {
		return orders;
	}
	public void setOrders(Order[] orders) {
		this.orders = orders;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Peer[] getPeers() {
		return peers;
	}
	public void setPeers(Peer[] peers) {
		this.peers = peers;
	}
	
	public String[] getServiceDiscoverPeers() {
		return serviceDiscoverPeers;
	}
	public String[] getQueryPeers() {
		return queryPeers;
	}
	public String[] getBlockListenerPeers() {
		return blockListenerPeers;
	}
	public Peer[] getServiceDiscoverPeersConfig() {
		ArrayList<Peer> peerList = new ArrayList<Peer>();
		for (int i = 0; i < serviceDiscoverPeers.length; i++) {
			String peerName = serviceDiscoverPeers[i].trim();
			for (int j = 0; j < peers.length; j++) {
				if (peerName.equals(peers[j].getName().trim())) {
					peerList.add(peers[j]);
				}
			}
		}
		return peerList.toArray(new Peer[peerList.size()]);
	}
	public void setServiceDiscoverPeers(String[] serviceDiscoverPeers) {
		this.serviceDiscoverPeers = serviceDiscoverPeers;
	}
	public Peer[] getQueryPeersConfig() {
		ArrayList<Peer> peerList = new ArrayList<Peer>();
		for (int i = 0; i < queryPeers.length; i++) {
			String peerName = queryPeers[i].trim();
			for (int j = 0; j < peers.length; j++) {
				if (peerName.equals(peers[j].getName().trim())) {
					peerList.add(peers[j]);
				}
			}
		}
		return peerList.toArray(new Peer[peerList.size()]);
	}
	public void setQueryPeers(String[] queryPeers) {
		this.queryPeers = queryPeers;
	}
	public Peer[] getBlockListenerPeersConfig() {
		ArrayList<Peer> peerList = new ArrayList<Peer>();
		for (int i = 0; i < blockListenerPeers.length; i++) {
			String peerName = blockListenerPeers[i].trim();
			for (int j = 0; j < peers.length; j++) {
				if (peerName.equals(peers[j].getName().trim())) {
					peerList.add(peers[j]);
				}
			}
		}
		return peerList.toArray(new Peer[peerList.size()]);
	}
	public void setBlockListenerPeers(String[] blockListenerPeers) {
		this.blockListenerPeers = blockListenerPeers;
	}
	
	public static class Peer {
		private String pemFile;
		private String hostnameOverride;
		private String sslProvider;
		private String negotiationType;
		private String name;
		private String endpoint;
		public String getPemFile() {
			return pemFile;
		}
		public void setPemFile(String pemFile) {
			this.pemFile = pemFile;
		}
		public String getHostnameOverride() {
			return hostnameOverride;
		}
		public void setHostnameOverride(String hostnameOverride) {
			this.hostnameOverride = hostnameOverride;
		}
		public String getSslProvider() {
			return sslProvider;
		}
		public void setSslProvider(String sslProvider) {
			this.sslProvider = sslProvider;
		}
		public String getNegotiationType() {
			return negotiationType;
		}
		public void setNegotiationType(String negotiationType) {
			this.negotiationType = negotiationType;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getEndpoint() {
			return endpoint;
		}
		public void setEndpoint(String endpoint) {
			this.endpoint = endpoint;
		}
	}	
	public static class Order{
		private String name;
		private String endpoint;
		private String clientCertFile;
		private String clientKeyFile;
		private String pemFile;
		private String hostnameOverride;
		private String sslProvider;
		private String negotiationType;
		public String getClientCertFile() {
			return clientCertFile;
		}
		public void setClientCertFile(String clientCertFile) {
			this.clientCertFile = clientCertFile;
		}
		public String getClientKeyFile() {
			return clientKeyFile;
		}
		public void setClientKeyFile(String clientKeyFile) {
			this.clientKeyFile = clientKeyFile;
		}
		public String getPemFile() {
			return pemFile;
		}
		public void setPemFile(String pemFile) {
			this.pemFile = pemFile;
		}
		public String getHostnameOverride() {
			return hostnameOverride;
		}
		public void setHostnameOverride(String hostnameOverride) {
			this.hostnameOverride = hostnameOverride;
		}
		public String getSslProvider() {
			return sslProvider;
		}
		public void setSslProvider(String sslProvider) {
			this.sslProvider = sslProvider;
		}
		public String getNegotiationType() {
			return negotiationType;
		}
		public void setNegotiationType(String negotiationType) {
			this.negotiationType = negotiationType;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getEndpoint() {
			return endpoint;
		}
		public void setEndpoint(String endpoint) {
			this.endpoint = endpoint;
		}
		
	}
}
