package com.bocsoft.callforcode.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.bocsoft.callforcode.common.TempStorage;
import com.bocsoft.callforcode.dao.TempStorageDao;
import com.bocsoft.callforcode.utils.WatsonOper;
import com.bocsoft.callforcode.vo.CusInfoVo;

public class CreditEvaluate {
	
	@Autowired
	WatsonOper watsonOper;
	@Autowired
	TempStorageDao tempStorageDao;
	
	@Scheduled(cron = "0 0/3 * * * ?") //  30 10 1 20 * ? 每月20号1点10分30秒触发任务
	public void evaluate() {
		
		if(TempStorage.informations.size() == 0) return;
		
		Iterator<Map.Entry<String, Object>> iterator = TempStorage.informations.entrySet().iterator();
		String evaluate = "";
		while(iterator.hasNext()) {
			 Map.Entry<String, Object> entry = iterator.next();
			 String identityID = entry.getKey();
			 CusInfoVo cusInfo = (CusInfoVo)entry.getValue();
			 
			 try {
				 evaluate = watsonOper.predict(cusInfo.getAsset(), cusInfo.getTransfreq(), cusInfo.getCredit());
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
			 
			 tempStorageDao.updateCusCredit(identityID, evaluate);
		}
	}

}
