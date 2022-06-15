package com.riseup.countries.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.riseup.countries.Country
import com.riseup.countries.R
import com.riseup.countries.util.getProgressDrawable
import com.riseup.countries.util.loadImage
import kotlinx.android.synthetic.main.item_country.view.*

class CountryListAdapter(var countries: ArrayList<Country>) :
    RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    fun updateCountries(newCountry: List<Country>) {
        countries.clear()
        countries.addAll(newCountry)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CountryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
    )

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    override fun getItemCount() = countries.size

    class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val countryName = view.name
        private val imageView = view.imageview
        private val countryCapital = view.capital
        private val progressDrawable = getProgressDrawable(view.context)
        fun bind(country: Country) {
            countryName.text = country.countryName
            countryCapital.text = country.capital
            imageView.loadImage(country.flag, progressDrawable)
        }
    }
}