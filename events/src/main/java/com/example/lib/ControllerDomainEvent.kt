package com.example.lib

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//[WIP] Event class responsible for publishing and subscribing using RXJava
//Domain and presenter processes are synchronous but will be changed to be async

@Suppress("UNCHECKED_CAST")
class ControllerDomainEvent<COut> private constructor(val eventName: String) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var domain : ((COut?) -> Unit) = { _ -> }

    fun publishEvent(value: COut? =  null, id: String = DEFAULT_ID){
        scope.launch {
            domain(value)
        }
    }

    fun registerDomain(domainProcess: (COut?) -> Unit){
        domain = domainProcess
    }

    companion object {
        const val DEFAULT_ID = "default"
        private var eventName = 0
        fun <COut>create() : ControllerDomainEvent<COut> {
            return ControllerDomainEvent(eventName++.toString())
        }
    }

}

