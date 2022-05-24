package gui

import errors.ErrorClass
import lastError
import tornadofx.View
import tornadofx.asObservable
import tornadofx.listview

class ErrorView: View() {
    override val root = listview<String> {
        title = "Ошибка выполнения"

        minWidth = 300.0
        maxHeight = 300.0

        items = lastError.getErrorHandler()!!.getExceptionText().asObservable()

        lastError = ErrorClass.NULL
    }
}