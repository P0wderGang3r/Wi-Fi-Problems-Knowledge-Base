package errors.error_classes

interface IErrorClass {
    fun setExceptionParams(params: List<String>)

    fun getExceptionText(): List<String>
}