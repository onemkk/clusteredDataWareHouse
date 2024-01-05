
# ClusteredData Warehouse

This app accepts fx deals details from different currencies and persist them into DB.

You can perform the following:
* Create new deals
* Find all deals 
* Get a particular deal using the id.

##  Quick Run - Docker
```sh
mvn clean install
```
```sh
docker compose up
```
* The first command will ensure the program is packaged with a jar file in the target folder .
* Ensure Docker desktop is running on your machine. The second command will pull an image of sql, run the application make the application endpoints available with a baseURL of http://localhost:8080/<endpoint>.
* 


## Project Documentation

### Technology Used
* SPRINGBOOT
* MYSQL
* DOCKER

## Project Packages
### Controller
* POST - /warehouse/api/deals - is used to create a new deal.
* GET - /warehouse/api/deals - used to get all existing deals in the database.
* GET - warehouse/api/deals/id/{id} - used to the deal with the id that s passed as a path variable.

### Exception
* The global exception handler deals with exceptions, especially all that can be traced to the controllers as entry points

### Logging
* log4j2 is used to log through out the application.
* stack traces from the controller, services and repositories are logged

### Model

* dealId - dealId for numbering on the database : Long
* fromCurrencyIsoCode - ISO Currency from deal: Currency
* toCurrencyIsoCode - ISO Currency to deal with Datatype: Currency
* dealTimestamp - Instant time of deal use epoch time: Instant
* dealAmount - Amount of fx deal or transaction: BigDecimal

### Repository
* Spring data JPA was used

### Service and Impl
*  Deal DTO(Data Transfer Object).
* Service Implementation contains the business logic.
* The utilities package contains utility classes for converting from model to DTO and vise versa, and capability for enforcing validation

### Request body
```json
{
    "dealId": 21,
    "fromCurrencyIsoCode" : "USD",
    "toCurrencyIsoCode" : "EUR",
    "dealAmount": 50
}
```

### Success Response

```json
{
  "message": "Success",
  "data": {
    "id": 2
  }
}
```

TEST
- Unit test in the test folder covers:
* Deal repository
* Deal service 
* Deal Controller
