database-docker-up:
	docker run -d --rm -e MONGO_INITDB_ROOT_USERNAME=brandDiscountsUser -e MONGO_INITDB_ROOT_PASSWORD=brandDiscountsPassword -p 27017:27017 --name mongo -v "$(shell pwd)/database":/database mongo:latest

database-provision:
	docker exec mongo bash -c '../database/import.sh localhost'

database-up:
	make database-docker-up
	make database-provision

database-reset:
	make database-down
	make database-up

database-down:
	docker rm -f mongodb-local
