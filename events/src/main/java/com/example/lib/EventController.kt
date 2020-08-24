package com.example.lib

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.example.lib.ControllerFullEvent.Companion.DEFAULT_ID

//[WIP] BaseController is effectively just a viewModel

open class EventController : ViewModel(){
    private val listOfDomainEvents = mutableListOf<Pair<DomainControllerEvent<*>,String>>()

    fun <DOut>subscribe(event: DomainControllerEvent<DOut>, handler: () -> DOut, id: String = DEFAULT_ID){
        event.registerController(handler,id)
        listOfDomainEvents.add(Pair(event, id))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unsubscribe(){
        listOfDomainEvents.forEach { it.first.unregisterController(it.first.eventName + it.second) }
    }
}

