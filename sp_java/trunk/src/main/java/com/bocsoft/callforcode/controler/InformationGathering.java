package com.bocsoft.callforcode.controler;

import static java.lang.String.format;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bocsoft.callforcode.service.InformationGatheringService;
import com.bocsoft.callforcode.vo.HttpCommonResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(tags="信息收集-资产/流水/产品使用情况等")
public class InformationGathering {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private InformationGatheringService informationGatheringService;
	
	@PostMapping("/infogathering")
	@ApiOperation(value="信息收集，可用于资产/流水/产品使用等信息的收集", consumes="application/x-www-form-urlencoded;charset=UTF8")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="form", name = "identityID", value = "身份证号", required = true, dataType="String", example="320926195511175276"),
		@ApiImplicitParam(paramType="form", name = "channel", value = "渠道", required = true, dataType="String", example="MOBILE BANKING"),
		@ApiImplicitParam(paramType="form", name = "info", value = "信息", required = true, dataType="String", example="{\"asset\":\"10000.00\"}")
	})
	public @ResponseBody HttpCommonResponse queryAccount(@RequestParam String identityID,
			@RequestParam String channel,
			@RequestParam String info) {
		
		logger.info(format("Accept HTTP request of InformationGathering, with identityID [%s] channel [%s]", identityID, channel));
		
		HttpCommonResponse httpCommonResponse = new HttpCommonResponse();
        
		httpCommonResponse = informationGatheringService.infoService(identityID, channel,info);
        
		return httpCommonResponse;
	}

}
