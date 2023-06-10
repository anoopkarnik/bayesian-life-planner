package com.bayesiansamaritan.lifeplanner.controller;

import com.bayesiansamaritan.lifeplanner.model.Financial.Fund;
import com.bayesiansamaritan.lifeplanner.model.Goal.Goal;
import com.bayesiansamaritan.lifeplanner.model.Goal.GoalType;
import com.bayesiansamaritan.lifeplanner.repository.Financial.FundRepository;
import com.bayesiansamaritan.lifeplanner.repository.User.UserProfileRepository;
import com.bayesiansamaritan.lifeplanner.response.FundResponse;
import com.bayesiansamaritan.lifeplanner.response.FundSummaryResponse;
import com.bayesiansamaritan.lifeplanner.response.GoalResponse;
import com.bayesiansamaritan.lifeplanner.response.TransactionResponse;
import com.bayesiansamaritan.lifeplanner.security.jwt.JwtUtils;
import com.bayesiansamaritan.lifeplanner.service.AccountService;
import com.bayesiansamaritan.lifeplanner.service.FundService;
import com.bayesiansamaritan.lifeplanner.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/funds")
public class FundController {
	@Autowired
	private FundRepository fundRepository;
	@Autowired
	AccountService accountService;

	@Autowired
	FundService fundService;

	@Autowired
	private UserProfileRepository userProfileRepository;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	DateUtils dateUtils;
	static final String HEADER_STRING = "Authorization";
	static final String TOKEN_PREFIX = "Bearer";

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public FundResponse getFund(@PathVariable("id") Long id) {
		Fund fund = fundRepository.findById(id).get();
		FundResponse fundResponse = new FundResponse(fund.getId(),fund.getCreatedAt(),fund.getUpdatedAt(),
				fund.getName(),fund.getStartDate(),fund.getDescription(),fund.getActive(),fund.getHidden(),
				fund.getCompleted(),fund.getUserId(),fund.getAmountAllocated(),fund.getAmountNeeded());
		return fundResponse;
	}

	@PostMapping
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Fund> createFund(HttpServletRequest request, @RequestBody Fund fund) {
		String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
		Long userId = userProfileRepository.findByName(username).get().getId();
		try {
			Fund _fund = fundRepository.save(new Fund(fund.getName(), fund.getAmountAllocated(),fund.getAmountNeeded(),userId,true));
			return new ResponseEntity<>(_fund, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<FundResponse>> getFund(HttpServletRequest request) {
		String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
		Long userId = userProfileRepository.findByName(username).get().getId();
		try {
			List<Fund> funds= fundRepository.findByUserId(userId);
			List<FundResponse> fundResponses = new ArrayList<>();
			for (Fund fund :funds){
				FundResponse fundResponse = new FundResponse(fund.getId(),fund.getCreatedAt(),fund.getUpdatedAt(),fund.getName(),
						fund.getStartDate(),fund.getDescription(),fund.getActive(),fund.getHidden(),fund.getCompleted(),fund.getUserId(),
						fund.getAmountAllocated(),fund.getAmountNeeded());
				fundResponses.add(fundResponse);
			}
			if (funds.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(fundResponses, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("/needed")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void changeAmountNeeded(@RequestParam("id") Long id, @RequestParam("amountNeeded") Long amountNeeded){
		try {
			fundRepository.updateAmountNeeded(id,amountNeeded);
		} catch (Exception e) {

		}
;	}

	@PatchMapping("/allocated")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void changeAmountAllocated(@RequestParam("id") Long id, @RequestParam("amountAllocated") Long amountAllocated){
		try {
			fundRepository.updateAmountAllocated(id,amountAllocated);
		} catch (Exception e) {

		}
		;	}

	@DeleteMapping
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void deleteFunds(@RequestParam("id") Long id){
		fundRepository.deleteById(id);
	}

	@GetMapping("/summary")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<FundSummaryResponse> getFundSummary(HttpServletRequest request){
		String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
		Long userId = userProfileRepository.findByName(username).get().getId();

		try {
			FundSummaryResponse fund= fundService.getFundSummary(userId);

			return new ResponseEntity<>(fund, HttpStatus.OK);
		} catch (Exception e) {
			FundSummaryResponse fund = new FundSummaryResponse();
			fund.setAmountAllocated(0L);
			fund.setFundsAmountAvailable(0L);
			fund.setEmergencyAmountAvailable(0L);
			fund.setTimeLeft(100L);
			fund.setTotalAmount(0L);
			fund.setFinancialIndependenceAmount(50000000L);
			fund.setFinancialIndependencePercentage(0L);
			return new ResponseEntity<>(fund, HttpStatus.OK);
		}
	}

	@PatchMapping("/modifyParams")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void modifyParams(@RequestBody FundResponse fundResponse)
	{
		fundService.modifyParams(fundResponse);
	}


}