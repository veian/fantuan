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

import com.ozhou.fantuan.model.MealRecord;
import com.ozhou.fantuan.model.dao.MealRecordDao;
import com.ozhou.fantuan.resource.dto.MealRecordDto;
import com.ozhou.fantuan.service.AccountService;
import com.ozhou.utils.DtoBoConverter;
 
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
		List<MealRecordDto> records = converter.getMappingFacade().mapAsList(mealRecords, MealRecordDto.class);
		return Response.status(200).entity(records).build();
	}
	
	@POST
	@Path("/")
	@Produces({"application/json"})
	@Transactional
	public Response putMealRecord(MealRecordDto record) {
		MealRecord mealRecord = converter.getMappingFacade().map(record, MealRecord.class);
		accountService.splitMealRecord(mealRecord);
		mealRecordDao.save(mealRecord);
		return Response.status(200).build();
	}
	
	@GET
	@Path("/user/count")
	@Produces({"application/json"})
	public Response getMealRecordCountForUser(@QueryParam("user") String user) {
		Long count = mealRecordDao.getMealRecordForUserCount(user);
		return Response.status(200).entity(count).build();
	}

}