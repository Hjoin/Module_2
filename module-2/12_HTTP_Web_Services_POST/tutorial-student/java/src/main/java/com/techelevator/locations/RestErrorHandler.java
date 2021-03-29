package com.techelevator.locations;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import com.techelevator.services.ConsoleService;

import java.io.IOException;

class RestErrorHandler extends DefaultResponseErrorHandler {

    ConsoleService console = new ConsoleService();

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {

        if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {

        } else if (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
               console.printError("That location was not found. Please try again."); ;
            }
        }
    }
}
