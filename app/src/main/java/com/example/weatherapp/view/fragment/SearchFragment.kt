package com.example.weatherapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.repo.remote.response.City
import com.example.weatherapp.databinding.FragmentSearchBinding
import com.example.weatherapp.view.adapter.CityResponseAdapter
import com.example.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding get() = _binding!!

    private val weatherViewModel: WeatherViewModel by activityViewModels()
    private val adapter: CityResponseAdapter by lazy {
        CityResponseAdapter(this::onCityClick)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            citySearchEditText.editText?.setOnEditorActionListener { textView, imeAction, _ ->
                if (imeAction == EditorInfo.IME_ACTION_DONE) {
                    weatherViewModel.fetchCity(textView.text.toString())
                }
                return@setOnEditorActionListener true
            }
            closeImageView.setOnClickListener {
                findNavController().navigateUp()
            }

            cityRecyclerView.apply {
                adapter = this@SearchFragment.adapter
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
            }
            weatherViewModel.cityState.observe(viewLifecycleOwner) { cityState ->
                if (cityState == null) return@observe
                progressBar.isVisible = cityState.isLoading
                citySearchEditText.isEnabled = !cityState.isLoading
                citySearchEditText.isErrorEnabled = cityState.error.isEmpty()
                citySearchEditText.apply {
                    isEnabled = !cityState.isLoading
                    isErrorEnabled = cityState.error.isEmpty()
                    error = cityState.error
                }
                adapter.submitList(cityState.cities)
            }
        }
    }

    private fun onCityClick(city: City) {
        weatherViewModel.emptyCityResults()
        weatherViewModel.fetchWeather(city.geonameid, city.name)
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}