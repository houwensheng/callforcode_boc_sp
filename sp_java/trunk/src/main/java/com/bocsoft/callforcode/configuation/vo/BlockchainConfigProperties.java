package com.bocsoft.callforcode.configuation.vo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "blockchain")
public class BlockchainConfigProperties {
	private ChannelConfigProperties channel;
	private SDKUser transUser;

	public ChannelConfigProperties getChannel() {
		return channel;
	}
	public void setChannel(ChannelConfigProperties channel) {
		this.channel = channel;
	}
	public SDKUser getTransUser() {
		return transUser;
	}
	public void setTransUser(SDKUser transUser) {
		this.transUser = transUser;
	}
}
