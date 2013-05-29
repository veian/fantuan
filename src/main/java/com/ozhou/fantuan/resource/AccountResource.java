package com.ozhou.fantuan.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.google.common.collect.Lists;
import com.ozhou.fantuan.model.Account;
import com.ozhou.fantuan.model.dao.AccountDao;
import com.ozhou.fantuan.resource.Dto.AccountDto;
import com.ozhou.fantuan.resource.converter.DtoBoConverter;

@Configurable(preConstruction=true)
@Path("/account")
public class AccountResource {

	@Autowired
	private AccountDao accountDao;
	@Autowired
	private DtoBoConverter converter;
	
	@GET
	@Path("/")
	@Produces({"application/json"})
	public Response getAccounts() {
		List<Account> accounts = accountDao.getAll();
		List<AccountDto> dtos = Lists.newArrayList();
		for (Account account : accounts) {
			AccountDto dto = converter.toDto(account);
			dtos.add(dto);
		}
		return Response.status(200).entity(dtos).build();
	}
	
	@GET
	@Path("/{user}")
	@Produces({"application/json"})
	public Response getAccount(@PathParam("user") String user) {
		Account account = accountDao.get(user);
		if (account == null)
			return Response.status(404).build();
		
		AccountDto dto = converter.toDto(account);
		return Response.status(200).entity(dto).build();
	}
}
