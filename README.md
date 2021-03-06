# Spring Cloud Function

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/8e064126d0b743ce83421a3d6b38c0be)](https://app.codacy.com/app/michaelruocco/spring-cloud-function?utm_source=github.com&utm_medium=referral&utm_content=michaelruocco/spring-cloud-function&utm_campaign=Badge_Grade_Dashboard)
[![BCH compliance](https://bettercodehub.com/edge/badge/michaelruocco/spring-cloud-function?branch=master)](https://bettercodehub.com/)

This project was created to try out using the spring cloud function framework. I have combined it
with the serverless framework to create some easily deployable but very simple spring cloud functions
onto AWS Lambda fronted by the AWS API Gateway.

## Building and deploying to AWS

The instructions below assume that you already have the Serverless framework configured and working
with your AWS account, instructions for doing this can be found
[here](https://serverless.com/framework/docs/providers/aws/guide/installation/).

```make
make deploy
```

If the serverless (sls) command (executed as part of make deploy) completes successfully it will print
various bits of output including the API gateway endpoints that have been created, the specific bits
of output you should be interested will read something like:

```
endpoints:
   GET - https://{your_api_gateway_endpoint}/dev/widgets/{id}
   POST - https://{your_api_gateway_endpoint}/dev/widgets
```

If the command has completed successfully you can then test your functions calling through the API
gateway by running the following commands:

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

The application depends on DynamoDB so in order for it to run successfully you need to have an instance
running locally. The preferred method for this is to use docker image provded by AWS. Assuming you have
docker installed and running, you run the application using  the following command.

```
make run
```

This will start up a two docker containers, one running dynamodb and another running the application on port 8080.
If you want to use a different port on the host machine you can achieve this by editing the port mapping in the
docker-compose.yml file.

Once the server is up and running you can send requests to it. However, because
there is no API gateway running on your local machine, you have to send a more complex request because
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
  * Add meta information required on page of data items by JSON API
  * Migrate to gradle
  * Add travis build pipeline
  * Cucumber tests to be added
  * Tests for AbstractAwsApiGatewayLambdaFunction should be removed and it (along with supporting classes) should be extracted into separate library
  * Secure the API using a cognito user pool