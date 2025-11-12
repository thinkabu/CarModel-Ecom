package com.example.ltdd_dacs3.Fragments.Shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.ltdd_dacs3.Adapters.HomeViewpagerAdapter
import com.example.ltdd_dacs3.Fragments.Categories.BMWFragment
import com.example.ltdd_dacs3.Fragments.Categories.FerrariFragment
import com.example.ltdd_dacs3.Fragments.Categories.FurnitureFragment
import com.example.ltdd_dacs3.Fragments.Categories.HondaFragment
import com.example.ltdd_dacs3.Fragments.Categories.LamborghiniFragment
import com.example.ltdd_dacs3.Fragments.Categories.MainCategoryFragment
import com.example.ltdd_dacs3.R
import com.example.ltdd_dacs3.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment: Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        // Xử lý WindowInsets để đảm bảo nội dung không bị che khuất
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBarsInsets.top, 0, systemBarsInsets.bottom)
            insets
        }

        val categoriesFragments = arrayListOf(
            MainCategoryFragment(),
            BMWFragment(),
            FerrariFragment(),
            HondaFragment(),
            LamborghiniFragment(),
            FurnitureFragment()
        )

        binding.viewpagerHome.isUserInputEnabled = false

        val viewPager2Adapter =
            HomeViewpagerAdapter(categoriesFragments, childFragmentManager, lifecycle)
        binding.viewpagerHome.adapter = viewPager2Adapter
        TabLayoutMediator(binding.tabLayout, binding.viewpagerHome) { tab, position ->
            when (position) {
                0 -> tab.text = "Main"
                1 -> tab.text = "BMW"
                2 -> tab.text = "Ferrari"
                3 -> tab.text = "Honda"
                4 -> tab.text = "Lamborghini"
                5 -> tab.text = "Furniture"
            }
        }.attach()
    }
}