package com.example.domain.usecase

import com.example.domain.repository.Repository

class UseCase(private val repository: Repository) {
    fun getData() = repository.requestApi()
}