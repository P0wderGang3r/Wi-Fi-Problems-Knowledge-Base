package gui.views.view_DB

import gui.controllers.controller_DB.ControllerDBCrutch
import gui.controllers.controller_DB.ControllerDBEditor
import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import javafx.scene.text.TextAlignment
import tornadofx.*

class ViewDBEditor: View() {
    private val sideController: ControllerDBEditor by inject()

    override val root = borderpane {
        val tableData = FXCollections.observableArrayList<ControllerDBCrutch>()

        left = vbox {

            label("Сорт") {
                paddingAll = 4.0
                useMaxWidth = true
                alignment = Pos.CENTER
                textAlignment = TextAlignment.CENTER
                style {
                    fontSize = 14.px
                    fontWeight = FontWeight.BOLD
                }
            }
            listview(sideController.values) {
                minWidth = 180.0
                maxWidth = 180.0

                fitToParentSize()

                onLeftClick {
                    sideController.setCurrentDBController(focusModel.focusedIndex)
                    tableData.asyncItems { sideController.getList() }

                    center = tableview(tableData) {

                        columnResizePolicy = SmartResize.POLICY

                        if (sideController.numberOfFields == 1) {
                            column(sideController.namesOfFields[0], ControllerDBCrutch::getFirstValue)
                                .weightedWidth(sideController.weightsOfFields[0])
                        }

                        if (sideController.numberOfFields == 2) {
                            column(sideController.namesOfFields[0], ControllerDBCrutch::getFirstValue)
                                .weightedWidth(sideController.weightsOfFields[0])
                            column(sideController.namesOfFields[1], ControllerDBCrutch::getSecondValue)
                                .weightedWidth(sideController.weightsOfFields[1])

                        }

                        if (sideController.numberOfFields == 3) {
                            column(sideController.namesOfFields[0], ControllerDBCrutch::getFirstValue)
                                .weightedWidth(sideController.weightsOfFields[0])
                            column(sideController.namesOfFields[1], ControllerDBCrutch::getSecondValue)
                                .weightedWidth(sideController.weightsOfFields[1])
                            column(sideController.namesOfFields[2], ControllerDBCrutch::getThirdValue)
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
                                }
                            }
                        }

                    }
                }

            }
        }

        center = tableview(tableData) {
            columnResizePolicy = SmartResize.POLICY

            column(sideController.namesOfFields[0], ControllerDBCrutch::getFirstValue)
                .weightedWidth(sideController.weightsOfFields[0])
        }
    }
}