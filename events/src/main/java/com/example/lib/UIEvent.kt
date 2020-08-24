package com.example.lib

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UIEvent<COut> private constructor(val eventName: String){
    private val scope = CoroutineScope(Dispatchers.IO)
    private val viewModels = mutableMapOf<String, ((COut?) -> Unit)?>()

    fun publishEvent(value: COut? =  null, id: String = DEFAULT_ID){
        scope.launch {
            viewModels[eventName+id]?.also{it(value)}
        }
    }
    fun registerViewModel(handler: (COut?) -> Unit, id: String = DEFAULT_ID) {
        viewModels[eventName+id] = handler
    }
    fun unregisterViewModel(id: String = DEFAULT_ID){
        viewModels[eventName+id] = null
    }

    companion object {
        const val DEFAULT_ID = "default"
        private var eventName = 0
        fun <COut>create() : UIEvent<COut> {
            return UIEvent(eventName++.toString())
        }
    }
}
