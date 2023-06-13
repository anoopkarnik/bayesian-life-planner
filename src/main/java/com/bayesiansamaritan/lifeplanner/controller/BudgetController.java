package com.bayesiansamaritan.lifeplanner.controller;

import com.bayesiansamaritan.lifeplanner.model.Financial.*;
import com.bayesiansamaritan.lifeplanner.repository.Financial.*;
import com.bayesiansamaritan.lifeplanner.repository.User.UserProfileRepository;
import com.bayesiansamaritan.lifeplanner.request.Financial.BudgetPlanRequest;
import com.bayesiansamaritan.lifeplanner.request.Financial.MonthlyBudgetRequest;
import com.bayesiansamaritan.lifeplanner.response.BudgetPlanResponse;
import com.bayesiansamaritan.lifeplanner.response.BudgetResponse;
import com.bayesiansamaritan.lifeplanner.security.jwt.JwtUtils;
import com.bayesiansamaritan.lifeplanner.service.BudgetService;
import com.bayesiansamaritan.lifeplanner.utils.HabitDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;


@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/budget")
public class BudgetController {
	@Autowired
	private MonthlyBudgetRepository monthlyBudgetRepository;
	@Autowired
	private CategoryTypeRepository categoryTypeRepository;
	@Autowired
	private SubCategoryTypeRepository subCategoryTypeRepository;
	@Autowired
	private ExpenseTypeRepository expenseTypeRepository;
	@Autowired
	private IncomeRepository incomeRepository;
	@Autowired
	private BudgetPlanRepository budgetPlanRepository;
	@Autowired
	private TransactionsRepository transactionsRepository;
	@Autowired
	BudgetService budgetService;
	@Autowired
	private UserProfileRepository userProfileRepository;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
    HabitDateUtils habitDateUtils;
	static final String HEADER_STRING = "Authorization";
	static final String TOKEN_PREFIX = "Bearer";

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public BudgetResponse getBudget(@PathVariable("id") Long id) throws ParseException {
		MonthlyBudget monthlyBudget = monthlyBudgetRepository.findById(id).get();
		CategoryType categoryType = categoryTypeRepository.findById(monthlyBudget.getCategoryTypeId()).get();
		SubCategoryType subCategoryType = subCategoryTypeRepository.findById(monthlyBudget.getSubCategoryTypeId()).get();
		ExpenseType expenseType = expenseTypeRepository.findById(monthlyBudget.getExpenseTypeId()).get();
		LocalDate todayDate = LocalDate.now();
		Date startDate =  new SimpleDateFormat("yyyy-MM-dd").parse(todayDate.with(TemporalAdjusters.firstDayOfMonth()).toString());
		Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(todayDate.with(TemporalAdjusters.firstDayOfNextMonth()).toString());
		List<Transactions> transactions = transactionsRepository.findBySome(monthlyBudget.getUserId(),monthlyBudget.getExpenseTypeId()
				,monthlyBudget.getCategoryTypeId(),monthlyBudget.getSubCategoryTypeId(),startDate,endDate);
		Long amountSpent = 0L;
		for (Transactions transaction:transactions){
			amountSpent+=transaction.getCost();
		}
		BudgetResponse budgetResponse = new BudgetResponse(monthlyBudget.getId(),monthlyBudget.getCreatedAt(),
				monthlyBudget.getUpdatedAt(),monthlyBudget.getName(),monthlyBudget.getStartDate(), categoryType.getName(),
				expenseType.getName(),subCategoryType.getName(),monthlyBudget.getDescription(),monthlyBudget.getActive(),
				monthlyBudget.getHidden(),monthlyBudget.getCompleted(),monthlyBudget.getCost(),monthlyBudget.getUserId(),
				monthlyBudget.getCost(),amountSpent);
		return budgetResponse;
	}

	@PostMapping("/monthly")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<MonthlyBudget> createMonthlyBudget(HttpServletRequest request, @RequestBody MonthlyBudgetRequest monthlyBudgetRequest) {
		String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
		Long userId = userProfileRepository.findByName(username).get().getId();
		try {
			MonthlyBudget monthlyBudget= budgetService.createMonthlyBudget(monthlyBudgetRequest.getCost(),
					monthlyBudgetRequest.getExpenseName(),monthlyBudgetRequest.getCategoryName(),monthlyBudgetRequest.getSubCategoryName(),
					userId);
			return new ResponseEntity<>(monthlyBudget, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/monthly")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<BudgetResponse>> getMonthlyBudget(HttpServletRequest request,@RequestParam("expenseType") String expenseType) {
		String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
		Long userId = userProfileRepository.findByName(username).get().getId();
		try {
			List<BudgetResponse> monthlyBudgets = budgetService.getMonthlyBudgets(expenseType,userId);
			if (monthlyBudgets.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(monthlyBudgets, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/plan")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<BudgetPlan> createBudgetPlan(HttpServletRequest request,@RequestBody BudgetPlanRequest budgetPlanRequest) {
		String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
		Long userId = userProfileRepository.findByName(username).get().getId();
		try {
			BudgetPlan budgetPlan = budgetService.createBudgetPlan(budgetPlanRequest.getExpenseName(),budgetPlanRequest.getPlanPercentage(),
					userId);
			return new ResponseEntity<>(budgetPlan, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/income")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<Income>> getIncome(HttpServletRequest request) {
		String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
		Long userId = userProfileRepository.findByName(username).get().getId();
		try {
			List<Income> incomes= incomeRepository.findByUserId(userId);
			if (incomes.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(incomes, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping("/income")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void deleteIncomes(@RequestParam("id") Long id){
		incomeRepository.deleteById(id);
	}


	@GetMapping("/planAmount")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<BudgetPlanResponse>> getBudgetPlanAmount(HttpServletRequest request) {
		String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
		Long userId = userProfileRepository.findByName(username).get().getId();
		try {
			List<BudgetPlanResponse> budgetPlans = budgetService.getBudgetPlans(userId);
			if (budgetPlans.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(budgetPlans, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/monthly")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void deleteBudget(@RequestParam("id") Long id){
		monthlyBudgetRepository.deleteById(id);
	}

	@PatchMapping("/monthly")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void changeCost(@RequestParam("id") Long id, @RequestParam("cost") Long cost){
		try {
			monthlyBudgetRepository.changeCost(id,cost);
		} catch (Exception e) {

		};
	};

	@PatchMapping("/plan")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void changePlanPercentage(@RequestParam("id") Long id, @RequestParam("plan_percentage") Long plan_percentage){
		try {
			budgetPlanRepository.changePlanPercentage(id,plan_percentage);
		} catch (Exception e) {

		};
	};

	@PatchMapping("/modifyParams")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void modifyParams(@RequestBody BudgetResponse budgetResponse)
	{
		budgetService.modifyParams(budgetResponse);
	}

}