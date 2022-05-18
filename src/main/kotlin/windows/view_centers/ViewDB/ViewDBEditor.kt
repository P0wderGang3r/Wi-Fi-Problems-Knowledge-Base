package windows.view_centers.ViewDB

import javafx.collections.FXCollections
import tornadofx.*
import windows.controllers.ControllerDB.ControllerCrutch
import windows.controllers.ControllerDB.ControllerDBEditor

class ViewDBEditor: View() {
    private val sideController: ControllerDBEditor by inject()

    override val root = borderpane {
        val tableData = FXCollections.observableArrayList<ControllerCrutch>()

        left = listview(sideController.values) {
            minWidth = 180.0
            maxWidth = 180.0

            onLeftClick {
                sideController.setCurrentDBController(focusModel.focusedIndex)

                center = tableview(tableData) {
                    tableData.asyncItems { sideController.getList() }

                    columnResizePolicy = SmartResize.POLICY

                    if (sideController.numberOfFields == 1) {
                        column(sideController.namesOfFields[0], ControllerCrutch::getFirstValue)
                            .weightedWidth(sideController.weightsOfFields[0])
                    }

                    if (sideController.numberOfFields == 2) {
                        column(sideController.namesOfFields[0], ControllerCrutch::getFirstValue)
                            .weightedWidth(sideController.weightsOfFields[0])
                        column(sideController.namesOfFields[1], ControllerCrutch::getSecondValue)
                            .weightedWidth(sideController.weightsOfFields[1])

                    }

                    if (sideController.numberOfFields == 3) {
                        column(sideController.namesOfFields[0], ControllerCrutch::getFirstValue)
                            .weightedWidth(sideController.weightsOfFields[0])
                        column(sideController.namesOfFields[1], ControllerCrutch::getSecondValue)
                            .weightedWidth(sideController.weightsOfFields[1])
                        column(sideController.namesOfFields[2], ControllerCrutch::getThirdValue)
                            .weightedWidth(sideController.weightsOfFields[2])
                    }

                    contextmenu {
                        item("Добавить элемент").action {
                            val addElementView = ViewDBAddElement(sideController, this@tableview)
                            addElementView.openModal(resizable = false)
                        }

                        item("Редактировать элемент").action {
                            selectedItem?.apply {
                                val attribute1 = selectedItem!!.getFirstValue()
                                var attribute2 = ""
                                var attribute3 = ""

                                if (sideController.numberOfFields > 1)
                                    attribute2 = selectedItem!!.getSecondValue()

                                if (sideController.numberOfFields > 2)
                                    attribute3 = selectedItem!!.getThirdValue()

                                val editElementView = ViewDBEditElement(sideController, this@tableview, listOf(attribute1, attribute2, attribute3))
                                editElementView.openModal(resizable = false)

                                this@tableview.asyncItems { sideController.getList() }
                            }
                        }

                        item("Удалить элемент").action {
                            selectedItem?.apply {
                                val attribute1 = selectedItem!!.getFirstValue()
                                var attribute2 = ""
                                var attribute3 = ""

                                if (sideController.numberOfFields > 1)
                                    attribute2 = selectedItem!!.getSecondValue()

                                if (sideController.numberOfFields > 2)
                                    attribute3 = selectedItem!!.getThirdValue()

                                sideController.removeFromList(listOf(attribute1, attribute2, attribute3))

                                this@tableview.asyncItems { sideController.getList() }

                                //TODO: Создание окна с уведомлением об ошибке при удалении элемента с существующими зависимостями
                            }
                        }
                    }

                }
            }

        }

        center = tableview<ControllerCrutch> {}
    }
}