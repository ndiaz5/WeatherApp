package com.example.weatherapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.repo.remote.response.City
import com.example.weatherapp.databinding.ListItemCityBinding

class CityResponseAdapter(
    private val onClick: (city: City) -> Unit
) : RecyclerView.Adapter<CityResponseAdapter.CityResultViewHolder>() {

    private var cityList = emptyList<City>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityResultViewHolder {
        return CityResultViewHolder(
            ListItemCityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        ).also { viewHolder ->
            viewHolder.itemView.setOnClickListener {
                onClick.invoke(cityList[viewHolder.adapterPosition])
            }
        }
    }

    override fun onBindViewHolder(holder: CityResultViewHolder, position: Int) {
        holder.bind(cityList[position])
    }

    override fun getItemCount() = cityList.size

    fun submitList(cityList: List<City>) {
        this.cityList = cityList
        notifyDataSetChanged()
    }

    class CityResultViewHolder(private val binding: ListItemCityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(city: City)  {
            binding.cityNameTextView.text = city.name
        }
    }
}