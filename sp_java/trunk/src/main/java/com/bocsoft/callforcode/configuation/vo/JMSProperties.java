package com.bocsoft.callforcode.configuation.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jms")
public class JMSProperties {
	@NotNull
	private Boolean sessionTransacted;
	
	@NotNull
	private Boolean useCCDT;
	
	private String ccdtUrl;
	
	
	@Pattern(regexp="\\d+-\\d+")
	private String concurrency;
	
	public Boolean getSessionTransacted() {
		return sessionTransacted;
	}
	public void setSessionTransacted(Boolean sessionTransacted) {
		this.sessionTransacted = sessionTransacted;
	}
	public String getConcurrency() {
		return concurrency;
	}
	public void setConcurrency(String concurrency) {
		this.concurrency = concurrency;
	}
	public Boolean getUseCCDT() {
		return useCCDT;
	}
	public void setUseCCDT(Boolean useCCDT) {
		this.useCCDT = useCCDT;
	}
	public String getCcdtUrl() {
		return ccdtUrl;
	}
	public void setCcdtUrl(String ccdtUrl) {
		this.ccdtUrl = ccdtUrl;
	}
	
}
