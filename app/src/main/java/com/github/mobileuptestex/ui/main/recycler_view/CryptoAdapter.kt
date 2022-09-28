package com.github.mobileuptestex.ui.main.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.mobileuptestex.databinding.ItemFragmentMainBinding
import com.github.mobileuptestex.network.dto.crypto_list.CryptoListResponseItem
import com.github.mobileuptestex.ui.SaveArgsModel

class CryptoAdapter(private val param: OnItemClickListener) :
  RecyclerView.Adapter<CryptoViewHolder>() {

  private var currentCurrency: String = "usd"

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
    return CryptoViewHolder(
      ItemFragmentMainBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )
  }

  override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
    val currItem = differ.currentList[position]
    holder.bind(holder.binding, currItem, currentCurrency)

    holder.itemView.setOnClickListener {
      param.onItemClick(SaveArgsModel(id = currItem.id, name = currItem.name))
    }
  }

  override fun getItemCount() = differ.currentList.size

  private val diffCallback = object : DiffUtil.ItemCallback<CryptoListResponseItem>() {
    override fun areItemsTheSame(
      oldItem: CryptoListResponseItem,
      newItem: CryptoListResponseItem
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
      oldItem: CryptoListResponseItem,
      newItem: CryptoListResponseItem
    ): Boolean = oldItem == newItem
  }

  private val differ = AsyncListDiffer(this, diffCallback)

  fun submitList(list: List<CryptoListResponseItem>, currency: String) {
    currentCurrency = currency
    return differ.submitList(list)
  }

  interface OnItemClickListener {
    fun onItemClick(info: SaveArgsModel)
  }

}