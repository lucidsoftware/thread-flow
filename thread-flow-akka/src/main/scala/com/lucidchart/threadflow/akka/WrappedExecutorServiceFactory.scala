package com.lucidchart.threadflow.akka

import akka.dispatch.ExecutorServiceFactory

class WrappedExecutorServiceFactory(factory: ExecutorServiceFactory) extends ExecutorServiceFactory {

  def createExecutorService = factory.createExecutorService

}
