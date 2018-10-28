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
curl -X POST https://{your_api_gateway_endpoint}/dev/widgets -d '{ "id": 3, "description": "widget 3", "cost": { "amount": 6, "currency": "GBP" }, "price": { "amount": 22, "currency": "GBP" } }'
curl -X GET https://{your_api_gateway_endpoint}/dev/widgets/3
```

Should both return:

```
{ "id": 3, "description": "widget 3", "cost": { "amount": 6, "currency": "GBP" }, "price": { "amount": 22, "currency": "GBP" } }
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
curl -X POST -H "Content-Type:application/json" http://localhost:8080/postWidget -d '{"headers":{"Host":"localhost"},"requestContext":{"stage":"local"},"resource":"/widgets","body":"{\"id\":1,\"description\":\"my-widget\",\"cost\":{\"amount\": 10,\"currency\":\"GBP\"},\"price\":{\"amount\":12,\"currency\":\"GBP\"}}"}'
```

Should give a response:

```
{"statusCode":201,"headers":{"Location":"https://localhost/local/widgets/1"},"body":"{\"id\":1,\"description\":\"my-widget\",\"cost\":{\"amount\":10.00,\"currency\":\"GBP\"},\"price\":{\"amount\":12.00,\"currency\":\"GBP\"}}"}
```

And:

```
curl -X POST -H "Content-Type:application/json" http://localhost:8080/getWidget -d '{"headers":{"Host":"localhost"},"requestContext":{"stage": "local"},"resource":"/widgets","pathParameters":{"id":1}}'
```

Should give a response:

```
{"statusCode":200,"headers":{},"body":"{\"id\":1,\"description\":\"my-widget\",\"cost\":{\"amount\":10.00,\"currency\":\"GBP\"},\"price\":{\"amount\":12.00,\"currency\":\"GBP\"}}"}
```

## Things still to do

* Request and response bodies needs to be updated to follow JSON API spec
* Add ID field to error response documents to follow JSON API spec
* Cucumber tests to be added
* Add validation for incoming payload on POST request
* Add functionality for PATCH and DELETE endpoints
* Tests for AbstractAwsLambdaFunction will be removed when it is extracted into separate library and only functions will be unit tested
* Look into how to secure an API in API gateway
* Secure the API using a cognito user pool