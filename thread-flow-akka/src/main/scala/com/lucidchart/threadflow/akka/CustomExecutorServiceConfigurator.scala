package com.lucidchart.threadflow.akka

import akka.dispatch._
import com.typesafe.config.Config
import java.util.concurrent.ThreadFactory

class CustomExecutorServiceConfigurator(config: Config, prerequisites: DispatcherPrerequisites, key: String)
    extends ExecutorServiceConfigurator(config, prerequisites) {

  private val underlying = {
    val fakeConfig = config.withValue("executor", config.getValue(key))
    // It doesn't seem like MessageDispatcherConfigurator was meant for this, but it works
    new MessageDispatcherConfigurator(fakeConfig, prerequisites) {
      def dispatcher() = ???
    }.configureExecutor()
  }

  def createExecutorServiceFactory(id: String, threadFactory: ThreadFactory) =
    underlying.createExecutorServiceFactory(id, threadFactory)

}
