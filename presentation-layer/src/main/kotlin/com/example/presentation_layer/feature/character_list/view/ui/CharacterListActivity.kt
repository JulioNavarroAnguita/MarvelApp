package com.example.presentation_layer.feature.character_list.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain_layer.feature.CharacterListDomainLayerBridge
import com.example.presentation_layer.base.BaseMvvmView
import com.example.presentation_layer.base.ScreenState
import com.example.presentation_layer.databinding.ActivityCharacterListBinding
import com.example.presentation_layer.domain.CharacterVo
import com.example.presentation_layer.domain.FailureVo
import com.example.presentation_layer.feature.character_list.view.adapter.CharacterAdapter
import com.example.presentation_layer.feature.character_list.view.state.CharacterListState
import com.example.presentation_layer.feature.character_list.viewmodel.CharacterListViewModel
import com.example.presentation_layer.feature.character_detail.view.ui.CharacterDetailActivity
import com.example.presentation_layer.utils.isNetworkAvailable
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val CHARACTER = "characterVo"

class CharacterListActivity : AppCompatActivity(),
    BaseMvvmView<CharacterListViewModel, CharacterListDomainLayerBridge, CharacterListState> {

    private lateinit var viewBinding: ActivityCharacterListBinding
    private lateinit var characterAdapter: CharacterAdapter

    override val viewModel: CharacterListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCharacterListBinding.inflate(layoutInflater)
        initModel()
        setContentView(viewBinding.root)
        loadCharacters()
    }

    override fun processRenderState(renderState: CharacterListState?) {
        when (renderState) {
            CharacterListState.NoData -> displayNoData()
            is CharacterListState.ShowCharacterDetail -> navigateToCharacterDetailActivity(renderState.character)
            is CharacterListState.ShowCharacterList -> displayCharacters(renderState.characterList)
            is CharacterListState.ShowError -> showError(renderState.failure)
        }
    }

    private fun navigateToCharacterDetailActivity(character: CharacterVo) {
        val intent = Intent(this, CharacterDetailActivity::class.java).apply {
            putExtra(CHARACTER, character)
        }
        startActivity(intent)
    }

    private fun initModel() {
        viewModel.screenState.observe(this) { screenState ->
            when (screenState) {
                ScreenState.Loading -> showLoading()
                is ScreenState.Render<CharacterListState> -> {
                    processRenderState(screenState.renderState)
                    hideLoading()
                }
            }
        }
    }

    private fun displayNoData() {
        viewBinding.tvNoData.visibility = View.VISIBLE
    }

    private fun loadCharacters() {
        if (this@CharacterListActivity.isNetworkAvailable()) {
            viewModel.loadCharacters()
        } else {
            viewModel.loadCharactersFromDatabase()
        }
    }

    private fun hideLoading() {
        viewBinding.pbLoading.visibility = View.GONE
    }

    private fun showLoading() {
        viewBinding.pbLoading.visibility = View.VISIBLE
    }

    private fun displayCharacters(characterList: List<CharacterVo>) {
        characterAdapter = CharacterAdapter(characterList) { item ->
            viewModel.onCharacterItemClicked(item)
        }
        with(viewBinding) {
            rvItemList.run {
                layoutManager = LinearLayoutManager(context)
                adapter = characterAdapter
            }
        }
    }

    private fun showError(failure: FailureVo?) {
        if (failure?.msgResource != null) {
            viewBinding.tvNoData.visibility = View.VISIBLE
            Toast.makeText(this, failure.msgResource, Toast.LENGTH_SHORT).show()
        }
    }

}