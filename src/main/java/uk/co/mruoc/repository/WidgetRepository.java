package uk.co.mruoc.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import uk.co.mruoc.model.Widget;

import java.util.UUID;

@EnableScan
public interface WidgetRepository extends CrudRepository<Widget, UUID> {

    //intentionally blank

}