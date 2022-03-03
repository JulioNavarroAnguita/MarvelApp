package com.example.presentation_layer.feature.ui.characterList.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain_layer.feature.characterList.CharacterListDomainLayerBridge
import com.example.presentation_layer.base.BaseMvvmView
import com.example.presentation_layer.base.ScreenState
import com.example.presentation_layer.databinding.ActivityCharacterListBinding
import com.example.presentation_layer.domain.CharacterVo
import com.example.presentation_layer.domain.FailureVo
import com.example.presentation_layer.feature.ui.characterList.view.adapter.CharacterAdapter
import com.example.presentation_layer.feature.ui.characterList.view.state.CharacterListState
import com.example.presentation_layer.feature.ui.characterList.viewmodel.CharacterListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterListActivity : AppCompatActivity(),
    BaseMvvmView<CharacterListViewModel, CharacterListDomainLayerBridge, CharacterListState> {

    override val viewModel: CharacterListViewModel by viewModel()
    private lateinit var viewBinding: ActivityCharacterListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCharacterListBinding.inflate(layoutInflater)
        initModel()
        setContentView(viewBinding.root)
        initView()
    }

    override fun processRenderState(renderState: CharacterListState?) {
        when (renderState) {
            CharacterListState.NoData -> displayNoData()
            is CharacterListState.ShowCharacterDetail -> TODO()
            is CharacterListState.ShowCharacterList -> displayCharacters(renderState.characterList)
            is CharacterListState.ShowError -> showError(renderState.failure)
        }
    }

    private fun initModel() {
        viewModel.screenState.observe(this, { screenState ->
            when (screenState) {
                ScreenState.Loading -> showLoading()
                is ScreenState.Render<CharacterListState> -> {
                    processRenderState(screenState.renderState)
                    hideLoading()
                }
            }
        })
    }

    private fun displayNoData() {
        viewBinding.tvNoData.visibility = View.VISIBLE
    }

    private fun initView() {
        viewModel.loadCharacters()
    }

    private fun hideLoading() {
        viewBinding.pbLoading.visibility = View.GONE
    }

    private fun showLoading() {
        viewBinding.pbLoading.visibility = View.VISIBLE
    }

    private fun displayCharacters(characterList: List<CharacterVo>) {
        with(viewBinding) {
            rvItemList.run {
                layoutManager = LinearLayoutManager(context)
                adapter = CharacterAdapter(characterList) { item ->
                    viewModel.onCharacterItemClicked(item)
                }
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