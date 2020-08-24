package com.example.lib

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//[WIP] Event class responsible for publishing and subscribing using RXJava
//Domain and presenter processes are synchronous but will be changed to be async

@Suppress("UNCHECKED_CAST")
class ControllerBasicEvent<COut,DOut> private constructor(val eventName: String) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val viewModels = mutableMapOf<String, ((DOut) -> Unit)?>()
    private var domain : ((COut?) -> DOut) = {input -> input as DOut}

    fun publishEvent(value: COut? =  null, id: String = DEFAULT_ID){
        scope.launch {
            viewModels[eventName+id]?.also{it(domain(value))}
        }
    }
    fun registerViewModel(handler: (DOut) -> Unit, id: String = DEFAULT_ID) {
        viewModels[eventName+id] = handler
    }
    fun unregisterViewModel(id: String = DEFAULT_ID){
        viewModels[eventName+id] = null
    }
    fun registerDomain(domainProcess: (COut?) -> DOut){
        domain = domainProcess
    }

    companion object {
        const val DEFAULT_ID = "default"
        private var eventName = 0
        fun <COut,DOut>create() : ControllerBasicEvent<COut, DOut> {
            return ControllerBasicEvent(eventName++.toString())
        }
    }

}

