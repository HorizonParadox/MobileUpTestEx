package com.github.mobileuptestex.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.mobileuptestex.R
import com.github.mobileuptestex.databinding.FragmentMainBinding
import com.github.mobileuptestex.databinding.ItemFragmentMainBinding
import com.github.mobileuptestex.network.dto.crypto_list.CryptoListResponseItem
import java.lang.Math.round
import kotlin.math.floor
import kotlin.math.roundToInt

class CryptoAdapter : RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {


  inner class CryptoViewHolder(val binding: ItemFragmentMainBinding) :
    RecyclerView.ViewHolder(binding.root)


  private val diffCallback = object : DiffUtil.ItemCallback<CryptoListResponseItem>() {
    override fun areItemsTheSame(
      oldItem: CryptoListResponseItem,
      newItem: CryptoListResponseItem
    ): Boolean {
      return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
      oldItem: CryptoListResponseItem,
      newItem: CryptoListResponseItem
    ): Boolean {
      return oldItem == newItem
    }
  }

  private val differ = AsyncListDiffer(this, diffCallback)

  fun submitList(list: List<CryptoListResponseItem>) = differ.submitList(list)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
    return CryptoViewHolder(
      ItemFragmentMainBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )
  }

  @SuppressLint("SetTextI18n")
  override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
    val currItem = differ.currentList[position]

    holder.binding.apply {
      with(currItem) {
        cryptoNameTV.text = name
        cryptoAbbreviationTV.text = symbol
        cryptoPriceTV.text = "$ ${(current_price * 100.0).roundToInt() / 100.0}"
        Log.d("PRICE", current_price.toString())

        if (price_change_percentage_24h.toString().contains("-")) {
          pricePercentTV.text = "${(price_change_percentage_24h * 100.0).roundToInt() / 100.0}%"
          pricePercentTV.setTextColor(pricePercentTV.resources.getColor(R.color.crypto_percent_neg))
        }
        else {
          pricePercentTV.text = "+${(price_change_percentage_24h * 100.0).roundToInt() / 100.0}%"
          pricePercentTV.setTextColor(pricePercentTV.resources.getColor(R.color.crypto_percent_pos))
        }
        Log.d("PERCENT", price_change_percentage_24h.toString())
        Glide.with(logoIV).load(image).into(logoIV)
      }

    }
  }

  override fun getItemCount() = differ.currentList.size
}