package errors.error_classes.default_error_classes

import errors.error_classes.IErrorClass

class EditDefaultErrorClass: IErrorClass {
    private var exceptionParams: List<String> = ArrayList()

    override fun setExceptionParams(params: List<String>) {
        this.exceptionParams = params
    }

    private fun genExceptionText(): List<String> {
        val exceptionText: ArrayList<String> = ArrayList()

        exceptionText.add("Ошибка")
        exceptionText.add("")
        exceptionText.add("Один из введённых параметров")
        exceptionText.add("содержит ошибку, нарушающую")
        exceptionText.add("целостность базы знаний.")

        return exceptionText
    }

    override fun getExceptionText(): List<String> {
        return genExceptionText()
    }
}