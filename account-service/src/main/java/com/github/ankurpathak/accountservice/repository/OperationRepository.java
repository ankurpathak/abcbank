package com.github.ankurpathak.accountservice.repository;

import com.github.ankurpathak.accountservice.entity.Operation;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Observed
public interface OperationRepository extends JpaRepository<Operation,String> {
    /**
     * Retrieves a paginated list of {@code Operation} entities associated with the specified account ID,
     * ordered by date in descending order.
     *
     * <p>Author: Ankur Pathak</p>
     *
     * @param accountId The unique identifier of the account for which operations are to be retrieved.
     * @param pageable  The pagination information for controlling the result size and page number.
     * @return A {@code Page} containing the requested operations.
     */
    @Query("select o from Operation o where o.account.id = ?1 order by o.date desc")
    Page<Operation> findByAccountIdOrderByDateDesc(String accountId, Pageable pageable);
}
