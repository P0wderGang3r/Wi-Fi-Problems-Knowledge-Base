package windows.controllers

import database_functions.initDataBase
import javafx.collections.FXCollections
import tornadofx.Controller
import javafx.scene.control.MenuItem
import tornadofx.action

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