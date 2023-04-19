package com.bayesiansamaritan.lifeplanner.controller;

import com.bayesiansamaritan.lifeplanner.repository.Financial.TransactionsRepository;
import com.bayesiansamaritan.lifeplanner.model.Financial.Income;
import com.bayesiansamaritan.lifeplanner.model.Financial.Transactions;
import com.bayesiansamaritan.lifeplanner.repository.Financial.IncomeRepository;
import com.bayesiansamaritan.lifeplanner.repository.User.UserProfileRepository;
import com.bayesiansamaritan.lifeplanner.request.Financial.TransactionsListRequest;
import com.bayesiansamaritan.lifeplanner.request.Financial.TransactionsRequest;
import com.bayesiansamaritan.lifeplanner.request.Task.TaskModifyRequest;
import com.bayesiansamaritan.lifeplanner.response.TransactionResponse;
import com.bayesiansamaritan.lifeplanner.security.jwt.JwtUtils;
import com.bayesiansamaritan.lifeplanner.service.TransactionsService;
import com.bayesiansamaritan.lifeplanner.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {
	@Autowired
	private TransactionsRepository transactionsRepository;

	@Autowired
	TransactionsService transactionsService;

	@Autowired
	IncomeRepository incomeRepository;
	@Autowired
	private UserProfileRepository userProfileRepository;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	DateUtils dateUtils;
	static final String HEADER_STRING = "Authorization";
	static final String TOKEN_PREFIX = "Bearer";

	@PostMapping
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Transactions> createTransactions(HttpServletRequest request, @RequestBody TransactionsRequest transactionsRequest) {
		String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
		Long userId = userProfileRepository.findByName(username).get().getId();
		try {
			Transactions transactions = transactionsService.createTransactions(transactionsRequest.getName(),transactionsRequest.getCost(),
					transactionsRequest.getExpenseName(),transactionsRequest.getCategoryName(),transactionsRequest.getSubCategoryName(),
					transactionsRequest.getAccountName(),transactionsRequest.getSubAccountName(),userId);
			return new ResponseEntity<>(transactions, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<TransactionResponse>> getTransactions(HttpServletRequest request, @RequestBody TransactionsListRequest transactionsListRequest) {
		String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
		Long userId = userProfileRepository.findByName(username).get().getId();
		try {
			List<TransactionResponse> transactionsList =transactionsService.getTransactions(userId,transactionsListRequest.getExpenseTypes(),
					transactionsListRequest.getAccountTypes(),transactionsListRequest.getCategoryTypes(),transactionsListRequest.getSubCategoryTypes(),
					transactionsListRequest.getDateFrom(),transactionsListRequest.getDateTo());
			if (transactionsList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(transactionsList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void deleteTransactions(@RequestParam("id") Long id){
		transactionsService.deleteTransactions(id);
	}

	@PostMapping("/income")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Income> createIncome(HttpServletRequest request,@RequestBody Income income) {
		String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
		Long userId = userProfileRepository.findByName(username).get().getId();
		try {
			Income _income = incomeRepository.save(new Income(income.getName(),income.getIncome(),true,userId));
			return new ResponseEntity<>(_income, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("/modifyParams")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void modifyParams(@RequestBody TransactionResponse transactionResponse)
	{
		transactionsService.modifyParams(transactionResponse);
	}

}