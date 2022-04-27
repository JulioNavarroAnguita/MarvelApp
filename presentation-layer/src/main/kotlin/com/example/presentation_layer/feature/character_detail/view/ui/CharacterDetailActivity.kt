package com.example.presentation_layer.feature.character_detail.view.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.domain_layer.feature.CharacterDetailDomainLayerBridge
import com.example.presentation_layer.base.BaseMvvmView
import com.example.presentation_layer.base.ScreenState
import com.example.presentation_layer.databinding.ActivityCharacterDetailBinding
import com.example.presentation_layer.domain.CharacterVo
import com.example.presentation_layer.domain.FailureVo
import com.example.presentation_layer.feature.character_detail.view.state.CharacterDetailState
import com.example.presentation_layer.feature.character_detail.viewmodel.CharacterDetailViewModel
import com.example.presentation_layer.utils.setImageFromUrlString
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

private const val CHARACTER = "characterVo"

class CharacterDetailActivity : AppCompatActivity(),
    BaseMvvmView<CharacterDetailViewModel, CharacterDetailDomainLayerBridge, CharacterDetailState> {

    private lateinit var viewBinding: ActivityCharacterDetailBinding

    override val viewModel: CharacterDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        initModel()
        setContentView(viewBinding.root)
        initView()
        viewModel.loadCharacterDetail(getArguments().id)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun processRenderState(renderState: CharacterDetailState?) {
        when (renderState) {
            is CharacterDetailState.ShowCharacterDetail -> showCharacterDetail(renderState.character)
            is CharacterDetailState.ShowError -> showError(renderState.failure)
        }
    }

    private fun showError(failure: FailureVo?) {
        if (failure?.msgResource != null) {
            viewBinding.tvNoData.visibility = View.VISIBLE
            Toast.makeText(this, failure.msgResource, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getArguments() = intent.getSerializableExtra(CHARACTER) as CharacterVo

    private fun initModel() {
        viewModel.screenState.observe(this) { screenState ->
            when (screenState) {
                ScreenState.Loading -> showLoading()
                is ScreenState.Render<CharacterDetailState> -> {
                    processRenderState(screenState.renderState)
                    hideLoading()
                }
            }
        }
    }

    private fun showCharacterDetail(character: CharacterVo) {
        supportActionBar?.title = character.name
        with(viewBinding) {
            characterImage.setImageFromUrlString(character.thumbnail.path + "." + character.thumbnail.extension)
            characterDescription.text = character.description.ifBlank { "No data" }
        }
    }

    private fun hideLoading() {
        viewBinding.progressbar.visibility = View.GONE
    }

    private fun showLoading() {
        viewBinding.progressbar.visibility = View.VISIBLE
    }

}