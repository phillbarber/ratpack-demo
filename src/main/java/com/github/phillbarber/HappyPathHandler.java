package com.github.phillbarber;

import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.http.client.HttpClient;

import javax.inject.Singleton;
import java.net.URI;

@Singleton
public class HappyPathHandler implements Handler {


  @Override
  public void handle(Context context) throws Exception {

      HttpClient httpClient = context.get(HttpClient.class);            //get httpClient
      URI uri = new URI("http://localhost:1234/fast-endpoint");

      httpClient.get(uri).then(response -> {
          context.render("Downstream system returned: " + response.getBody().getText());  //Render the response from the httpClient GET request
              }
      );
  }
}
