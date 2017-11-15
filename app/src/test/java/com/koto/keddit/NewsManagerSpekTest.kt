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
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import retrofit2.Call
import retrofit2.Response
import java.util.*

@RunWith(JUnitPlatform::class)
class NewsManagerSpekTest : Spek({
    given("a NewsManager") {
        var testSub = TestObserver<RedditNews>()
        var apiMock = mock<NewsAPI>()
        var callMock = mock<Call<RedditNewsResponse>>()

        beforeEachTest {
            testSub = TestObserver<RedditNews>()
            callMock = mock()
            apiMock = mock<NewsAPI>()
            `when`(apiMock.getNews(Mockito.anyString(), Mockito.anyString())).thenReturn(callMock)
        }

        on("service returns something") {
            val redditNewsResponse = RedditNewsResponse(RedditDataResponse(listOf(), null, null))
            val response = Response.success(redditNewsResponse)

            `when`(callMock.execute()).thenReturn(response)

            val newsManager = NewsManager(apiMock)
            newsManager.getNews("").subscribe(testSub)

            it("should receive something and no errors") {
                testSub.assertNoErrors()
                testSub.assertValueCount(1)
                testSub.assertComplete()
            }
        }

        on("service returns just one news") {
            val newsData = RedditNewsDataResponse(
                    "author",
                    "title",
                    10,
                    Date().time,
                    "thumbnail",
                    "url"
            )

            val newsReponse = RedditChildrenResponse(newsData)
            val redditNewsResponse = RedditNewsResponse(RedditDataResponse(listOf(newsReponse), null, null))
            val response = Response.success(redditNewsResponse)

            `when`(callMock.execute()).thenReturn(response)

            val newsManager = NewsManager(apiMock)
            newsManager.getNews("").subscribe(testSub)

            it("should process only one news successfully") {
                testSub.assertNoErrors()
                testSub.assertValueCount(1)
                testSub.assertComplete()

                assert(testSub.values()[0].news[0].author == newsData.author)
                assert(testSub.values()[0].news[0].title == newsData.title)
            }
        }

        on("service returns a 500 error") {
            val responseError = Response.error<RedditNewsResponse>(
                    500,
                    ResponseBody.create(MediaType.parse("application/json"), "")
            )

            `when`(callMock.execute()).thenReturn(responseError)

            val newsManager = NewsManager(apiMock)
            newsManager.getNews("").subscribe(testSub)

            it("should receive an onError message") {
                assert(testSub.errors().size == 1)
            }
        }
    }
})