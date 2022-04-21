package com.example.presentation_layer.feature.character_list.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.presentation_layer.databinding.ItemCharacterAdapterBinding
import com.example.presentation_layer.domain.CharacterVo
import com.example.presentation_layer.utils.setImageFromUrlString

class CharacterViewHolder(private val itemBinding: ItemCharacterAdapterBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun onBind(item: CharacterVo, callback: (CharacterVo) -> Unit) {
        with(itemBinding) {
            nameCharacter.text = item.name
            imageCharacte.setImageFromUrlString(item.thumbnail.path + "." + item.thumbnail.extension)
        }
        itemView.setOnClickListener { callback(item) }
    }

}
