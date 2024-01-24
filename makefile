IMAGE_NAME:=postgres
CONTAINER_NAME:=postgres-db
PORT:=5432

docker-build:
	docker build -t $(IMAGE_NAME) .

docker-run:
	docker run --name $(CONTAINER_NAME) -p $(PORT):$(PORT) -d $(IMAGE_NAME)

docker-restart:
	docker restart $(CONTAINER_NAME)

docker-stop:
	docker stop $(CONTAINER_NAME)

clean:
	docker rm $(CONTAINER_NAME)
	docker rmi $(IMAGE_NAME)
