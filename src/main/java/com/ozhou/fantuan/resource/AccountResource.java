package com.ozhou.fantuan.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ozhou.fantuan.model.Account;
import com.ozhou.fantuan.model.AccountEntry;
import com.ozhou.fantuan.model.dao.AccountDao;
import com.ozhou.fantuan.resource.dto.AccountDto;
import com.ozhou.fantuan.resource.dto.AccountEntryDto;
import com.ozhou.utils.CountDto;
import com.ozhou.utils.DtoBoConverter;

@Path("/accounts")
public class AccountResource {

	@Autowired
	private AccountDao accountDao;
	@Autowired
	private DtoBoConverter converter;

	@POST
	@Path("/")
	@Produces({"application/json"})
	@Transactional
	public void putAccount(@NotNull AccountDto record) {
		Account account = converter.getMappingFacade().map(record, Account.class);
		accountDao.save(account);
	}
	
	@GET
	@Path("/")
	@Produces({"application/json"})
	public List<AccountDto> getAccounts(@QueryParam("start") int start, @QueryParam("pageSize") int pageSize) {
		List<Account> accounts = accountDao.getAll(start, pageSize);
		List<AccountDto> dtos = converter.getMappingFacade().mapAsList(accounts, AccountDto.class);
		return dtos;
	}

	@GET
	@Path("/count")
	@Produces({"application/json"})
	public CountDto getAccountsCount() {
		Long count = accountDao.getCount();
		return new CountDto(count);
	}
	
	@GET
	@Path("/{user}")
	@Produces({"application/json"})
	public AccountDto getAccount(@PathParam("user") String user) {
		Account account = accountDao.get(user);
		if (account == null)
			throw new NotFoundException();
		
		AccountDto dto = converter.getMappingFacade().map(account, AccountDto.class);
		return dto;
	}
	
	@GET
	@Path("/{user}/entry")
	@Produces({"application/json"})
	public List<AccountEntryDto> getAccountEntries(@PathParam("user") String user, 
			@QueryParam("start") int start, @QueryParam("pageSize") int pageSize) {
		List<AccountEntry> entries = accountDao.getAccountEntryByUser(user, start, pageSize);
		List<AccountEntryDto> dtos = converter.getMappingFacade().mapAsList(entries, AccountEntryDto.class);
		return dtos;
	}
	
	@GET
	@Path("/{user}/entry/count")
	@Produces({"application/json"})
	public CountDto getAccountEntriesCount(@PathParam("user") String user) {
		Long count = accountDao.getAccountEntryByUserCount(user);
		return new CountDto(count);
	}
	
	@POST
	@Path("/clear-cache")
	@Produces({"application/json"})
	public void clearCache() {
		accountDao.clearCache();
	}
}
