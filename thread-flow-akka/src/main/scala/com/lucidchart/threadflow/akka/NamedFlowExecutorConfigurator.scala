package com.lucidchart.threadflow.akka

import akka.dispatch._
import com.lucidchart.threadflow._
import com.typesafe.config.Config

class NamedFlowExecutorConfigurator(config: Config, prerequisites: DispatcherPrerequisites)
    extends FlowExecutorConfigurator(new NamedStore(config.getString("flow.name")), config, prerequisites)
