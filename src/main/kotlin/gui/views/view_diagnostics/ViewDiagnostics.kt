package gui.views.view_diagnostics

import diagnostics_functions.refreshDiagnostics
import gui.controllers.controller_diagnostics.ControllerDiagnostics
import gui.controllers.controller_diagnostics.ControllerDiagnosticsCrutch
import javafx.collections.FXCollections
import javafx.scene.paint.Color
import tornadofx.*

class ViewDiagnostics: View() {
    private val sideController: ControllerDiagnostics by inject()

    override val root = borderpane {
        val tableData = FXCollections.observableArrayList<ControllerDiagnosticsCrutch>()
        tableData.asyncItems { sideController.getList() }

        val attributeValues = FXCollections.observableArrayList<String>()
        attributeValues.asyncItems { sideController.getValues() }

        center = tableview(tableData) {
            isEditable = true

            columnResizePolicy = SmartResize.POLICY

            column("Признак", ControllerDiagnosticsCrutch::firstProperty).weightedWidth(1.0)
            column<ControllerDiagnosticsCrutch, String?>(
                "Значение признака",
                ControllerDiagnosticsCrutch::secondProperty
            ).useComboBox(attributeValues).weightedWidth(0.8)

            onEditCommit {
                val previousValue = items[selectedCell!!.row].secondValue
                sideController.setValue(selectedCell!!.row, items[selectedCell!!.row].secondValue)

                style {
                    if (sideController.isCorrectAttributeValues(
                            items[selectedCell!!.row].firstValue,
                            items[selectedCell!!.row].secondValue
                        )
                    ) {
                        backgroundColor += Color.LIGHTGRAY
                    } else {
                        backgroundColor += Color.RED
                    }
                }
            }
        }

        bottom = hbox {
            paddingAll = 10.0

            vbox {
                paddingLeft = 10.0
                button("Сбросить запись") {
                    action {
                        refreshDiagnostics()
                        tableData.asyncItems { sideController.getList() }
                        attributeValues.asyncItems { sideController.getValues() }
                        this@borderpane.center.style {
                            backgroundColor += Color.LIGHTGRAY
                        }
                    }
                }
            }

            vbox {
                paddingLeft = 10.0
                button("Отобразить результаты диагностики") {
                    action {
                        sideController.calculateDiagnostics()
                        val resultsOfDiagnostics = ViewResultsOfDiagnostics(sideController)
                        resultsOfDiagnostics.openModal()
                    }
                }
            }

        }
    }
}