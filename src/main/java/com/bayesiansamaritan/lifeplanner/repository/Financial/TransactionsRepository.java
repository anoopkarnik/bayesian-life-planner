package com.bayesiansamaritan.lifeplanner.repository.Financial;

import com.bayesiansamaritan.lifeplanner.model.Financial.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {

  @Query("Select t from Transactions t where t.userId = :user_id and t.expenseTypeId in :expense_type_id and t.accountTypeId in :account_id and " +
          "t.categoryId in :category_id and t.subCategoryId in :sub_category_id " +
          "and t.createdAt between :start_date and :end_date")
  List<Transactions> findByAll(@Param("user_id") Long userId, @Param("expense_type_id") List<Long> expenseTypeIds,
                               @Param("account_id") List<Long> accountTypeIds, @Param("category_id") List<Long> categoryTypeIds,
                               @Param("sub_category_id") List<Long> subCategoryTypeIds,
                               @Param("start_date") Date dateFrom, @Param("end_date") Date dateTo);

  @Query("Select t from Transactions t where t.userId = :user_id and t.expenseTypeId=:expense_type_id and " +
          "t.categoryId=:category_id and t.subCategoryId=:sub_category_id " +
          "and t.createdAt between :start_date and :end_date")
  List<Transactions> findBySome(@Param("user_id") Long userId, @Param("expense_type_id") Long expenseTypeId,
                                @Param("category_id") Long categoryTypeId,
                                @Param("sub_category_id") Long subCategoryTypeId,
                                @Param("start_date") Date dateFrom, @Param("end_date") Date dateTo);

  void deleteById(Long id);

  @Transactional
  @Modifying
  @Query("update Transactions set name=:name,startDate=:startDate,description=:description,active=:active,hidden=:hidden,completed=:completed" +
          ",accountId=:accountId,accountTypeId=:accountTypeId,expenseTypeId=:expenseTypeId,categoryId=:categoryId," +
          "subCategoryId=:subCategoryId,cost=:cost,updated_at=now() where id=:id")
  void modifyParams(@Param("id") Long id, @Param("name") String name, @Param("startDate") Date startDate,
                           @Param("description") String description, @Param("accountId") Long accountId, @Param("accountTypeId") Long accountTypeId,
                           @Param("active") Boolean active, @Param("hidden") Boolean hidden, @Param("completed") Boolean completed,
                           @Param("expenseTypeId") Long expenseTypeId,@Param("categoryId") Long categoryId,
                           @Param("subCategoryId") Long subCategoryId,@Param("cost") Long cost);


}
