## Alexa Weather Application

[![Build Status](https://travis-ci.org/szeyick/AlexaWeatherApp.svg?branch=master)](https://travis-ci.org/szeyick/AlexaWeatherApp)

This repository contains a Java application that will listen to messages being posted to AWS-IoT and process the inbound request. The inbound message contains a weather request that has been passed on from the Amazon Echo.

The application is the companion to the Alexa Weather skill that is contained in another repository. This functions as the backend processor to retrieve and process the requested data. It is this application that downloads and processes the data from the Australian Bureau of Meteorology.
