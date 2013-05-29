package com.ozhou.fantuan.resource;
 
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.ozhou.fantuan.model.MealRecord;
import com.ozhou.fantuan.model.dao.MealRecordDao;
import com.ozhou.fantuan.resource.Dto.MealRecordDto;
import com.ozhou.fantuan.resource.converter.DtoBoConverter;
import com.ozhou.fantuan.service.AccountService;
 
@Configurable(preConstruction=true)
@Path("/meal")
public class MealResource {
	
	@Autowired
	private MealRecordDao mealRecordDao;
	@Autowired
	private DtoBoConverter converter;
	@Autowired
	private AccountService accountService;
	
	public MealResource() {}
	
	@GET
	@Path("/user")
	@Produces({"application/json"})
	public Response getMealRecordForUser(@QueryParam("user") String user, 
			@QueryParam("start") int start, @QueryParam("pageSize") int pageSize) {
		List<MealRecord> mealRecords = mealRecordDao.getMealRecordForUser(user, start, pageSize);
		List<MealRecordDto> records = Lists.newArrayList();
		for (MealRecord mealRecord : mealRecords)
			records.add(converter.toDto(mealRecord));
		return Response.status(200).entity(records).build();
	}
	
	@POST
	@Path("/")
	@Produces({"application/json"})
	@Transactional
	public Response putMealRecord(MealRecordDto record) {
		MealRecord mealRecord = converter.toBo(record);
		accountService.splitMealRecord(mealRecord);
		mealRecordDao.save(mealRecord);
		return Response.status(200).build();
	}

}