package com.example.lib

import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.example.lib.ControllerFullEvent.Companion.DEFAULT_ID

//[WIP] ViewModel that handles subscription and un-subscription
//Achieves memory leak safety by using lifecycle events and disposables

open class EventViewModel : ViewModel(), Observable{
    private val listOfFullEvents = mutableListOf<Pair<ControllerFullEvent<*, *, *>,String>>()
    private val listOfBasicEvents = mutableListOf<Pair<ControllerBasicEvent<*, *>, String>>()
    private val listOfUIEvents = mutableListOf<Pair<UIEvent<*>, String>>()

    fun <COut,DOut,POut>subscribe(event: ControllerFullEvent<COut, DOut, POut>, handler: (POut?) -> Unit, id: String = DEFAULT_ID){
        val wrappedHandler : (input:POut?) -> Unit = {input -> handler(input); notifyChange()}
        event.registerViewModel(wrappedHandler,id)
        listOfFullEvents.add(Pair(event, id))
    }

    fun <COut,DOut>subscribe(event: ControllerBasicEvent<COut, DOut>, handler: (DOut) -> Unit, id: String = DEFAULT_ID){
        val wrappedHandler : (input:DOut) -> Unit = {input -> handler(input); notifyChange()}
        event.registerViewModel(wrappedHandler,id)
        listOfBasicEvents.add(Pair(event, id))
    }

    fun <COut>subscribe(event: UIEvent<COut>, handler: (COut?) -> Unit, id: String = DEFAULT_ID){
        val wrappedHandler : (input: COut?) -> Unit = {input -> handler(input); notifyChange()}
        event.registerViewModel(wrappedHandler,id)
        listOfUIEvents.add(Pair(event, id))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unsubscribe(){
        listOfFullEvents.forEach { it.first.unregisterViewModel(it.first.eventName + it.second) }
        listOfBasicEvents.forEach { it.first.unregisterViewModel(it.first.eventName + it.second) }
        listOfUIEvents.forEach{ it.first.unregisterViewModel(it.first.eventName + it.second) }
    }

    @Transient
    private var mCallbacks: PropertyChangeRegistry? = null

    override fun addOnPropertyChangedCallback(callback: OnPropertyChangedCallback) {
        synchronized(this) {
            if (mCallbacks == null) {
                mCallbacks = PropertyChangeRegistry()
            }
        }
        mCallbacks!!.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: OnPropertyChangedCallback) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.remove(callback)
    }

    private fun notifyChange() {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.notifyCallbacks(this, 0, null)
    }
}
