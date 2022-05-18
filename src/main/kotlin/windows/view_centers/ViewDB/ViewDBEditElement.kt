package windows.view_centers.ViewDB

import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.TableView
import tornadofx.*
import windows.controllers.ControllerDB.ControllerCrutch
import windows.controllers.ControllerDB.ControllerDBEditor

class ViewDBEditElement(sideController: ControllerDBEditor, parentTableView: TableView<ControllerCrutch>,
                        attributes: List<String>): View() {

    private val arguments = listOf(SimpleStringProperty(attributes[0]),
        SimpleStringProperty(attributes[1]), SimpleStringProperty(attributes[2]))

    override val root = vbox {
        title = "Редактирование элемента"
        minWidth = 100.0
        paddingAll = 10.0
        paddingBottom = 15.0

        if (sideController.numberOfFields == 1) {
            label("${sideController.namesOfFields[0]}:")
            textfield(arguments[0])
            label()
        }

        if (sideController.numberOfFields == 2) {
            label("${sideController.namesOfFields[0]}:")
            textfield(arguments[0])
            label()
            label("${sideController.namesOfFields[1]}:")
            textfield(arguments[1])
            label()

        }

        if (sideController.numberOfFields == 3) {
            label("${sideController.namesOfFields[0]}:")
            textfield(arguments[0])
            label()
            label("${sideController.namesOfFields[1]}:")
            textfield(arguments[1])
            label()
            label("${sideController.namesOfFields[2]}:")
            textfield(arguments[2])
            label()
        }

        button("Изменить элемент") {
            action {
                if(arguments[0].value == null)
                    arguments[0].value = ""
                if(arguments[1].value == null)
                    arguments[1].value = ""
                if(arguments[2].value == null)
                    arguments[2].value = ""

                sideController.editInList(attributes, listOf(arguments[0].value, arguments[1].value, arguments[2].value))

                parentTableView.asyncItems { sideController.getList() }

                close()
            }
        }
    }
}