package com.lucidchart.threadflow.scala

import com.lucidchart.threadflow.test.MockFlowStore
import java.util.concurrent.Executors
import org.specs2.mutable.Specification
import scala.concurrent.{ExecutionContext, Future}

object FlowSpec extends Specification {

  "apply(ExecutionContext)" should {
    "preserve flow id" in {
      val executorService = Executors.newSingleThreadExecutor
      try {
        val flow = new Flow(new MockFlowStore: ScalaFlowStore)
        implicit val executionContext = flow(ExecutionContext.fromExecutorService(executorService))
        def submit(id: Option[String]) {
          Future {
            flow.store.value = id
            Future {
              flow.store.value must_== id
            }
          }
        }
        submit(Some("foo"))
        submit(Some("bar"))
        submit(None)
      } finally {
        executorService.shutdown()
      }
      ok
    }
  }

}
