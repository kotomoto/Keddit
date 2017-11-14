package com.koto.keddit

import com.koto.keddit.api.NewsAPI
import com.koto.keddit.models.RedditNews
import com.koto.keddit.restapi.model.RedditChildrenResponse
import com.koto.keddit.restapi.model.RedditDataResponse
import com.koto.keddit.restapi.model.RedditNewsDataResponse
import com.koto.keddit.restapi.model.RedditNewsResponse
import io.reactivex.observers.TestObserver
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import retrofit2.Call
import retrofit2.Response
import java.util.*

class NewsManagerTest {
    var testSub = TestObserver<RedditNews>()
    var callMock = mock<Call<RedditNewsResponse>>()
    var apiMock = mock<NewsAPI>()

    @Before
    fun setUp() {
        testSub = TestObserver<RedditNews>()
        callMock = mock()
        apiMock = mock<NewsAPI>()
        `when`(apiMock.getNews(anyString(), anyString())).thenReturn(callMock)
    }

    @Test
    fun success_basic() {
        // given
        val redditNewsResponse = RedditNewsResponse(RedditDataResponse(listOf(), null, null))
        val response = Response.success(redditNewsResponse)

        `when`(callMock.execute()).thenReturn(response)

        // when
        val newsManager = NewsManager(apiMock)
        newsManager.getNews("").subscribe(testSub)

        // then
        testSub.assertNoErrors()
        testSub.assertValueCount(1)
        testSub.assertComplete()
    }

    @Test
    fun success_checkOneNews() {
        // given
        val newsData = RedditNewsDataResponse(
                "author",
                "title",
                10,
                Date().time,
                "thumbnail",
                "url"
        )
        val newsResponse = RedditChildrenResponse(newsData)
        val redditNewsResponse = RedditNewsResponse(RedditDataResponse(listOf(newsResponse), null, null))
        val response = Response.success(redditNewsResponse)

        `when`(callMock.execute()).thenReturn(response)

        // when
        val newsManager = NewsManager(apiMock)
        newsManager.getNews("").subscribe(testSub)

        // then
        testSub.assertNoErrors()
        testSub.assertValueCount(1)
        testSub.assertComplete()

        assert(testSub.values()[0].news[0].author == newsData.author)
        assert(testSub.values()[0].news[0].title == newsData.title)
    }

    @Test
    fun error() {
        // given
        val responseError = Response.error<RedditNewsResponse>(
                500,
                ResponseBody.create(MediaType.parse("application/json"), "")
        )

        `when`(callMock.execute()).thenReturn(responseError)

        // when
        val newsManager = NewsManager(apiMock)
        newsManager.getNews("").subscribe(testSub)

        // then
        assert(testSub.errors().size == 1)
    }
}

inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)