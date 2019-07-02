package com.bocsoft.callforcode.controler;

import static java.lang.String.format;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bocsoft.callforcode.service.SignSPService;
import com.bocsoft.callforcode.vo.HttpCommonResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(tags="新用户签约")
public class SignSP {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SignSPService signSPService;
	
	@PostMapping("/sign")
	@ApiOperation(value="新用户签约", consumes="application/x-www-form-urlencoded;charset=UTF8")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="form", name = "identityID", value = "身份证号", required = true, dataType="String", example="320926195511175276"),
		@ApiImplicitParam(paramType="form", name = "channel", value = "渠道", required = true, dataType="String", example="MOBILE BANKING")
	})
	public @ResponseBody HttpCommonResponse queryAccount(@RequestParam String identityID,
			@RequestParam String channel) {
		
		logger.info(format("Accept HTTP request of SignSP, with identityID [%s] channel [%s]", identityID, channel));
		
		
		
		HttpCommonResponse httpCommonResponse = new HttpCommonResponse();
        
		httpCommonResponse = signSPService.sign(identityID, channel);
        
		return httpCommonResponse;
	}

}
