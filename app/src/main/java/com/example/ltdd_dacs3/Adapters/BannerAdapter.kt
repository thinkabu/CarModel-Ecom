package com.example.ltdd_dacs3.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.request.RequestOptions
import com.example.ltdd_dacs3.Data.Product
import com.example.ltdd_dacs3.R

class BannerAdapter(
    private var sliderItems:List<Product>,
    private val viewPager2: ViewPager2
): RecyclerView.Adapter<BannerAdapter.SliderViewholder>() {
    private lateinit var context: Context
    private val runnable = Runnable {
        sliderItems = sliderItems
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SliderViewholder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.slider_item_container, parent, false)

        return SliderViewholder(view)
    }

    override fun onBindViewHolder(holder: SliderViewholder, position: Int) {
        holder.setImage(sliderItems[position], context)
        if (position == sliderItems.lastIndex - 1){
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int = sliderItems.size

    class SliderViewholder(itemView: View):RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageSlide)

        fun setImage(sliderItems: Product, context: Context){
            val requestOptions = RequestOptions().transform(CenterInside())

            Glide.with(context).load(sliderItems.images).apply(requestOptions).into(imageView)

        }
    }
}