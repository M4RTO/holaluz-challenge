# HolaLuz Fraud Detection

This code is a solution to detect fraud

## Run docker compose

This command generate a docker container up and running:
```
docker compose up -d
```

With CURL or postman send http POST request with this url:
```
curl --location 'localhost:8080/api/v1/frauds' \
--form 'file=@"/DESTINATION_FILE/2016-readings.csv"'```
```


Then with this CURL or postman send http GET request and find a fraud with client id:
```
curl --location 'localhost:8080/api/v1/frauds/{client_id}```
```
And respond with a pdf result.

This url you can show de DB to see the suspicious readings:

```
http://localhost:8080/h2-console/
```
In this database you can enter with these credentials:

```
JDBC URL: jdbc:h2:mem:mydb
User Name:	sa
Password: password

```
Enter to SUSPICIOUS DATABASE and show the fraud reading of each client