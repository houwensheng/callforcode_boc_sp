package com.bocsoft.callforcode.configuation.vo;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "appConfig")
public class ApplicationConfigProperties {

	@Pattern(regexp="redis|oracle")
	private String duplicationCheckBy;
	
	@NotNull
	private String instanceFor;
	
	@NotNull
	private String keyPairsParentPath;
	
	@NotNull
	private Boolean redisCacheUsePrefix;
	
	private List<String> cacheNames;

	public Boolean getRedisCacheUsePrefix() {
		return redisCacheUsePrefix;
	}

	public void setRedisCacheUsePrefix(Boolean redisCacheUsePrefix) {
		this.redisCacheUsePrefix = redisCacheUsePrefix;
	}

	public String getDuplicationCheckBy() {
		return duplicationCheckBy;
	}

	public void setDuplicationCheckBy(String duplicationCheckBy) {
		this.duplicationCheckBy = duplicationCheckBy;
	}

	public List<String> getCacheNames() {
		return cacheNames;
	}

	public void setCacheNames(List<String> cacheNames) {
		this.cacheNames = cacheNames;
	}

	public String getInstanceFor() {
		return instanceFor;
	}

	public void setInstanceFor(String instanceFor) {
		this.instanceFor = instanceFor;
	}

	public String getKeyPairsParentPath() {
		return keyPairsParentPath;
	}

	public void setKeyPairsParentPath(String keyPairsParentPath) {
		this.keyPairsParentPath = keyPairsParentPath;
	}
}
