package com.ozhou.fantuan.resource.dto;

import java.math.BigDecimal;

public class AccountDto {
	
	private BigDecimal balance;
	private String name;
	private String password;
	
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
