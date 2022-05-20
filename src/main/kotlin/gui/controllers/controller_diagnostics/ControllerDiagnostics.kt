package gui.controllers.controller_diagnostics

import diagnostics_functions.*
import tornadofx.Controller

class ControllerDiagnostics: Controller() {
    var diagnosticsResult: List<String> = ArrayList()

    fun calculateDiagnostics() {
        diagnosticsResult = getDiagnosticsResults()
    }

    fun isCorrectAttributeValues(attributeName: String, valueName: String): Boolean {
        return checkAttributeValues().size == 0
    }

    fun getResults(): List<String> {
        return diagnosticsResult
    }

    fun setValue(row: Int, value: String) {
        setDiagnosticsValue(row, value)
    }

    fun getValues(): List<String> {
        return getAttributeValues()
    }

    fun getList(): List<ControllerDiagnosticsCrutch> {
        return getDiagnosticsList()
    }
}