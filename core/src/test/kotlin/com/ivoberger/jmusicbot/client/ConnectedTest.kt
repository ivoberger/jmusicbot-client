package com.ivoberger.jmusicbot.client

import com.ivoberger.jmusicbot.client.model.Auth
import com.ivoberger.jmusicbot.client.model.Event
import com.ivoberger.jmusicbot.client.testUtils.PrintTree
import com.ivoberger.jmusicbot.client.testUtils.enterConnectedState
import com.ivoberger.jmusicbot.client.testUtils.newTestUser
import com.ivoberger.jmusicbot.client.testUtils.toToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import timber.log.Timber

@ExperimentalCoroutinesApi
internal class ConnectedTest {

    companion object {
        private lateinit var mMockServer: MockWebServer
        private lateinit var mBaseUrl: String
        private var mPort: Int = 0

        @JvmStatic
        @BeforeAll
        fun setUp() {
            Timber.plant(PrintTree())
        }

        @JvmStatic
        @AfterAll
        fun tearDown() {
            Timber.uprootAll()
        }
    }

    @BeforeEach
    fun testSetUp() {
        mMockServer = MockWebServer()
        mBaseUrl = mMockServer.hostName
        mPort = mMockServer.port
        val token = Auth.Token(newTestUser.toToken())
        JMusicBot.stateMachine.enterConnectedState(
            Event.ServerFound(mBaseUrl, mPort), Event.Authorize(newTestUser, token)
        )
        Assertions.assertEquals("http://$mBaseUrl:$mPort/", JMusicBot.baseUrl)
        Assertions.assertEquals(newTestUser, JMusicBot.user)
        Assertions.assertEquals(token, JMusicBot.authToken)
    }

    @AfterEach
    fun testTearDown() {
        JMusicBot.stateMachine.transition(Event.Disconnect())
        mMockServer.shutdown()
    }

    @Test
    fun queueUpdates() {
    }

    @Test
    fun playerUpdates() {
    }
}
