package com.example.lastfm.unittests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.lib.*
import com.example.lib.DomainFullEvent
import io.mockk.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule

open class EventUnitTest {

    @Before
    fun injectMocks() { MockKAnnotations.init(this, relaxUnitFun = true) }

    @After
    fun tearDown() { unmockkAll() }

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    fun <COut>mockPublishEvent(event: ControllerFullEvent<COut, *, *>, cOut: COut? = null){
        mockkObject(event)
        every {event.publishEvent(cOut)} just Runs
    }

    fun <COut>mockPublishEvent(event: ControllerBasicEvent<COut, *>, cOut: COut? = null){
        mockkObject(event)
        every {event.publishEvent(cOut)} just Runs
    }

    fun <COut>mockPublishEvent(event: ControllerDomainEvent<COut>, cOut: COut? = null){
        mockkObject(event)
        every {event.publishEvent(cOut)} just Runs
    }

    fun <COut>mockPublishEvent(event: DomainFullEvent<COut, *>, cOut: COut? = null){
        mockkObject(event)
        every {event.publishEvent(cOut)} just Runs
    }

    fun mockPublishEvent(event: DomainBasicEvent<*>){
        mockkObject(event)
        every {event.publishEvent()} just Runs
    }

    fun <COut>mockPublishEvent(event: DomainControllerEvent<COut>, cOut: COut? = null){
        mockkObject(event)
        every {event.publishEvent(cOut)} just Runs
    }

    fun <COut>mockPublishEvent(event: UIEvent<COut>, cOut: COut? = null){
        mockkObject(event)
        every {event.publishEvent(cOut)} just Runs
    }
}
