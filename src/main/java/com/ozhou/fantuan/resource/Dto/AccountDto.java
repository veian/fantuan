package com.ozhou.fantuan.resource.Dto;

import java.math.BigDecimal;

public class AccountDto {
	
	private BigDecimal balance;
	private String name;
	
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
	
}
