package com.example.weatherapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.repo.remote.response.WeatherResponse
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherBinding
import com.example.weatherapp.util.base.BaseFragment
import com.example.weatherapp.view.adapter.DayAdapter
import com.example.weatherapp.view.adapter.HeaderAdapter
import com.example.weatherapp.view.adapter.HourAdapter
import com.example.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val NUMBER_OF_DAYS = 7

@AndroidEntryPoint
class WeatherFragment : BaseFragment() {

    private var _binding: FragmentWeatherBinding? = null
    private val binding: FragmentWeatherBinding get() = _binding!!

    private val weatherViewModel: WeatherViewModel by activityViewModels()

    private val headerAdapter: HeaderAdapter by lazy {
        HeaderAdapter()
    }
    private val dayAdapter: DayAdapter by lazy {
        DayAdapter(this::onUserDaySelection)
    }
    private val hourAdapter: HourAdapter by lazy {
        HourAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            searchImageView.setOnClickListener {
                navigateToCitySearch()
            }

            val mainLayoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            headerRecyclerView.apply {
                adapter = headerAdapter
                layoutManager = mainLayoutManager
                PagerSnapHelper().attachToRecyclerView(this)
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    // When city is scrolled we want to update current city and weather.
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val index = mainLayoutManager.findFirstCompletelyVisibleItemPosition()
                        if (index >= 0)
                            this@WeatherFragment.onCityScroll(
                                headerAdapter.getCityAtPosition(index)
                            )
                    }
                })
            }
            hourlyRv.apply {
                adapter = hourAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
            dailyRecyclerView.apply {
                adapter = dayAdapter
                layoutManager = object : LinearLayoutManager(requireContext(), HORIZONTAL, false) {
                    override fun canScrollHorizontally(): Boolean {
                        return false
                    }

                    override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
                        lp?.width = width / NUMBER_OF_DAYS
                        return true
                    }
                }
            }
            weatherViewModel.weatherState.observe(viewLifecycleOwner) { weatherState ->
                progressBar.isVisible = weatherState.isLoading
                if (weatherState.isEmpty) navigateToCitySearch()
                if (weatherState.error.isNotBlank()) showToast(weatherState.error)
                hourAdapter.submitList(weatherState.hourly)
                dayAdapter.submitList(weatherState.daily)
                headerAdapter.submitList(weatherState.weatherResponses)
            }
        }
    }

    private fun navigateToCitySearch() {
        findNavController().navigate(R.id.action_weatherFragment_to_searchFragment)
    }

    private fun onCityScroll(weather: WeatherResponse) {
        weatherViewModel.onCityScroll(weather)
    }

    private fun onUserDaySelection(index: Int) {
        weatherViewModel.onSelectDay(index)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}