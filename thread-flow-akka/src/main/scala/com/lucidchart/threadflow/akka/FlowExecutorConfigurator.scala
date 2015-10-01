package com.lucidchart.threadflow.akka

import akka.dispatch._
import com.lucidchart.threadflow._
import com.lucidchart.threadflow.scala.Flow
import com.typesafe.config.Config

/**
 * Preserves the thread-local flow id variable through an Akka executor.
 */
class FlowExecutorConfigurator(store: FlowStore, config: Config, prerequisites: DispatcherPrerequisites)
    extends CustomExecutorServiceConfigurator(config, prerequisites, "flow.proxy-executor")
    with ProxyExecutorServiceFactoryProvider {

  private[this] val flow = new Flow(store)

  protected def proxy[A](f: () => A) = flow(f)

}
