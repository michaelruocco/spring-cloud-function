env=dev

clean:
	mvn clean
	docker-compose rm -f

build:
	mvn clean package

run:
	make build
	docker build -t michaelruocco/widget-app .
	docker-compose up

deploy:
	make build
	sls deploy -s $(env)

remove:
	sls remove -s $(env)