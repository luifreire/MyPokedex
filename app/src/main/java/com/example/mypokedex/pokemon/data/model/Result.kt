package com.example.mypokedex.pokemon.data.model

/**
 * Generic class for holding success response, error response and loading status
 */
data class Result<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T?): Result<T> {
            return Result(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, error: Error?): Result<T> {
            return Result(Status.ERROR, null, message)
        }

        fun <T> loading(data: T? = null): Result<T> {
            return Result(Status.LOADING, data, null)
        }
    }

    override fun toString(): String {
        return "Result(status=$status, data=$data, message=$message)"
    }
}