package gui.controllers.controller_diagnostics

import tornadofx.getProperty
import tornadofx.property

class ControllerDiagnosticsCrutch(firstValue: String,
                                  secondValue: String)
{
    var firstValue: String by property(firstValue)
    fun firstProperty() = getProperty(ControllerDiagnosticsCrutch::firstValue)

    var secondValue: String by property(secondValue)
    fun secondProperty() = getProperty(ControllerDiagnosticsCrutch::secondValue)
}