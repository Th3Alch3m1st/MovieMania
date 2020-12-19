package com.coderbyte.moviemania.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.arena.banglalinkmela.app.RxImmediateSchedulerRule
import com.coderbyte.moviemania.argumentCaptor
import com.coderbyte.moviemania.capture
import com.coderbyte.moviemania.data.model.movie.PopularMoviesResponse
import com.coderbyte.moviemania.data.repository.MovieInfoRepository
import com.coderbyte.moviemania.data.session.Session
import com.coderbyte.moviemania.ui.home.HomeViewModel
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created By Rafiqul Hasan on 1/9/20
 * Brain Station 23
 */

@RunWith(MockitoJUnitRunner::class)
class HomeViewViewModelTest {
    companion object {
        @ClassRule
        @JvmField
        val rxImmediateSchedulerRule = RxImmediateSchedulerRule()
    }

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: MovieInfoRepository

    @Mock
    lateinit var session: Session

    lateinit var homeViewModel: HomeViewModel
    private val popularMoviesResponse by lazy { LoggingObserver<PopularMoviesResponse>() }
    private val mockStateObserver by lazy { mock(Observer::class.java) as Observer<Boolean> }


    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        homeViewModel =
            HomeViewModel(repository, session)
    }

    @Test
    fun popularMoviewResponseVerifiedSuccess() {
        Success()

        val mockPhoneValidationStatus = LoggingObserver<Boolean>()

        homeViewModel.hideProgressbar.observeForever(mockStateObserver)

       homeViewModel.getPopularTvSeries(1)

        //argument testing
        val ac = argumentCaptor<Int>()
        verify(repository).getPopularMovies(capture(ac))
        assertThat(ac.value).isEqualTo("1")

        //loader state checking in order
        val inOrder = inOrder(mockStateObserver)
        inOrder.verify(mockStateObserver).onChanged(true)
        inOrder.verify(mockStateObserver).onChanged(false)
        inOrder.verifyNoMoreInteractions()

        assertThat(mockPhoneValidationStatus.value).isTrue()
    }

    private fun Success() {
        `when`(repository.getPopularMovies(com.coderbyte.moviemania.any()))
            .thenReturn(Single.create { emitter ->
                emitter.onSuccess(getPopularMovieData())
            })
    }



    private class LoggingObserver<T> : Observer<T> {
        var value: T? = null
        override fun onChanged(t: T?) {
            this.value = t
        }
    }


    val gson = Gson()
    fun getPopularMovieData(): PopularMoviesResponse {
        val string = "{\n" +
                "    \"page\": 2,\n" +
                "    \"results\": [\n" +
                "        {\n" +
                "            \"adult\": false,\n" +
                "            \"backdrop_path\": \"/udOSxhgB4AZRmlALkpSLbWrE88G.jpg\",\n" +
                "            \"genre_ids\": [\n" +
                "                35\n" +
                "            ],\n" +
                "            \"id\": 771437,\n" +
                "            \"original_language\": \"pt\",\n" +
                "            \"original_title\": \"Flops: Agentes Nada Secretos\",\n" +
                "            \"overview\": \"\",\n" +
                "            \"popularity\": 1.689,\n" +
                "            \"poster_path\": \"/8Px2jiJ9SyW54fF7VIbNrtfovA3.jpg\",\n" +
                "            \"release_date\": \"2020-12-04\",\n" +
                "            \"title\": \"Flops: Agentes Nada Secretos\",\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 10,\n" +
                "            \"vote_count\": 2\n" +
                "        },\n" +
                "        {\n" +
                "            \"adult\": false,\n" +
                "            \"backdrop_path\": \"/oKCWjFmi5qayUF66i19qL6AgV8p.jpg\",\n" +
                "            \"genre_ids\": [\n" +
                "                35\n" +
                "            ],\n" +
                "            \"id\": 771364,\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Speech & Debate: The Aftermath\",\n" +
                "            \"overview\": \"Competition in the time of quarantine.\",\n" +
                "            \"popularity\": 1.371,\n" +
                "            \"poster_path\": \"/qLTw4QgWMDfS5ytzFrsduVkCPdj.jpg\",\n" +
                "            \"release_date\": \"2020-08-01\",\n" +
                "            \"title\": \"Speech & Debate: The Aftermath\",\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 10,\n" +
                "            \"vote_count\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"adult\": false,\n" +
                "            \"backdrop_path\": null,\n" +
                "            \"genre_ids\": [\n" +
                "                99\n" +
                "            ],\n" +
                "            \"id\": 771199,\n" +
                "            \"original_language\": \"hr\",\n" +
                "            \"original_title\": \"Kumek\",\n" +
                "            \"overview\": \"\\\"To make the film Goddaddy was a great challenge, because it covers almost forty years of Milan Bandić's activity, a seemingly hyperactive and Trump-like character of the Croatian political scene. Bandić has been elected six times as the mayor of Zagreb, which is not only the capital city of all Croats, but also the capital city of all Croatian criminals. Three times Milan Bandić has rejected to give us an interview for the film, so we made an audition to find him a double.\\\" - Dario Juričan\",\n" +
                "            \"popularity\": 1.588,\n" +
                "            \"poster_path\": \"/itSwaWNnbE3SIClcF49ALhTeLI5.jpg\",\n" +
                "            \"release_date\": \"2020-11-22\",\n" +
                "            \"title\": \"Goddaddy\",\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 10,\n" +
                "            \"vote_count\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"adult\": false,\n" +
                "            \"backdrop_path\": null,\n" +
                "            \"genre_ids\": [],\n" +
                "            \"id\": 771194,\n" +
                "            \"original_language\": \"pt\",\n" +
                "            \"original_title\": \"Pitty - MATRIZ Ao Vivo\",\n" +
                "            \"overview\": \"This DVD presents the record of the \\\"Matriz\\\" tour, with the historic show at Concha Acústica, in Salvador, where the artist received on stage the musicians who participated in the album of the same name, all from Bahia: Lazzo Matumbi, Larissa Luz, Russo Passapusso and Robertinho Barreto (BaianaSystem). Accompanied by her band, Pitty played all the songs of the album and several successes of her career.\",\n" +
                "            \"popularity\": 2.037,\n" +
                "            \"poster_path\": \"/ihqWNVXBYznjccZYPwQuQfFA7H4.jpg\",\n" +
                "            \"release_date\": \"2020-12-05\",\n" +
                "            \"title\": \"MATRIZ Ao Vivo\",\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 10,\n" +
                "            \"vote_count\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"adult\": false,\n" +
                "            \"backdrop_path\": null,\n" +
                "            \"genre_ids\": [\n" +
                "                27,\n" +
                "                16\n" +
                "            ],\n" +
                "            \"id\": 770610,\n" +
                "            \"original_language\": \"pt\",\n" +
                "            \"original_title\": \"Waiting\",\n" +
                "            \"overview\": \"A girl waits for someone, but something is waiting for her too...\",\n" +
                "            \"popularity\": 1.271,\n" +
                "            \"poster_path\": \"/7U1ND5Z7kq0HG6z17FFBj7kJE8l.jpg\",\n" +
                "            \"release_date\": \"2020-11-30\",\n" +
                "            \"title\": \"Waiting\",\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 10,\n" +
                "            \"vote_count\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"adult\": false,\n" +
                "            \"backdrop_path\": \"/zUxJ7RC8PvHa7qIpYWU4S0NAAw1.jpg\",\n" +
                "            \"genre_ids\": [\n" +
                "                53\n" +
                "            ],\n" +
                "            \"id\": 770191,\n" +
                "            \"original_language\": \"fi\",\n" +
                "            \"original_title\": \"The Evil One\",\n" +
                "            \"overview\": \"Three identical brothers are struggling about what to do with the evil fourth brother.\",\n" +
                "            \"popularity\": 0.6,\n" +
                "            \"poster_path\": \"/hB02z6JTLScKmZxFgoGreGzg8Hk.jpg\",\n" +
                "            \"release_date\": \"2020-10-15\",\n" +
                "            \"title\": \"The Evil One\",\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 10,\n" +
                "            \"vote_count\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"adult\": false,\n" +
                "            \"backdrop_path\": \"/9tdA1UB1RWMc1nRYreODI5Xey44.jpg\",\n" +
                "            \"genre_ids\": [\n" +
                "                10402\n" +
                "            ],\n" +
                "            \"id\": 769737,\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"A Holly Dolly Christmas\",\n" +
                "            \"overview\": \"Parton will perform from an intimate, candlelit set while sharing personal Christmas stories and faith-based recollections of the season, interspersed with songs from the album of the same name.\",\n" +
                "            \"popularity\": 5.149,\n" +
                "            \"poster_path\": \"/dBfvENzJed9NcL1JJ8VttRGyf5w.jpg\",\n" +
                "            \"release_date\": \"2020-12-06\",\n" +
                "            \"title\": \"A Holly Dolly Christmas\",\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 10,\n" +
                "            \"vote_count\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"adult\": false,\n" +
                "            \"backdrop_path\": null,\n" +
                "            \"genre_ids\": [\n" +
                "                10749,\n" +
                "                18,\n" +
                "                14\n" +
                "            ],\n" +
                "            \"id\": 769663,\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"The Rock Short\",\n" +
                "            \"overview\": \"A lonely teenage boy falls for the perfect girl only to discover she isn't what she seems\",\n" +
                "            \"popularity\": 1.639,\n" +
                "            \"poster_path\": \"/9CsaBaEA8Ohgp7vES5IjouWfjKl.jpg\",\n" +
                "            \"release_date\": \"2020-11-28\",\n" +
                "            \"title\": \"The Rock Short\",\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 10,\n" +
                "            \"vote_count\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"adult\": false,\n" +
                "            \"backdrop_path\": \"/malHT2nYzG5Iy3km0GtgrcKGXnF.jpg\",\n" +
                "            \"genre_ids\": [\n" +
                "                16\n" +
                "            ],\n" +
                "            \"id\": 769366,\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Sometimes I think about things, and sometimes I get sad.\",\n" +
                "            \"overview\": \"The project explores the inner struggles of the artist, the fears, anxieties and thought patterns that, to some extent, drive their existence. The artist's voice, sporadically reciting diary-like entries, guides you through a world built as a visual representation of their emotions. The resulting experience is surrealist in nature, but also deeply intimate.\",\n" +
                "            \"popularity\": 1.876,\n" +
                "            \"poster_path\": \"/arFbIJFF3STUazIKEtk1UbAF5N9.jpg\",\n" +
                "            \"release_date\": \"2020-11-01\",\n" +
                "            \"title\": \"Sometimes I think about things, and sometimes I get sad.\",\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 10,\n" +
                "            \"vote_count\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"adult\": false,\n" +
                "            \"backdrop_path\": null,\n" +
                "            \"genre_ids\": [\n" +
                "                99\n" +
                "            ],\n" +
                "            \"id\": 768989,\n" +
                "            \"original_language\": \"pt\",\n" +
                "            \"original_title\": \"Transição\",\n" +
                "            \"overview\": \"\",\n" +
                "            \"popularity\": 0.65,\n" +
                "            \"poster_path\": \"/C28Y0Xijep4Y43owU0KdYYLzKG.jpg\",\n" +
                "            \"release_date\": \"2020-11-11\",\n" +
                "            \"title\": \"Transição\",\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 10,\n" +
                "            \"vote_count\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"adult\": false,\n" +
                "            \"backdrop_path\": null,\n" +
                "            \"genre_ids\": [\n" +
                "                18\n" +
                "            ],\n" +
                "            \"id\": 768970,\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Weightless Marigold\",\n" +
                "            \"overview\": \"Marigold is an aspiring dancer preparing for her first audition for a professional contemporary dance company. She soon begins to doubt her abilities and question whether or not she's good enough, which send her into a dangerous downward spiral.\",\n" +
                "            \"popularity\": 0.844,\n" +
                "            \"poster_path\": \"/a98OOEDr8k3UEOt7MNHyazLXB8.jpg\",\n" +
                "            \"release_date\": \"2020-11-19\",\n" +
                "            \"title\": \"Weightless Marigold\",\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 10,\n" +
                "            \"vote_count\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"adult\": false,\n" +
                "            \"backdrop_path\": null,\n" +
                "            \"genre_ids\": [\n" +
                "                80,\n" +
                "                18\n" +
                "            ],\n" +
                "            \"id\": 768387,\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Night of Thieves\",\n" +
                "            \"overview\": \"After getting robbed, a father desperately tries to reclaim his most prized possession.\",\n" +
                "            \"popularity\": 1.099,\n" +
                "            \"poster_path\": \"/nYSDGLOaHMhkZtDtC09d1plwyoN.jpg\",\n" +
                "            \"release_date\": \"2020-11-19\",\n" +
                "            \"title\": \"Night of Thieves\",\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 10,\n" +
                "            \"vote_count\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"adult\": false,\n" +
                "            \"backdrop_path\": \"/v26lB6Y3GqTamrEXqnPPPcp0BiG.jpg\",\n" +
                "            \"genre_ids\": [\n" +
                "                35,\n" +
                "                18\n" +
                "            ],\n" +
                "            \"id\": 768166,\n" +
                "            \"original_language\": \"pt\",\n" +
                "            \"original_title\": \"Carta à A.H.\",\n" +
                "            \"overview\": \"A young screenwriter does not have more creativity to write movies. Everything changes when he meets Aline, an extroverted woman, that helps him to find inspiration for a story.\",\n" +
                "            \"popularity\": 0.835,\n" +
                "            \"poster_path\": \"/BjdOwzVNOd0Q9G7kRi0ajnM0SP.jpg\",\n" +
                "            \"release_date\": \"2020-11-20\",\n" +
                "            \"title\": \"A Letter to A.H.\",\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 10,\n" +
                "            \"vote_count\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"adult\": false,\n" +
                "            \"backdrop_path\": null,\n" +
                "            \"genre_ids\": [\n" +
                "                35\n" +
                "            ],\n" +
                "            \"id\": 768136,\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Count Arthur Strong Is There Anybody Out There?\",\n" +
                "            \"overview\": \"As well as being the all round entertainer we all know and love from the telly, Count Arthur Strong is also a lifelong fan of astronomy, since having been given a microscope, or whatever it is they use, for Christmas when he was a small precocious baby. In fact it's said the first word he spoke was 'Uranus'. In this, his brand new show, he seamlessly combines the very best of showbiz entertainment you'll currently find, in the world, possibly, as he wrestles with some of the big questions that all round entertainers shy away from.  Are we alone in the universe?  Is there life on Mars bars?  2lbs of potatoes.  Packet of ginger nuts.  Don't lose this shopping list.\",\n" +
                "            \"popularity\": 0.982,\n" +
                "            \"poster_path\": \"/hfoB7ur4tFhGGRqRJ0KvLtypcJQ.jpg\",\n" +
                "            \"release_date\": \"2020-11-13\",\n" +
                "            \"title\": \"Count Arthur Strong Is There Anybody Out There?\",\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 10,\n" +
                "            \"vote_count\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"adult\": false,\n" +
                "            \"backdrop_path\": \"/yMIBQ0OBg4MLWXWLfxEok08PhrH.jpg\",\n" +
                "            \"genre_ids\": [\n" +
                "                99,\n" +
                "                12\n" +
                "            ],\n" +
                "            \"id\": 767786,\n" +
                "            \"original_language\": \"de\",\n" +
                "            \"original_title\": \"Setgeflüster\",\n" +
                "            \"overview\": \"Take an exclusive inside look into the hearts and minds of the creators of \\\"Stay Around a Little Longer\\\" with this  50-minute long Making-of. How could you possibly say no to that?\",\n" +
                "            \"popularity\": 0.952,\n" +
                "            \"poster_path\": \"/5iVzoVrwIp48RcGGh0GllTiWSoM.jpg\",\n" +
                "            \"release_date\": \"2020-11-24\",\n" +
                "            \"title\": \"Setgeflüster\",\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 10,\n" +
                "            \"vote_count\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"adult\": false,\n" +
                "            \"backdrop_path\": null,\n" +
                "            \"genre_ids\": [\n" +
                "                99\n" +
                "            ],\n" +
                "            \"id\": 767734,\n" +
                "            \"original_language\": \"pt\",\n" +
                "            \"original_title\": \"Same/Different/Both/Neither\",\n" +
                "            \"overview\": \"In a period of isolation, far away from each other, 2 friends reconnect through video-letters, inspired by the poetic gaze of women experimental filmmakers: Marie Menken, Joyce Wieland, Gunvor Nelson, Yvonne Rainer. Fernanda is a Brazilian living in São Paulo, Adriana is a Mexican-Brazilian living in Los Angeles. They both share their inspiration while capturing the reality of these times.\",\n" +
                "            \"popularity\": 0.867,\n" +
                "            \"poster_path\": \"/mDYsjAypupHAKvLFUaMka8qTo1J.jpg\",\n" +
                "            \"release_date\": \"2020-11-20\",\n" +
                "            \"title\": \"Same/Different/Both/Neither\",\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 10,\n" +
                "            \"vote_count\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"adult\": false,\n" +
                "            \"backdrop_path\": null,\n" +
                "            \"genre_ids\": [\n" +
                "                27,\n" +
                "                53,\n" +
                "                18\n" +
                "            ],\n" +
                "            \"id\": 767711,\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"The Crumbling\",\n" +
                "            \"overview\": \"An addict blinded by love has one night to obtain the object of their affection.\",\n" +
                "            \"popularity\": 0.6,\n" +
                "            \"poster_path\": \"/r69w5ra5IrKXPIcONJecaAntYGh.jpg\",\n" +
                "            \"release_date\": \"2020-10-01\",\n" +
                "            \"title\": \"The Crumbling\",\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 10,\n" +
                "            \"vote_count\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"adult\": false,\n" +
                "            \"backdrop_path\": null,\n" +
                "            \"genre_ids\": [\n" +
                "                18,\n" +
                "                53\n" +
                "            ],\n" +
                "            \"id\": 767532,\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"Scales\",\n" +
                "            \"overview\": \"Scales is a tense, claustrophobic drama that spends one evening with four characters, who are trapped in a combustable boiling pot of an apartment.  American boxer, Darnell, his PR manager and girlfriend, Maria, troubled entrepreneur Adam and drug dealer Keith are forced to question their relationships and loyalties, as sinister secrets come to the fore, threatening dangerous consequences.  Unsettling and unexpected, this dark tale of lies and manipulation will lead audiences on a voyeuristic and ill-fated journey that, like the four characters, their sensibility may not want to be a part of, but their curiosity certainly will, begging the question: 'How do you weigh trust?'\",\n" +
                "            \"popularity\": 1.111,\n" +
                "            \"poster_path\": \"/tcj4KCDKo20q6O9u2XgUG7qWuKa.jpg\",\n" +
                "            \"release_date\": \"2020-11-27\",\n" +
                "            \"title\": \"Scales\",\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 10,\n" +
                "            \"vote_count\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"adult\": false,\n" +
                "            \"backdrop_path\": \"/3qWthtS6yGJHp4qDNthqu7DboCE.jpg\",\n" +
                "            \"genre_ids\": [\n" +
                "                18,\n" +
                "                10751\n" +
                "            ],\n" +
                "            \"id\": 767494,\n" +
                "            \"original_language\": \"de\",\n" +
                "            \"original_title\": \"Verminderte Sicht\",\n" +
                "            \"overview\": \"\",\n" +
                "            \"popularity\": 0.6,\n" +
                "            \"poster_path\": \"/1fYxQEUI58yYSVfi5dJLnk3vDRm.jpg\",\n" +
                "            \"release_date\": \"2020-03-21\",\n" +
                "            \"title\": \"Verminderte Sicht\",\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 10,\n" +
                "            \"vote_count\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"adult\": false,\n" +
                "            \"backdrop_path\": \"/bCca0EZnC35MBSKywRQv6QpZ1Ri.jpg\",\n" +
                "            \"genre_ids\": [\n" +
                "                99\n" +
                "            ],\n" +
                "            \"id\": 767389,\n" +
                "            \"original_language\": \"en\",\n" +
                "            \"original_title\": \"You Don't Know Nicotine\",\n" +
                "            \"overview\": \"Amidst radical changes in nicotine use globally, one filmmaker's journey through the confusion & fear leads to a startling discovery about Earth's most hated stimulant. Society may be changed forever.\",\n" +
                "            \"popularity\": 3.756,\n" +
                "            \"poster_path\": \"/gELtMNd1jjjOcLBJcOJvFkQnjgn.jpg\",\n" +
                "            \"release_date\": \"2020-11-20\",\n" +
                "            \"title\": \"You Don't Know Nicotine\",\n" +
                "            \"video\": false,\n" +
                "            \"vote_average\": 10,\n" +
                "            \"vote_count\": 2\n" +
                "        }\n" +
                "    ],\n" +
                "    \"total_pages\": 500,\n" +
                "    \"total_results\": 10000\n" +
                "}"
        return gson.fromJson(string, PopularMoviesResponse::class.java)
    }

}