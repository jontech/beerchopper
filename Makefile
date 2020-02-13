.PHONY:
	db tags setup run devel

db:
	docker exec -it beerchopper_db_1 /usr/bin/mysql -p123 beerchopper

setup:
	docker-compose run --entrypoint 'gradle setup' app

run:
	docker-compose run --entrypoint 'gradle run' app

tags:
	find . -name '*.java' | etags -

devel:
	docker-compose --no-ansi up app
