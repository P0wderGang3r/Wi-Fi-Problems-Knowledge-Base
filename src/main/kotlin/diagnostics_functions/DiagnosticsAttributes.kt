package diagnostics_functions

import attributes
import data_classes.DiagnosticsClass
import diagnostics
import gui.controllers.controller_diagnostics.ControllerDiagnosticsCrutch

/**
 * Сброс списка диагностируемых признаков
 */
fun refreshDiagnostics() {
    diagnostics.clear()

    for (attribute in attributes) {
        diagnostics.add(DiagnosticsClass(attribute, ""))
    }
}

/**
 * Фиксация диагностируемого значения в памяти программного средства
 */
fun setDiagnosticsValue(row: Int, value: String) {
    diagnostics[row].value = value
}

/**
 * Получение множества всех значений всех неисправностей
 */
fun getAttributeValues(): List<String> {
    val listOfValues: ArrayList<String> = ArrayList()

    listOfValues.add("")

    for (attribute in attributes) {
        for (value in attribute.availableValues) {
            var isAlreadyAdded = false

            for (valueInList in listOfValues) {
                if (value.equals(valueInList))
                    isAlreadyAdded = true
            }

            if (!isAlreadyAdded)
                listOfValues.add(value)
        }
    }

    return listOfValues
}

/**
 * Получение множества диагностируемых признаков с их значениями
 */
fun getDiagnosticsList(): List<ControllerDiagnosticsCrutch> {
    val result: ArrayList<ControllerDiagnosticsCrutch> = ArrayList()

    for (diagnosticsEntry in diagnostics) {
        result.add(
            ControllerDiagnosticsCrutch(
                diagnosticsEntry.attribute.name,
                diagnosticsEntry.value
            )
        )
    }

    return result
}