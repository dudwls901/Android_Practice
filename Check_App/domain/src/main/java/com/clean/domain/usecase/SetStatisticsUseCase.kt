package com.clean.domain.usecase

import com.clean.domain.repository.MainRepository
import javax.inject.Inject

class SetStatisticsUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    fun execute(plusValue: Int) = mainRepository.setStatistics(plusValue)
}