package errors.error_classes.default_error_classes

import errors.error_classes.IErrorClass

class InitDefaultErrorClass: IErrorClass {
    private var exceptionParams: List<String> = ArrayList()

    override fun setExceptionParams(params: List<String>) {
        this.exceptionParams = params
    }

    private fun genExceptionText(): List<String> {
        val exceptionText: ArrayList<String> = ArrayList()

        exceptionText.add("Ошибка")
        exceptionText.add("")
        exceptionText.add("При прочтении базы знаний")
        exceptionText.add("возникла ошибка, ввиду которой")
        exceptionText.add("база знаний была загружена частично,")
        exceptionText.add("либо не была загружена вовсе.")

        return exceptionText
    }

    override fun getExceptionText(): List<String> {
        return genExceptionText()
    }
}