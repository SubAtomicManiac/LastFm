package com.example.lib

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//[WIP] Event class responsible for publishing and subscribing using RXJava
//Domain and presenter processes are synchronous but will be changed to be async

@Suppress("UNCHECKED_CAST")
class DomainControllerEvent<DOut> private constructor(val eventName: String) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val controllers = mutableMapOf<String, (() -> DOut?)?>()
    private var domain : ((DOut?) -> Unit) = {_ -> Unit}

    fun publishEvent(value: DOut? =  null, id: String = DEFAULT_ID){
        scope.launch {
           domain(value)
        }
    }

    fun registerDomain(domainProcess: (DOut?) -> Unit){
        domain = domainProcess
    }

    fun registerController(handler: () -> DOut, id: String = ControllerBasicEvent.DEFAULT_ID) {
        controllers[eventName+id] = handler
    }
    fun unregisterController(id: String = ControllerBasicEvent.DEFAULT_ID){
        controllers[eventName+id] = null
    }

    companion object {
        const val DEFAULT_ID = "default"
        private var eventName = 0
        fun <DOut>create() : DomainControllerEvent<DOut> {
            return DomainControllerEvent(eventName++.toString())
        }
    }

}

