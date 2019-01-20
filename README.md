# Vanhack Java Challenge

### Table of Contents
- [Description](#description)
- [Requirements](#requirements)
- [Additional Requirements](#additional-requirements)
- [Assessment](#assessment)
- [Development Tools](#development-tools)
- [Technical Specification](#technical-specification)
- [Getting Started](#getting-started)
- [API](#api)
    - [Create Short URL](#create-short-url)
    - [Redirect to Original URL](#redirect-to-original-url)
    - [Code Documentation](#code-documentation)
    - [Docker Image](#docker-image)
    - [Running Localy](#running-localy)

## Description

Most of us are familiar with seeing URLs like bit.ly or t.co on our Twitter or Facebook feeds. These are examples of shortened URLs, which are a short alias or pointer to a longer page link. For example, I can send you the shortened URL http://bit.ly/SaaYw5 that will forward you to a very long Google URL with search results on how to iron a shirt.

## Requirements

- Design and implement an API for short URL creation
- Implement forwarding of short URLs to the original ones
- There should be some form of persistent storage
- The application should be distributed as one or more Docker images

- Must be readable, maintainable, and extensible

## Additional Requirements

- Design and implement an API for gathering statistic

## Assessment

Treat this as a real project. It should be readable, maintainable, and extensible where appropriate.

The implementation should preferably be in Java, however any language can be used.

If you will transfer it to another team - it should be clear how to work with it and what is going on.

You should send us a link to a Git repository that we will be able to clone.

## Technical Specification

- Language: Java, version 8
- Framework: Spring Boot, version 2.1.0
- Database: H2, version 1.4.197

## Development Tools

- Eclipse IDE 2018-12, version 4.9.0
- Apache Maven, version 3.6.0
- Apache Maven Javadoc Plugin, version 3.0.1
- Docker Maven Plugin, version 0.20.1

## Getting Started

### API

The app exposes the two following URL links.

#### Create Short URL

    /shrink?url=:longUrl

**Required:**`longUrl=[string]`

This URL will return a JSON in the format:
```
{
    shortUrl: [alphanumeric],
    longUrl: [string],
    created: [datetime],
    clicks: [long]
}
```
**Note:** If the longUrl hasn't been created before this JSON will come with the `shortUrl` representation and `clicks` equal to **0**, otherwise `clicks` will return the statistic showing how many times this `shortUrl` link has been clicked since its creation.

#### Redirect to Original URL

    /redirect/:shortUrl

**Required:**`shortUrl=[alphanumeric]`

### Code Documentation

Generating the code documentation is just run the following maven command:

    mvn javadoc:javadoc

Then the javadocs documentation will be accessible
at `./target/site/index.html` directory.

### Docker Image

Generating the Docker image just run the following maven command:

    mvn docker:save

### Running Localy

Run this app localy executing the following command in the terminal:

    mvn package && java -jar target/urlshortener-1.0.0-SNAPSHOT.jar

Now you can open the browser of your preference and type `http://localhost:8080/shrink?url=www.vanhack.com` to get the created short URL pointing to *www.vanhack.com*.

