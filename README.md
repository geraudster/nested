# Avro schemas dependencies test

```sh
mvn clean install
mvn -f customer-producer/pom.xml jib:dockerBuild
docker-compose up -d
```

Start an Avro Consumer:

```sh
docker-compose exec base kafka-avro-console-consumer \
    --bootstrap-server kafka:9092 \
    --topic customer \
    --property schema.registry.url=http://schema-registry:8081
```

In another window, start Customer producer: 

```sh
docker run --network=nestedavro_confluent customerproducer
```
