package com.example.retrofit2practice1.presentation.viewmodel

import androidx.lifecycle.ViewModel

class GitHubViewModel : ViewModel() {
    private var name = ""

    fun setName(newName: String) {
        name = newName
    }

    fun getName() = name
}