package com.riseup.countries

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.riseup.countries.model.CountiesService
import com.riseup.countries.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.initMocks
import java.util.concurrent.TimeUnit

class ListViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var countiresService: CountiesService

    @InjectMocks
    var listViewModel = ListViewModel()

    private var testSingle: Single<List<Country>>? = null

    @Before
    fun setup() {
        initMocks(this)
    }

    @Test
    fun getCountriesSuccess() {
        val country = Country("countryName", "capital", "url")
        val coutriesList = arrayListOf(country)
        testSingle = Single.just(coutriesList)

       `when`(countiresService.getCountries()).thenReturn(testSingle)
        listViewModel.refresh()

        assertEquals(1,listViewModel.countries.value?.size)
        assertEquals(false,listViewModel.countryLoadError.value)
        assertEquals(false,listViewModel.loading.value)

    }
    @Test
    fun getCountriesFail() {
        testSingle = Single.error(Throwable())

      `when`(countiresService.getCountries()).thenReturn(testSingle)

        listViewModel.refresh()

        assertEquals(true,listViewModel.countryLoadError.value)
        assertEquals(false,listViewModel.loading.value)

    }

    @Before
    fun seUpRxSchedulers() {
        val immediate = object : Scheduler() {
            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker({ it.run() })
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }


}