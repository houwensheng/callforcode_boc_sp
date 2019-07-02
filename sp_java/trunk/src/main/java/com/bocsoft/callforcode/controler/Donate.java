package com.bocsoft.callforcode.controler;

import static java.lang.String.format;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bocsoft.callforcode.service.DonateService;
import com.bocsoft.callforcode.vo.HttpCommonResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(tags="客户发起捐赠")
public class Donate {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DonateService donateService;
	
	@PostMapping("/donate")
	@ApiOperation(value="客户发起捐赠", consumes="application/x-www-form-urlencoded;charset=UTF8")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="form", name = "identityID", value = "身份证号", required = true, dataType="String", example="320926195511175276"),
		@ApiImplicitParam(paramType="form", name = "channel", value = "渠道", required = true, dataType="String", example="MOBILE BANKING"),
		@ApiImplicitParam(paramType="form", name = "uuid", value = "交易流水号", required = true, dataType="String", example="123456"),
		@ApiImplicitParam(paramType="form", name = "sort", value = "捐赠方式", required = true, dataType="String", example="credit"),
		@ApiImplicitParam(paramType="form", name = "amount", value = "金额", required = true, dataType="String", example="10000")
	})
	public @ResponseBody HttpCommonResponse queryAccount(@RequestParam String identityID,
			@RequestParam String channel,
			@RequestParam String uuid,
			@RequestParam String sort,
			@RequestParam String amount) {
		
		logger.info(format("Accept HTTP request of donate, with identityID [%s] channel [%s]", identityID, channel));
		
		HttpCommonResponse httpCommonResponse = new HttpCommonResponse();
        
		httpCommonResponse = donateService.donateService(identityID, channel, uuid, sort, amount);
        
		return httpCommonResponse;
	}

}
