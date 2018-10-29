package uk.co.mruoc.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import uk.co.mruoc.model.Widget;

import java.util.UUID;

@EnableScan
@EnableScanCount
public interface WidgetRepository extends PagingAndSortingRepository<Widget, UUID> {

    Page<Widget> findAll(Pageable pageable);

}