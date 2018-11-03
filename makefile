env=dev

build:
	mvn clean package

run:
	make build
	docker rm -f widget-dynamodb || true
	docker run --name widget-dynamodb -p 8000:8000 -d amazon/dynamodb-local
	mvn exec:java

deploy:
	make build
	sls deploy -s $(env)

remove:
	sls remove -s $(env)