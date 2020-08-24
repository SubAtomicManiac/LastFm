package com.example.lib

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//[WIP] Event class responsible for publishing and subscribing using RXJava
//Domain and presenter processes are synchronous but will be changed to be async

@Suppress("UNCHECKED_CAST")
class ControllerFullEvent<COut,DOut,POut> private constructor(val eventName: String) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val viewModels = mutableMapOf<String, ((POut?) -> Unit)?>()
    private var domain : (suspend (COut?) -> DOut?) = {input -> input as DOut}
    private var presenter : ((DOut?) -> POut?) = {input -> input as POut}

    fun publishEvent(value: COut? =  null, id: String = DEFAULT_ID){
        scope.launch {
            viewModels[eventName+id]?.also{it((presenter(domain(value))))}
        }
    }
    fun registerViewModel(handler: (POut?) -> Unit, id: String = DEFAULT_ID) {
        viewModels[eventName+id] = handler
    }
    fun unregisterViewModel(id: String = DEFAULT_ID){
        viewModels[eventName+id] = null
    }
    fun registerDomain(domainProcess: suspend (COut?) -> DOut?){
        domain = domainProcess
    }

    fun registerPresenter(presenterProcess: (DOut?) -> POut?){
        presenter = presenterProcess
    }

    companion object {
        const val DEFAULT_ID = "default"
        private var eventName = 0
        fun <COut,DOut,POut>create() : ControllerFullEvent<COut, DOut, POut> {
            return ControllerFullEvent(eventName++.toString())
        }
    }

}

