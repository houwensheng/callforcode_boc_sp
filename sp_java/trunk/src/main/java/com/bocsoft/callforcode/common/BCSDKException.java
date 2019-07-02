package com.bocsoft.callforcode.common;

public class BCSDKException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String code;
	private String returnMsg;
	
	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
    public BCSDKException(ExceptionEnums ex) {
        super(ex.getMessage());
        this.code = ex.getCode();
    }
    
    public BCSDKException(ExceptionEnums ex, String returnMsg) {
        super(ex.getMessage());
        this.code = ex.getCode();
        this.returnMsg = returnMsg;
    }

	public BCSDKException(ExceptionEnums ex, Throwable cause) {
		super(ex.getMessage(), cause);
		this.code = ex.getCode();
	}
	
	public BCSDKException(ExceptionEnums ex, String returnMsg, Throwable cause) {
		super(ex.getMessage(), cause);
		this.code = ex.getCode();
		this.returnMsg = returnMsg;
	}
}
