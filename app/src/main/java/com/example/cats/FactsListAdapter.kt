package com.example.cats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cats.databinding.FactsListItemBinding

class FactsListAdapter(): RecyclerView.Adapter<FactsListAdapter.FactsViewHolder>() {

    var factsList = ArrayList<CatFactModelItem>()
    fun setFactsList(factsList: List<CatFactModelItem>){
        this.factsList = factsList as ArrayList<CatFactModelItem>
        notifyDataSetChanged()
    }

    class FactsViewHolder(val binding: FactsListItemBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactsViewHolder {
        return FactsViewHolder(FactsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FactsViewHolder, position: Int) {
        Glide.with(holder.itemView).load(R.drawable.cat).into(holder.binding.catIcon)
        holder.binding.itemFactText.text = factsList[position].text
        holder.binding.factId.text = "ID: ${factsList[position]._id}"
        holder.itemView.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToDetailFragment(factsList[position].updatedAt,factsList[position].text)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return factsList.size
    }
}