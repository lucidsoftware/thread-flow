package com.lucidchart.threadflow.akka

import akka.dispatch._
import com.lucidchart.threadflow._
import com.typesafe.config.Config

class GlobalFlowExecutorConfigurator(config: Config, prerequisites: DispatcherPrerequisites)
    extends FlowExecutorConfigurator(ThreadLocalStore.GLOBAL, config, prerequisites)
