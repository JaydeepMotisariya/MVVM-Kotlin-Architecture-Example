package com.riseup.countries.di

import com.riseup.countries.model.CountiesService
import com.riseup.countries.viewmodel.ListViewModel
import dagger.Component


@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: CountiesService)
    fun inject(viewModel: ListViewModel)
}