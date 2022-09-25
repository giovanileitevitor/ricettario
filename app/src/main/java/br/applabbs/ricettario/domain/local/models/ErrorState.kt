package br.applabbs.ricettario.domain.local.models

data class ErrorState(
    val hasError: Boolean,
    val statusError: String
)

data class AckState(
    val hasSuccess: Boolean,
    val statusAck: String
)
