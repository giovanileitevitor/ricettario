package br.applabbs.ricettario.domain.local.usecases

import java.sql.Time

interface LocalDataUseCase {
    suspend fun startTimer(time: Time)
    suspend fun isTimerFinished(time: Time): Boolean
}