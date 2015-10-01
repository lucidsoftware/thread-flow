package com.lucidchart.threadflow.httpclient

import com.lucidchart.threadflow.test.MockFlowStore
import org.apache.http.client.methods.HttpGet
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.apache.http.{HttpRequest, HttpResponse}
import org.apache.http.impl.bootstrap.ServerBootstrap
import org.apache.http.protocol.{HttpContext, HttpRequestHandler}
import org.specs2.mutable.Specification

object FlowHttpRequestInterceptorSpec extends Specification {

  "FlowHttpRequestInterceptor" should {
    "set X-Flow-Id header" in {
      val server = ServerBootstrap.bootstrap
        .setListenerPort(6932)
        .registerHandler("*", new HttpRequestHandler {
          def handle(request: HttpRequest, response: HttpResponse, context: HttpContext) = {
            Option(request.getFirstHeader("X-Flow-Id")).foreach { header =>
              response.setEntity(new StringEntity(header.getValue))
            }
          }
        })
        .create

      server.start()
      val header = try {
        val flow = new HttpFlow(new MockFlowStore)
        val client = flow.addFlowHeader(HttpClients.custom).build
        try {
          flow.setValue("foo")
          val response = client.execute(new HttpGet("http://localhost:6932/"))
          EntityUtils.toString(response.getEntity)
        } finally {
          client.close()
        }
      } finally {
        server.shutdown(0, null)
      }

      header must_== "foo"
    }
  }

}
