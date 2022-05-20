package gui.views.view_DB

import gui.controllers.controller_DB.ControllerDBCrutch
import gui.controllers.controller_DB.ControllerDBEditor
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.TableView
import tornadofx.*

class ViewDBAddElement(sideController: ControllerDBEditor, parentTableView: TableView<ControllerDBCrutch>): View() {
    private val arguments = listOf(SimpleStringProperty(), SimpleStringProperty(), SimpleStringProperty())

    override val root = vbox {
        title = "Добавление нового элемента"
        minWidth = 100.0
        paddingAll = 10.0
        paddingBottom = 15.0

        if (sideController.numberOfFields == 1) {
            label("Сорт \"${sideController.namesOfFields[0]}\":")
            textfield(arguments[0])
            label()
        }

        if (sideController.numberOfFields == 2) {
            label("Новое значение для сорта \"${sideController.namesOfFields[0]}\":")
            textfield(arguments[0])
            label()
            label("Новое значение для сорта \"${sideController.namesOfFields[1]}\":")
            textfield(arguments[1])
            label()

        }

        if (sideController.numberOfFields == 3) {
            label("Новое значение для сорта \"${sideController.namesOfFields[0]}\":")
            textfield(arguments[0])
            label()
            label("Новое значение для сорта \"${sideController.namesOfFields[1]}\":")
            textfield(arguments[1])
            label()
            label("Новое значение для сорта \"${sideController.namesOfFields[2]}\":")
            textfield(arguments[2])
            label()
        }

        button("Добавить новый элемент") {
            action {
                if(arguments[0].value == null)
                    arguments[0].value = ""
                if(arguments[1].value == null)
                    arguments[1].value = ""
                if(arguments[2].value == null)
                    arguments[2].value = ""

                sideController.addInList(listOf(arguments[0].value, arguments[1].value, arguments[2].value))

                parentTableView.asyncItems { sideController.getList() }

                close()
            }
        }
    }
}