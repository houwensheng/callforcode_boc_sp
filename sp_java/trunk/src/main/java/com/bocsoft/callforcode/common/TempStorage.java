package com.bocsoft.callforcode.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class TempStorage {
	
	public static HashMap<String,Object> customers = new HashMap<String,Object>();
	
	public static HashMap<String,Object> informations = new HashMap<String,Object>();
	
	public static BigDecimal creditPool = new BigDecimal("0");
	
	public static BigDecimal cashPool = new BigDecimal("0");
	
	public static BigDecimal loanPool = new BigDecimal("0");
	
	public static List<String> waitLoan = new ArrayList<>();
	
	public static List<String> waitDonate = new ArrayList<>();
}
