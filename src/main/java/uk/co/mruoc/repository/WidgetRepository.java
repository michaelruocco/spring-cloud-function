package uk.co.mruoc.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface WidgetRepository extends CrudRepository<Widget, Long> {

    //intentionally blank

}