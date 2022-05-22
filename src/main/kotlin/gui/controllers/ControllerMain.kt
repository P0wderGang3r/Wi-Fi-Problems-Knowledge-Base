package gui.controllers

import database_functions.initDataBase
import errors.ErrorClass
import gui.ErrorView
import lastError
import tornadofx.Controller

class ControllerMain: Controller() {

    fun initDB(path: String?) {
        if (path != null) {
            println(path)
            lastError = initDataBase(path)
            if (lastError != ErrorClass.NULL) {
                println("Предоставленная база знаний была прочитана частично или не прочитана вовсе")
                ErrorView().openModal()
            }
        }
        else
        {
            println("Предоставлен некорректный путь до базы знаний")
        }
    }

    fun saveDB(path: String?) {
        if (path != null) {
            println(path)
        }
        else
        {
            println("Предоставлен некорректный путь до базы знаний")
        }
    }
}