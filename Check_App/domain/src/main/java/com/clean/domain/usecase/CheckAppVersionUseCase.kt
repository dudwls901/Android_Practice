package com.clean.domain.usecase

import com.clean.domain.repository.SplashRepository
import javax.inject.Inject

class CheckAppVersionUseCase @Inject constructor(
    private val splashRepository: SplashRepository
) {
    fun execute() = splashRepository.checkAppVersion()
}