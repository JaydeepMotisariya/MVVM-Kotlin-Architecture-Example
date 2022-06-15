package com.riseup.countries.model

import com.riseup.countries.Country
import com.riseup.countries.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class CountiesService {

    @Inject
    lateinit var api : CountriesApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getCountries(): Single<List<Country>>{
        return  api.getCountries()
    }
}