package com.github.mobileuptestex.ui.main.recycler_view

import android.annotation.SuppressLint
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.mobileuptestex.R
import com.github.mobileuptestex.databinding.ItemFragmentMainBinding
import com.github.mobileuptestex.network.dto.crypto_list.CryptoListResponseItem
import kotlin.math.roundToInt

class CryptoViewHolder(val binding: ItemFragmentMainBinding) :
  RecyclerView.ViewHolder(binding.root) {

  @SuppressLint("SetTextI18n")
  fun bind(
    binding: ItemFragmentMainBinding,
    currItem: CryptoListResponseItem,
    currentCurrency: String
  ) {
    binding.apply {
      val currentPrice = (currItem.current_price * 100.0).roundToInt() / 100.0
      val pricePercentage = (currItem.price_change_percentage_24h * 100.0).roundToInt() / 100.0

      Glide.with(logoIV).load(currItem.image).into(logoIV)
      cryptoNameTV.text = currItem.name
      cryptoAbbreviationTV.text = currItem.symbol.uppercase()
      cryptoPriceTV.text = if (currentCurrency == "usd") "$ $currentPrice" else "€ $currentPrice"

      if (pricePercentage.toString().contains("-")) {
        pricePercentTV.text = "$pricePercentage%"
        pricePercentTV.setTextColor(getColor(pricePercentTV.context, R.color.crypto_percent_neg))
      } else {
        pricePercentTV.text = "+$$pricePercentage%"
        pricePercentTV.setTextColor(getColor(pricePercentTV.context, R.color.crypto_percent_pos))
      }
    }
  }

}