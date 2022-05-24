package gui.views.view_DB

import gui.controllers.controller_DB.ControllerDBCrutch
import gui.controllers.controller_DB.ControllerDBEditor
import gui.controllers.controller_DB.ControllerDBInteraction
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.TableView
import tornadofx.*

class ViewDBEditElement(sideController: ControllerDBEditor, parentTableView: TableView<ControllerDBCrutch>,
                        attributes: List<String>): View() {

    private val arguments = listOf(SimpleStringProperty(attributes[0]),
        SimpleStringProperty(attributes[1]), SimpleStringProperty(attributes[2]))

    override val root = vbox {
        title = "Изменение значений элемента"
        minWidth = 400.0
        paddingAll = 10.0
        paddingBottom = 15.0

        if (sideController.numberOfFields == 1) {
            label("Изменить значение сорта \"${sideController.namesOfFields[0]}\":")
            combobox(arguments[0]) {
                items = sideController.getColumn(0).asObservable()
            }
            label()
        }

        if (sideController.numberOfFields == 2) {
            label("Изменить значение сорта \"${sideController.namesOfFields[0]}\":")
            textfield(arguments[0])
            label()
            if (sideController.currentDBController != ControllerDBInteraction.ATTRIBUTE_AVAILABLE_VALUES) {
                label("Изменить зависимость сорта \"${sideController.namesOfFields[1]}\":")

                combobox(arguments[1]) {
                    useMaxWidth = true
                    items = sideController.getColumn(1).asObservable()
                }
            }
            else {
                label("Изменить значение сорта \"${sideController.namesOfFields[1]}\":")
                textfield(arguments[1])
            }
            label()

        }

        if (sideController.numberOfFields == 3) {
            label("Изменить значение сорта \"${sideController.namesOfFields[0]}\":")
            textfield(arguments[0])
            label()
            label("Изменить значение сорта \"${sideController.namesOfFields[1]}\":")
            textfield(arguments[1])
            label()
            label("Изменить зависимость сорта \"${sideController.namesOfFields[2]}\":")
            combobox(arguments[2]) {
                useMaxWidth = true
                items = sideController.getColumn(2).asObservable()
            }
            label()
        }

        button("Применить изменения элемента") {
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