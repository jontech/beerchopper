.PHONY:
	db test tags

db:
	docker exec -it beerchopper_db_1 /usr/bin/mysql -p123 beerchopper

db-shell:
	docker exec -it beerchopper_db_1 /bin/bash

app-shell:
	docker-compose run app "/bin/bash"

test:
	docker-compose run --entrypoint 'gradle -t test' app

setup:
	docker-compose run --entrypoint 'gradle setup' app

tags:
	find . -name '*.java' | etags -
