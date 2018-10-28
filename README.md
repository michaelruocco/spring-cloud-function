# Spring Cloud Function

This project was created to try out using the spring cloud function framework. I have combined it
with the serverless framework to create some easily deployable but very simple spring cloud functions
onto AWS Lambda fronted by the AWS API Gateway.

## Building and deploying to AWS

The instructions below assume that you already have the Serverless framework configured and working
with your AWS account, instructions for doing this can be found
[here](https://serverless.com/framework/docs/providers/aws/guide/installation/).

```
mvn clean package // builds thin jar for aws deployment, and fat jar for running locally
sls deploy // deploys jar to aws and creates two lambda functions behind API gateway
```

If the serverless (sls) command completes successfully it will print various bits of output including
the API gateway endpoints that have been created, the specific bits of output you should be interested
in read:

```
endpoints:
   GET - https://{your_api_gateway_endpoint}/dev/widgets/{id}
   POST - https://{your_api_gateway_endpoint}/dev/widgets
```

If the command has completed successfully you can then test your functions calling through the API
gateway by running the following command:

```
curl -X POST https://{your_api_gateway_endpoint}/dev/widgets -d '{ "data": { "id": "d60d5c08-d5cc-4258-bbab-241e30dd6791", "type": "widgets", "attributes": { "description": "my widget", "cost": { "amount": 10.00, "currency": "GBP" }, "price": { "amount": 15.00, "currency": "GBP" } } } }'
curl -X GET https://{your_api_gateway_endpoint}/dev/widgets/d60d5c08-d5cc-4258-bbab-241e30dd6791
```

Should both return:

```
{ "data": { "id": "d60d5c08-d5cc-4258-bbab-241e30dd6791", "type": "widgets", "attributes": { "description": "my widget", "cost": { "amount": 10.00, "currency": "GBP" }, "price": { "amount": 15.00, "currency": "GBP" } } } }
```

Bare in mind that the first time each service is called it will be performing a "cold start" so will
likely take between 16-18 seconds to respond, following that you should get a much quicker response.

## Running Locally

To run and test locally you can either run directly from maven. The application depends on DynamoDB
so in order for it to run successfully you need to have an instance running locally. The preferred
method for this is to use docker image provded by AWS. Assuming you have docker installed and
running you can do this by running the following command.

```
docker run --name widget-dynamodb -p 8000:8000 -d amazon/dynamodb-local
```

This will start up a container running dynamodb on port 8000 named widget-dynamodb. Once this is
running you can run the following command to start the application.

```
mvn clean install exec:java
```

Once the server is up and running you can send requests to it. However, because
there is no API gateway running on your local machine you have to send a more complex request, because
you need to mimic the work that is done by the API Gateway.

```
curl -X POST -H "Content-Type:application/json" http://localhost:8080/postWidget -d '{"headers":{"Host":"localhost"},"requestContext":{"stage":"local"},"resource":"/widgets","body":"{\"data\":{\"id\":\"d60d5c08-d5cc-4258-bbab-241e30dd6791\",\"type\":\"widgets\",\"attributes\":{\"description\":\"my widget\",\"cost\":{\"amount\":10.00,\"currency\":\"GBP\"},\"price\":{\"amount\":15.00,\"currency\":\"GBP\"}}}}"}'
```

Should give a response:

```
{"statusCode":201,"headers":{"Location":"https://localhost/local/widgets/d60d5c08-d5cc-4258-bbab-241e30dd6791"},"body":"{\"data\":{\"id\":\"d60d5c08-d5cc-4258-bbab-241e30dd6791\",\"type\":\"widgets\",\"attributes\":{\"description\":\"my widget\",\"cost\":{\"amount\":10.00,\"currency\":\"GBP\"},\"price\":{\"amount\":15.00,\"currency\":\"GBP\"}}}}"}
```

And:

```
curl -X POST -H "Content-Type:application/json" http://localhost:8080/getWidget -d '{"headers":{"Host":"localhost"},"requestContext":{"stage": "local"},"resource":"/widgets","pathParameters":{"id":"d60d5c08-d5cc-4258-bbab-241e30dd6791"}}'
```

Should give a response:

```
{"statusCode":200,"headers":{},"body":"{\"data\":{\"id\":\"d60d5c08-d5cc-4258-bbab-241e30dd6791\",\"type\":\"widgets\",\"attributes\":{\"description\":\"my widget\",\"cost\":{\"amount\":10.00,\"currency\":\"GBP\"},\"price\":{\"amount\":15.00,\"currency\":\"GBP\"}}}}"}
```

## Things still to do

* Add validation for incoming payload on POST request
* Add pagination to get all widget endpoint
* Add functionality for PATCH and DELETE
* Add missing unit test coverage
* Cucumber tests to be added
* Tests for AbstractAwsLambdaFunction should be removed and it (along with supporting classes) should be extracted into separate library
* Secure the API using a cognito user pool