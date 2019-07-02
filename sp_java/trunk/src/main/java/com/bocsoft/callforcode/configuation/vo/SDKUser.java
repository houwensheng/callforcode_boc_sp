package com.bocsoft.callforcode.configuation.vo;


import static com.bocsoft.callforcode.common.Constants.UTF_8;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.PrivateKey;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;

public class SDKUser implements User, Enrollment  {

    private String name;
    private String[] roleList;
    private String mspId;
    private String affiliation;
    private String account;
    private String privateKeyPath;
    private String certPath;
    
    private HashSet<String> hashSet;
    
    private PrivateKey privateKey;
    private String certStr;
    
    public void initEnrollment() throws IOException {
    	PEMParser pp = new PEMParser(new FileReader(privateKeyPath));
    	Object o = pp.readObject();
    	pp.close();
    	JcaPEMKeyConverter jkc = new JcaPEMKeyConverter();
    	privateKey = jkc.getPrivateKey((PrivateKeyInfo)o);
    	
    	certStr = FileUtils.readFileToString(new File(certPath), UTF_8);

    }
    


	public String getPrivateKeyPath() {
		return privateKeyPath;
	}



	public void setPrivateKeyPath(String privateKeyPath) {
		this.privateKeyPath = privateKeyPath;
	}



	public String getCertPath() {
		return certPath;
	}

	public void setCertPath(String certPath) {
		this.certPath = certPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getRoles() {
		if (hashSet == null) {
			hashSet = new HashSet<String>();
			for (int i = 0; i < roleList.length; i++) {
				hashSet.add(roleList[i]);
			}
		}
		return hashSet;
	}

	public void setRoleList(String[] roles) {
		this.roleList = roles;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public String getMspId() {
		return mspId;
	}

	public void setMspId(String mspId) {
		this.mspId = mspId;
	}

	public Enrollment getEnrollment() {
		return this;
	}

	public PrivateKey getKey() {
		return privateKey;
	}

	public String getCert() {
		return certStr;
	}

    

}