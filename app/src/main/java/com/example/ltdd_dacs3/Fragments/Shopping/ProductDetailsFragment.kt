package com.example.ltdd_dacs3.Fragments.Shopping

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ltdd_dacs3.Adapters.RatiosAdapter
import com.example.ltdd_dacs3.Adapters.ViewPager2Images
import com.example.ltdd_dacs3.Data.CartProduct
import com.example.ltdd_dacs3.Helper.getProductPrice
import com.example.ltdd_dacs3.R
import com.example.ltdd_dacs3.Util.Resource
import com.example.ltdd_dacs3.Util.hideBottomNavigationView
import com.example.ltdd_dacs3.ViewModel.DetailsViewModel
import com.example.ltdd_dacs3.databinding.FragmentProductDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {
    private val args by navArgs<ProductDetailsFragmentArgs>()
    private lateinit var binding: FragmentProductDetailsBinding
    private val viewPagerAdapter by lazy { ViewPager2Images() }
    private val sizesAdapter by lazy { RatiosAdapter() }
//    private val colorsAdapter by lazy { ColorsAdapter() }
    private var selectedColor: Int? = null
    private var selectedSize: String? = null
    private val viewModel by viewModels<DetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        hideBottomNavigationView()
        binding = FragmentProductDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = args.product

        setupSizesRv()
//        setupColorsRv()
        setupViewpager()

        binding.imageClose.setOnClickListener {
            findNavController().navigateUp()
        }

        sizesAdapter.onItemClick = {
            selectedSize = it
        }

//        colorsAdapter.onItemClick = {
//            selectedColor = it
//        }

        binding.buttonAddToCart.setOnClickListener {
            viewModel.addUpdateProductInCart(CartProduct(product, 1, selectedColor, selectedSize))
        }

        lifecycleScope.launchWhenStarted {
            viewModel.addToCart.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        binding.buttonAddToCart.startAnimation()
                    }

                    is Resource.Success -> {
                        binding.buttonAddToCart.revertAnimation()
                        binding.buttonAddToCart.setBackgroundColor(resources.getColor(R.color.blue))
                    }

                    is Resource.Error -> {
                        binding.buttonAddToCart.stopAnimation()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

        binding.apply {
            tvProductName.text = product.name
            val priceAfterOffer = product.offerPercentage.getProductPrice(product.price)
            tvProductNewPrice.text = "$ ${String.format("%.2f", priceAfterOffer)}"
            if (product.offerPercentage == null) {
                tvProductNewPrice.visibility = View.INVISIBLE
            }else{
                tvProductPrice.paintFlags = tvProductNewPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
            tvProductPrice.text = "$ ${product.price}"
            tvProductDescription.text = product.description
            tvWarehouse.text = product.warehouse.toString()
            txtRatingDetail.text = product.rating.toString()

//            if (product.colors.isNullOrEmpty())
//                tvProductColors.visibility = View.INVISIBLE
            if (product.ratios.isNullOrEmpty())
                txtRatioDetail.visibility = View.INVISIBLE
        }

        viewPagerAdapter.differ.submitList(product.images)
//        product.colors?.let { colorsAdapter.differ.submitList(it) }
        product.ratios?.let { sizesAdapter.differ.submitList(it) }
    }

    private fun setupViewpager() {
        binding.apply {
            viewPagerProductImages.adapter = viewPagerAdapter
        }
    }

//    private fun setupColorsRv() {
//        binding.rvColors.apply {
//            adapter = colorsAdapter
//            layoutManager =
//                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        }
//    }

    private fun setupSizesRv() {
        binding.rvSizes.apply {
            adapter = sizesAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }
}