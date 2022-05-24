package gui.views.view_diagnostics

import gui.controllers.controller_diagnostics.ControllerDiagnostics
import javafx.collections.FXCollections
import tornadofx.View
import tornadofx.asyncItems
import tornadofx.borderpane
import tornadofx.listview

class ViewResultsOfDiagnostics(sideController: ControllerDiagnostics): View() {

    override val root = borderpane {
        title = "Результаты диагностики"
        val data = FXCollections.observableArrayList<String>()
        data.asyncItems { sideController.getResults() }

        center = listview(data) {
            minWidth = 600.0
        }
    }

}