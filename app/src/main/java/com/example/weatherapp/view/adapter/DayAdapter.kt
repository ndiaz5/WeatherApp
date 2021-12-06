package com.example.weatherapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.repo.remote.response.Day
import com.example.weatherapp.databinding.ListItemDayBinding

class DayAdapter(
    private val onClick: (position: Int) -> Unit,
) : RecyclerView.Adapter<DayAdapter.DayViewHolder>() {

    private var dayList = emptyList<Day>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        return DayViewHolder(
            ListItemDayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).also { viewHolder ->
            viewHolder.itemView.setOnClickListener {
                onClick.invoke(viewHolder.adapterPosition)
            }
        }
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(dayList[position])
    }

    override fun getItemCount() = dayList.size

    fun submitList(dayList: List<Day>) {
        this.dayList = dayList
        notifyDataSetChanged()
    }

    class DayViewHolder(private val binding: ListItemDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(day: Day) {
            with(binding) {
                dailyImageView.setImageDrawable(
                    AppCompatResources.getDrawable(
                        root.context,
                        day.getDrawableResource()
                    )
                )
                dailyTemperatureTextView.text = day.low.toString()
                dayTextView.text = root.context.getString(day.getDayStringResource())
            }
        }
    }
}