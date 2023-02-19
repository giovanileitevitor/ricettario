package br.applabbs.ricettario.domain.local.usecases

import android.content.SharedPreferences
import java.sql.Time

class LocalDataUseCaseImpl(): LocalDataUseCase {

    override suspend fun isTimerFinished(currentTime: Time): Boolean {
        //Compare the current date with last date stored
        //var storedTime = SharedPref.getStoredTime("STORED_TIME")
        //return currentTime == storedTime
        return false
    }

    override suspend fun startTimer(time: Time) {
        //save timer on SharedPref
    }
}