package com.ozhou.fantuan.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.collect.Lists;

@Entity
@Table(name = "users")
public class Account {

	@Id
	@Column(name = "username")
	private String name;
	@Column(name = "password")
	private String password;
	@Column(name = "enabled")
	private boolean enabled = true;
	@Column(name = "balance", precision=10, scale=2)
	private BigDecimal balance;
	@OneToMany(mappedBy="account", cascade = {CascadeType.ALL})
	private List<AccountEntry> entries = Lists.newArrayList();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public List<AccountEntry> getEntries() {
		return entries;
	}
	public void setEntries(List<AccountEntry> entries) {
		this.entries = entries;
	}
	
	public void debit(Date date, BigDecimal amount, String description) {
		createAccountEntry(AccountEntry.Type.DEBIT, date, amount, description);
		balance = balance.add(amount);
	}
	
	public void credit(Date date, BigDecimal amount, String description) {
		createAccountEntry(AccountEntry.Type.CREDIT, date, amount, description);
		balance = balance.subtract(amount);
	}
	
	private void createAccountEntry(AccountEntry.Type type, Date date, BigDecimal amount, String description) {
		AccountEntry entry = new AccountEntry();
		entry.setType(type);
		entry.setAmount(amount);
		entry.setDescription(description);
		entry.setDate(date);
		
		getEntries().add(entry);
		entry.setAccount(this);
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
