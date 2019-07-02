package com.bocsoft.callforcode.common;

public enum ExceptionEnums {

	MISSING_PARAM("A001","missing required parameter"),
	
	JSON_SERIALIZATION_EXCEPTION("A002","error on serializing object to json"),

	JSON_DESERIALIZATION_EXCEPTION("A003","error on paring json to object"),
	
	INVALID_MSG_TYPE("A004","invalid message type"),
	
	BEAN_COPY_EXCEPTION("A005","error on bean copy"),
	
	ENCRYPT_MESSAGE_ERROR("A006","error when encrypt message"),
	
	DECRYPT_MESSAGE_ERROR("A007","error when decrypt message"),
	
	CHAIN_CODE_INSTANTIATE_EXCEPTION("B001","error on instantiate chaincode"),
	
	CHAIN_CODE_INVOKE_EXCEPTION("B002","error on invoking chaincode, check peer/order status"),
	
	CONFIG_ERROR_CHAINCODE_LANG("B003", "${fabricsdk.constant.chainCodeLanguage} must set to one of (go, java, node)"),
	
	BLOCK_PARSE_ARGS_TO_VO_ERROR("B004","error on parse args to vo in block listener"),
	
	CHAIN_CODE_INSTALL_EXCEPTION("B005","error on install chaincode"),
	
	CHAIN_CODE_UPGRADE_EXCEPTION("B006","error on upgrade chaincode"),
	
	CHAIN_CODE_LIST_INSTALLED_EXCEPTION("B007","error on list installed chaincode"),
	
	CHAIN_CODE_LIST_INSTANTIATED_EXCEPTION("B008","error on list instantiated chaincode"),
	
	PSEUDO_ACCOUNT_NOT_EXIST("P001","account not exist"),
	
	ACCOUNT_NOT_FOUND("P001","account not found"),
	
	TRANSACTION_NOT_FOUND("P002","transaction not found");
	
    private String errorCode;
    private String errorMessage;
    
    private ExceptionEnums(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getCode() {
        return errorCode;
    }

    public void setCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return errorMessage;
    }

    public void setMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
