package com.ozhou.fantuan.resource;
 
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ozhou.fantuan.model.MealRecord;
import com.ozhou.fantuan.model.dao.MealRecordDao;
import com.ozhou.fantuan.resource.dto.MealRecordDto;
import com.ozhou.fantuan.service.AccountService;
import com.ozhou.utils.CountDto;
import com.ozhou.utils.DtoBoConverter;
 
@Path("/meals")
public class MealResource {
	
	@Autowired
	private MealRecordDao mealRecordDao;
	@Autowired
	private DtoBoConverter converter;
	@Autowired
	private AccountService accountService;
	
	public MealResource() {}
	
	@GET
	@Path("/")
	@Produces({"application/json"})
	// Cannot directly return list of records because issue:
	// https://issues.jboss.org/browse/RESTEASY-834
	public Response getMealRecordForUser(@QueryParam("user") @NotNull String user, 
			@QueryParam("start") int start, @QueryParam("pageSize") int pageSize) {
		List<MealRecord> mealRecords = mealRecordDao.getMealRecordForUser(user, start, pageSize);
		List<MealRecordDto> records = converter.getMappingFacade().mapAsList(mealRecords, MealRecordDto.class);
		return Response.ok(records).build();
	}
	
	@POST
	@Path("/")
	@Produces({"application/json"})
	@Transactional
	public void putMealRecord(@NotNull MealRecordDto record) {
		MealRecord mealRecord = converter.getMappingFacade().map(record, MealRecord.class);
		accountService.splitMealRecord(mealRecord);
		mealRecordDao.save(mealRecord);
	}
	
	@GET
	@Path("/count")
	@Produces({"application/json"})
	public CountDto getMealRecordCountForUser(@QueryParam("user") @NotNull String user) {
		Long count = mealRecordDao.getMealRecordForUserCount(user);
		return new CountDto(count);
	}

}