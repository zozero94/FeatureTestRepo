package com.example.domain.usecase

import com.example.domain.repository.Repository
import javax.inject.Inject

class ApiUseCase @Inject constructor(private val repository: Repository) : UseCase {
    override fun getData() = repository.requestApi()
}