package gui.controllers.controller_diagnostics

import diagnostics_functions.*
import tornadofx.Controller

class ControllerDiagnostics: Controller() {
    var diagnosticsResult: List<String> = ArrayList()

    fun calculateDiagnostics() {
        diagnosticsResult = getDiagnosticsResults()
    }

    fun isCorrectAttributeValue(attributeName: String, valueName: String): Boolean {
        return calculateIsCorrectAttributeValue(attributeName, valueName)
    }

    fun getResults(): List<String> {
        return listOf("Test")
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