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
   POST - https://{your_api_gateway_endpoint}/dev/reverse
   POST - https://{your_api_gateway_endpoint}/dev/greet
```

If the command has completed successfully you can then test your functions calling through the API
gateway by running the following command:

```
curl https://{your_api_gateway_endpoint}/dev/greet -d '{ "value": "Joe Bloggs" }'
curl https://{your_api_gateway_endpoint}/dev/reverse -d '{ "value": "Joe Bloggs" }'
```

Should return:

```
{"value":"Hello Joe Bloggs, and welcome to Spring Cloud Function!!!"}
{"value":"sggolB eoJ"}
```

Bare in mind that the first time each service is called it will be performing a "cold start" so will
likely take between 10-12 seconds to respond, following that you should get a much quicker response.

## Running Locally

To run and test locally you can either run directly from maven

```
mvn clean install exec:java
```

Or you can build the jars and execute the fat jar used for running locally:

```
mvn clean package
java -jar target/spring-cloud-function-1.0.0-SNAPSHOT.jar
```

In either case, when once the server is up and running you can send requests to it. However, because
there is no API gateway running on your local machine you have to send a more complex request, because
you need to mimic the work that is done by the API Gateway.

```
curl http://localhost:8080/greet -H "Content-Type: application/json" -d '{ "body": "{\"value\":\"Joe Bloggs\"}" }'
curl http://localhost:8080/reverse -H "Content-Type: application/json" -d '{ "body": "{\"value\":\"Joe Bloggs\"}" }'
```

However, both of these requests strangely fail when running locally for the same reason, the body value
field does not seem to be picked up correctly and so is always reported as null, this means that the first
curl request listed above returns a message containing "null" instead of the value passed in, and the second
request fails with an error because a StringBuilder cannot take a null argument. Further investigation is required
as to why this is happening. I suspect that I am using an old/incorrect or buggy version of one of libraries, or
I have uncovered a bug that needs fixing. I intend to start by raising an issue against the Spring Cloud Functions
project and then will go from there.