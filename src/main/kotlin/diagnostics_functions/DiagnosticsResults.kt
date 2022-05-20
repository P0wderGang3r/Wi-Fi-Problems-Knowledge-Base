package diagnostics_functions

import database_functions.findAttribute
import database_functions.findValue

fun getDiagnosticsResults(): List<String> {

    return emptyList()
}

fun calculateIsCorrectAttributeValue(attributeName: String, valueName: String): Boolean {
    val attribute = findAttribute(attributeName) ?: return false

    if (valueName == "")
        return true

    if (!findValue(valueName, attribute.availableValues)) {
        return false
    }

    return true
}

/*TODO:
 * Если слот с неверным значением, создаём вердикт о некорректном значении (если пустой, то игнорируем)
 * Поочереди проходим по списку valuesByMalfunction, собирая значения при неисправности
 * Составляем коэффициент правдоподобия для данного класса неисправности
 * Составляем отчёт по данному классу
 */