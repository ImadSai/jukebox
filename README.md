# Jukebox Settings API

Web application to remote control Jukebox settings.  
Depending on what components (hardware modules) a jukebox has, it may or may not support a setting.

## Install

    mvn install

## Run the app

    java -jar target/juckboxSettings-*.jar com.theoctavegroup.jukeboxsettings.JukeboxSettingsApplication

## Run the tests

    mvn test

# REST API

The jukeboxSettings REST API described below.

## Get list of Jukebox supported by setting and filtered by Model

### Request

`GET /getJukeboxes`

    curl -i -H 'Accept: application/json' -d 'settingId=Foo' http://localhost:8080/getJukeboxes

### Response

    HTTP/1.1 200 OK
    Date: Sun, 30 May 2021 04:44:55 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/json

    []

## Get Application Health and Information

### Request

`GET /actuator`

    curl -i -H 'Accept: application/json' http://localhost:8080/actuator/health

### Response

    HTTP/1.1 200 OK
    Date: Sun, 30 May 2021 04:44:55 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/json

    {
        "status": "UP"
    }

## Get Application API Docs ( Swagger )

### Request

UI version :

`http://localhost:8080/swagger-ui.html`

Json format :

`http://localhost:8080/v2/api-docs`
