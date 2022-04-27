package com.example.presentation_layer.feature.character_list.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation_layer.databinding.ItemCharacterAdapterBinding
import com.example.presentation_layer.domain.CharacterVo
import com.example.presentation_layer.feature.character_list.view.viewholder.CharacterViewHolder

class CharacterAdapter(
    private var characterList: List<CharacterVo>,
    private val onItemSelected: (CharacterVo) -> Unit
) : RecyclerView.Adapter<CharacterViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : CharacterViewHolder {
        val itemBinding = ItemCharacterAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(itemBinding)
    }


    override fun getItemCount() = characterList.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.onBind(characterList[position], onItemSelected)
    }

}
