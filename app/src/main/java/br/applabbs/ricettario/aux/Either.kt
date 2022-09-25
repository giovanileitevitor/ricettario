package br.applabbs.ricettario.aux


sealed class Either<out L, out R>{
    data class Error<out L>(val errorData: L): Either<L, Nothing>()
    data class Success<out R>(val data: R): Either<Nothing, R>()

    val isSuccess get() = this is Success<R>

    val isError get() = this is  Error<L>

    fun <L> makeError(errorData: L) = Error(errorData)

    fun <R> makeSuccess(data: R) = Success(data)

    fun fold(onError: (L) -> Any, onSuccess: (R) -> Any): Any =
        when(this){
            is Error -> onError(errorData)
            is Success -> onSuccess(data)
        }

    suspend fun foldAssync(onError: suspend (L) -> Any, onSuccess: (R) -> Any): Any =
        when(this){
            is Error -> onError(errorData)
            is Success -> onSuccess(data)
        }
}

fun <T, L, R> Either<L, R>.map(fn: (R) -> (T)): Either<L, T> =  this.flatMap(fn.c(::makeSuccess))

fun <T, L, R> Either<L, R>.flatMap(fn: (R) -> Either<L, T>): Either<L, T> =
    when(this){
        is Either.Error -> Either.Error(errorData)
        is Either.Success -> fn(data)
    }

fun <A, B, C>((A) -> B).c(f: (B) -> C): (A) -> C = {
    f(this(it))
}

fun <T> requestTryCatch(request: Either<Failure, T>) =
    try {
        request
    } catch (error: Exception){
        Either.Error(Failure.GenericFailure)
    }