package com.bayesiansamaritan.lifeplanner.repository.Financial;

import com.bayesiansamaritan.lifeplanner.model.Financial.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
  List<Account> findByUserIdAndId(Long userId, Long accountId);

  List<Account> findByUserIdAndAccountTypeId(Long userId, Long accountTypeId);

  List<Account> findByUserId(Long userId);

  @Query("Select t from Account t where t.userId=:userId and t.freeLiquidity=true")
  List<Account> findByUserIdAndFreeLiquidity(Long userId);

  @Query("Select t from Account t where t.userId=:userId and (t.freeLiquidity=true or t.liquidity=true)")
  List<Account> findByUserIdAndLiquidity(Long userId);

  @Query("Select t from Account t where t.userId=:userId and t.name=:name")
  Account findByNameAndUserId(String name, Long userId);

  @Transactional
  @Modifying
  @Query("update Account set balance = balance - :cost,updated_at=now() where id=:id")
  public void updateBalance(@Param("id") Long id,@Param("cost") Long cost);

  @Transactional
  @Modifying
  @Query("update Account set balance = :balance,updated_at=now() where id=:id")
  public void changeBalance(@Param("id") Long id,@Param("balance") Long balance);

  void deleteById(Long id);

  @Transactional
  @Modifying
  @Query("update Account set name=:name,startDate=:startDate,description=:description,active=:active,hidden=:hidden,completed=:completed" +
          ",accountTypeId=:accountTypeId,balance=:balance,liquidity=:liquidity,freeLiquidity=:freeLiquidity," +
          "nomineeName=:nomineeName, maturityDate=:maturityDate,stockCode=:stockCode,schemeCode=:schemeCode,updated_at=now() where id=:id")
  void modifyParams(@Param("id") Long id, @Param("name") String name, @Param("startDate") Date startDate,
                    @Param("description") String description, @Param("accountTypeId") Long accountTypeId,
                    @Param("active") Boolean active, @Param("hidden") Boolean hidden, @Param("completed") Boolean completed,
                    @Param("balance") Long balance, @Param("liquidity") Boolean liquidity, @Param("freeLiquidity") Boolean freeLiquidity,
                    @Param("nomineeName") String nomineeName, @Param("maturityDate") Date maturityDate,
                    @Param("stockCode") String stockCode, @Param("schemeCode") String schemeCode);
}
