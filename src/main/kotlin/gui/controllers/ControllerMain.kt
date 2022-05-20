package gui.controllers

import database_functions.initDataBase
import tornadofx.Controller

class ControllerMain: Controller() {

    fun initDB(path: String?) {
        if (path != null) {
            println(path)
            if (!initDataBase(path))
                println("Предоставленная база знаний была прочитана частично или не прочитана вовсе")
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