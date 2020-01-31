.PHONY:
	db test

db:
	docker exec -it beerchopper_db_1 /usr/bin/mysql -p123 beerchopper

test:
	docker-compose run --entrypoint 'gradle -t test' app
