package com.bocsoft.callforcode.vo;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class RequestBaseVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String txId = "-1";
	
	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}
	
	ArrayList<String> args = new ArrayList<String>();
	
	public void setArgsEmpty() {
		args.clear();
	}

	public abstract ArrayList<String> getArgs();
}
