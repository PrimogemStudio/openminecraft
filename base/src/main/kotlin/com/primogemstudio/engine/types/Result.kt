package com.primogemstudio.engine.types

data class Result<R, E>(
    private val success: Boolean, 
    val result: R?, 
    val err: E?
) {
    companion object {
        @JvmStatic
        fun <R, E> success(d: R): Result<R, E> = Result(true, d, null)

        @JvmStatic
        fun <R, E> fail(e: E): Result<R, E> = Result(false, null, e)
    }

    val isSuccess: Boolean
        get() = success
    inline fun <FR> match(suc: (R) -> FR, err: (E) -> FR): FR = if (isSuccess) suc(this.result!!) else err(this.err!!)
}