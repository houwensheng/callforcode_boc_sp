package com.bocsoft.callforcode.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description= "HTTP请求通用响应数据")
public class HttpCommonResponse {
	
	@ApiModelProperty(value = "身份证号")
	private String identityID;
	
	@ApiModelProperty(value = "渠道号")
	private String channel;
	
	@ApiModelProperty(value = "原服务名")
	private String serviceName;
	
	@ApiModelProperty(value = "状态码\r\n" + 
			"0000 – 成功\r\n" + 
			"其他值 – 失败，失败时只有errMsg有值\r\n")
	private String returnCode;
	
	@ApiModelProperty(value = "返回描述")
	private String errMsg;

	public String getIdentityID() {
		return identityID;
	}

	public void setIdentityID(String identityID) {
		this.identityID = identityID;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
