package gui.controllers.controller_DB

import gui.controllers.controller_DB.ControllerDBInteractions.*
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.Controller

class ControllerDBEditor: Controller() {
    var currentDBController: ControllerDBInteractions = NULL

    var namesOfFields: List<String> = currentDBController.getNamesOfFields()

    var weightsOfFields: List<Double> = currentDBController.getWeightsOfFields()

    var numberOfFields: Int = currentDBController.getNumberOfFields()

    val values: ObservableList<String> = FXCollections.observableArrayList(
        "Неисправности",
        "Признаки",
        "Возможные значения",
        "Нормальные значения",
        "Признаки при неиспр.",
        "Значения при неиспр."
    )

    fun setCurrentDBController(currentDBNum: Int) {
        currentDBController = when (currentDBNum) {
            0 -> MALFUNCTION
            1 -> ATTRIBUTE
            2 -> ATTRIBUTE_AVAILABLE_VALUES
            3 -> ATTRIBUTE_NORMAL_VALUES
            4 -> ATTRIBUTE_PICTURE
            5 -> VALUES_BY_MALFUNCTION

            else -> NULL
        }

        namesOfFields = currentDBController.getNamesOfFields()
        weightsOfFields = currentDBController.getWeightsOfFields()
        numberOfFields = currentDBController.getNumberOfFields()
    }

    fun getList(): List<ControllerDBCrutch> {
        return currentDBController.getList()
    }

    fun addInList(arguments: List<String>) {
        currentDBController.addInList(arguments)
    }

    fun editInList(defaultArguments: List<String>, arguments: List<String>) {
        currentDBController.editInList(defaultArguments, arguments)
    }

    fun removeFromList(arguments: List<String>) {
        currentDBController.removeFromList(arguments)
    }
}