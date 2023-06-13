package com.bayesiansamaritan.lifeplanner.controller;

import com.bayesiansamaritan.lifeplanner.model.Financial.Account;
import com.bayesiansamaritan.lifeplanner.model.Financial.AccountType;
import com.bayesiansamaritan.lifeplanner.repository.Financial.AccountRepository;
import com.bayesiansamaritan.lifeplanner.repository.Financial.AccountTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.User.UserProfileRepository;
import com.bayesiansamaritan.lifeplanner.request.Financial.AccountRequest;
import com.bayesiansamaritan.lifeplanner.response.AccountBalanceResponse;
import com.bayesiansamaritan.lifeplanner.response.AccountResponse;
import com.bayesiansamaritan.lifeplanner.security.jwt.JwtUtils;
import com.bayesiansamaritan.lifeplanner.service.AccountService;
import com.bayesiansamaritan.lifeplanner.utils.HabitDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
	@Autowired
	AccountService accountService;
	@Autowired
	AccountTypeRepository accountTypeRepository;
	@Autowired
	AccountRepository accountRepository;
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
	public AccountResponse getAccount(@PathVariable("id") Long id) {
		Account account = accountRepository.findById(id).get();
		AccountType accountType = accountTypeRepository.findById(account.getAccountTypeId()).get();
		AccountResponse accountResponse = new AccountResponse(account.getId(),account.getCreatedAt(),
				account.getUpdatedAt(),account.getName(),account.getStartDate(),accountType.getName(),
				account.getBalance(),account.getLiquidity(),account.getFreeLiquidity(),account.getDescription(),
				account.getActive(),account.getHidden(),account.getCompleted(),account.getUserId(),
				account.getNomineeName(),account.getMaturityDate(),account.getStockCode(),account.getSchemeCode());
		return accountResponse;
	}

	@PostMapping
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Account> createSubAccountType(HttpServletRequest request, @RequestBody AccountRequest accountRequest)
	{
		String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
		Long userId = userProfileRepository.findByName(username).get().getId();
		try {
			Account _Account = accountService.createSubAccount(accountRequest.getAccountName(), accountRequest.getBalance(),
					accountRequest.getFreeLiquidity(), accountRequest.getLiquidity(), accountRequest.getName(),userId);
			return new ResponseEntity<>(_Account, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<AccountResponse>> getAccountByUserAndAccountType(HttpServletRequest request, @RequestParam("accountTypeName") String accountTypeName) {
		String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
		Long userId = userProfileRepository.findByName(username).get().getId();
		try {
			List<AccountResponse> accountList = accountService.getAccountByUserAndAccountType(userId,accountTypeName);
			if (accountList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(accountList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/balances")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<AccountBalanceResponse> > getAllAccountBalances(HttpServletRequest request) {
		String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
		Long userId = userProfileRepository.findByName(username).get().getId();
		try {
			List<AccountBalanceResponse> accountTypes = accountService.getAllAccountBalances(userId);
			if (accountTypes.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(accountTypes, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void changeBalance(@RequestParam("id") Long id, @RequestParam("cost") Long balance){
		try {
			accountService.changeBalance(id,balance);
		} catch (Exception e) {

		}
;	}

	@DeleteMapping
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void deleteAccounts(@RequestParam("id") Long id){
		accountService.deleteAccounts(id);
	}

	@PatchMapping("/modifyParams")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void modifyParams(@RequestBody AccountResponse accountResponse)
	{
		accountService.modifyParams(accountResponse);
	}


}