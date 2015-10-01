package com.lucidchart.threadflow.java

import com.lucidchart.threadflow.test.MockFlowStore
import java.util.concurrent.{Callable, Executors}
import org.specs2.mutable.Specification

object FlowSpec extends Specification {

  "wrapCallable" should {
    "preverse flow id" in {
      val flow = new Flow(new MockFlowStore)
      flow.getStore.set("foo")
      val wrap = flow.wrapCallable(new Callable[Unit] {
        def call() = flow.getStore.get must_== "foo"
      })
      wrap.call()
      flow.getStore.get must beNull
    }
  }

  "wrapExecutorService" should {
    "preserve flow id" in {
      val flow = new Flow(new MockFlowStore)
      val executorService = flow.wrapExecutorService(Executors.newSingleThreadExecutor)
      def submit(id: Option[String]) {
        executorService.submit(new Runnable {
          def run() = {
            flow.getStore.set(id.orNull)
            new Runnable {
              def run() = {
                Option(flow.getStore.get()) must_== id
              }
            }
          }
        })
      }
      submit(Some("foo"))
      submit(Some("bar"))
      submit(null)
      ok
    }
  }

}
