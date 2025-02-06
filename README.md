SwiftDataAPI
===========

SwiftDataAPI is a basic API that provides access to the SWIFT codes database through RESTful endpoints.

## Endpoints

### GET /v1/swift-codes/{swift-code}

Returns the SWIFT code data for the given SWIFT code.

### GET /v1/swift-codes/country/{countryISO2code}

Returns the list of SWIFT codes data for the given country.

### POST /v1/swift-codes

Creates a new SWIFT code entry.

### DELETE /v1/swift-codes/{swift-code}

Deletes the SWIFT code entry for the given SWIFT code.

## Build (Maven) and Test

To build the project, run the following command (in the project root directory):

```bash
mvn clean install
```

## Deploy and Run (Docker)

To deploy the project, run the following command (in the project root directory):

```bash
docker-compose up
```
