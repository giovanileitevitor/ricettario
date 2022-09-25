package br.applabbs.ricettario.aux

sealed class Failure{
    object GenericFailure : Failure()
    class RoomFailure(val message: String?): Failure()
    class NetworkFailure(val message: String?, val code: Int): Failure()
}