package uk.co.mruoc.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.co.mruoc.Application;
import uk.co.mruoc.model.FakeWidget;
import uk.co.mruoc.model.Widget;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("local")
public class WidgetRepositoryTest {

    @ClassRule
    public static LocalDynamoRule dynamoRule = new LocalDynamoRule();

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    private DynamoDBMapperConfig dynamoDBMapperConfig;

    @Autowired
    private WidgetRepository repository;

    @Before
    public void setup() {
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB, dynamoDBMapperConfig);

        CreateTableRequest request = dynamoDBMapper.generateCreateTableRequest(Widget.class);
        request.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
        TableUtils.createTableIfNotExists(amazonDynamoDB, request);

        dynamoDBMapper.batchDelete(repository.findAll());
    }

    @After
    public void tearDown() {
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB, dynamoDBMapperConfig);

        DeleteTableRequest request = dynamoDBMapper.generateDeleteTableRequest(Widget.class);
        TableUtils.deleteTableIfExists(amazonDynamoDB, request);
    }

    @Test
    public void shouldPersistWidget() {
        Widget widget = new FakeWidget();
        repository.save(widget);

        Optional<Widget> result = repository.findById(widget.getId());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualToComparingFieldByField(widget);
    }

    @Test
    public void shouldReturnPageOfWidgets() {
        Widget widget1 = new FakeWidget();
        Widget widget2 = new FakeWidget();
        Widget widget3 = new FakeWidget();
        repository.save(widget1);
        repository.save(widget2);
        repository.save(widget3);

        Page<Widget> result = repository.findAll(PageRequest.of(0, 2));

        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(result.getTotalElements()).isEqualTo(3L);
        assertThat(result).hasSize(2);
        assertThat(result.getContent()).containsAnyOf(widget1, widget2, widget3);
    }

    @Test
    public void shouldDeleteWidget() {
        Widget widget = new FakeWidget();
        repository.save(widget);

        Optional<Widget> result = repository.findById(widget.getId());
        assertThat(result.isPresent()).isTrue();

        repository.deleteById(widget.getId());

        result = repository.findById(widget.getId());
        assertThat(result.isPresent()).isFalse();
    }

}


