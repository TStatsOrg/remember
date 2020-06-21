package com.app.shared.business

sealed class Either<T> {
    data class Success<T> (val data: T): Either<T>()
    data class Failure<T>(val error: Errors): Either<T>()
}