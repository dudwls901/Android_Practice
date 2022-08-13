package com.clean.domain.usecase

import com.clean.domain.model.DomainScore
import com.clean.domain.repository.MainRepository
import javax.inject.Inject

class GetScoreUseCase @Inject constructor(
    private val mainRepository: MainRepository
){
    fun execute() = mainRepository.getScore()
}