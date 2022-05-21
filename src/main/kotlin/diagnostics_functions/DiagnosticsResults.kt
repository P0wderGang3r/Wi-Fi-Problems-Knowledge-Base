package diagnostics_functions

import attributePictures
import data_classes.DiagnosticsMalfunctionClass
import database_functions.findAttribute
import database_functions.findValue
import diagnostics

/**
 * Проверка, является ли значение принадлежащим множеству значений сопоставленного признака
 */
fun calculateIsCorrectAttributeValue(attributeName: String, valueName: String): Boolean {
    val attribute = findAttribute(attributeName) ?: return false

    if (valueName == "")
        return true

    if (!findValue(valueName, attribute.availableValues)) {
        return false
    }

    return true
}

/**
 * Проверка, является ли каждое из значений принадлежащим множеству значений сопоставленного признака
 */
fun checkAttributeValues(): ArrayList<String> {
    val diagnosticsVerdict: ArrayList<String> = ArrayList()

    for (diagnosticsEntry in diagnostics) {
        if (!calculateIsCorrectAttributeValue(diagnosticsEntry.attribute.name, diagnosticsEntry.value)) {
            if (diagnosticsVerdict.size == 0) {
                diagnosticsVerdict.add("Ошибка вывода результата")
                diagnosticsVerdict.add("")
                diagnosticsVerdict.add("Следующим параметрам были заданы некорректные значения:")
            }

            diagnosticsVerdict.add(diagnosticsEntry.attribute.name)
        }
    }

    return diagnosticsVerdict
}

/**
 * Если слот с неверным значением, создаём вердикт о некорректном значении (если пустой, то игнорируем)
 * Поочереди проходим по списку valuesByMalfunction, собирая значения при неисправности
 * Составляем коэффициент правдоподобия для данного класса неисправности
 * Составляем отчёт по данному классу
 */
fun getDiagnosticsResults(): List<String> {

    val diagnosticsVerdict: ArrayList<String> = checkAttributeValues()
    val diagnosticsMalfunctionClasses: ArrayList<DiagnosticsMalfunctionClass> = ArrayList()

    if (diagnosticsVerdict.size != 0) {
        return diagnosticsVerdict
    }

    diagnosticsVerdict.add("Отчёт о совпадении с классами неисправностей")
    diagnosticsVerdict.add("")

    for (picture in attributePictures) {
        var numOfCorrectAttributes = 0.0
        diagnosticsVerdict.add("| Неисправность:")
        diagnosticsVerdict.add("|->| ${picture.malfunction.name}")
        diagnosticsVerdict.add("| Перечень совпадающих значений признаков:")

        //Проходим по всем признакам при неисправности
        for (valuesByAttribute in picture.valuesByAttributes) {

            //Проходим по каждому полю диагностики
            for (diagnosticsEntry in diagnostics) {
                //Если признак совпадает, то
                if (diagnosticsEntry.attribute == valuesByAttribute.attribute) {

                    //Проверяем каждое поле признака
                    for (valueByMalfunction in valuesByAttribute.values) {
                        //Если совпадает с одним из значений при неисправности, то добавляем как корректный
                        if (valueByMalfunction.equals(diagnosticsEntry.value)) {
                            diagnosticsVerdict.add("|->| ${diagnosticsEntry.attribute.name}")
                            numOfCorrectAttributes += 1
                            break
                        }
                    }

                }
            }
        }
        //Добавляем неисправность в перечень неисправностей с их коэффициентами совпадения
        diagnosticsMalfunctionClasses.add(
            DiagnosticsMalfunctionClass(
                picture.malfunction.name,
                numOfCorrectAttributes / picture.valuesByAttributes.size
            )
        )

        if (numOfCorrectAttributes < 1) {
            diagnosticsVerdict.add("|->| совпадающих признаков не обнаружено")
        }

        diagnosticsVerdict.add("| Процент совпадения с классом:")
        diagnosticsVerdict.add("|->| ${((numOfCorrectAttributes / picture.valuesByAttributes.size) * 100).toInt()}%")
        diagnosticsVerdict.add("")
    }

    //Вычисляем наивысшее значение коэффициента совпадения
    var bestCoefficient = 0.0
    for (diagnosticsBestCoefficient in diagnosticsMalfunctionClasses) {
        if (bestCoefficient < diagnosticsBestCoefficient.coefficient) {
            bestCoefficient = diagnosticsBestCoefficient.coefficient
        }
    }

    val diagnosticsBestList: ArrayList<DiagnosticsMalfunctionClass> = ArrayList()

    //Добавляем все неисправности с наивысшим коэффициентом совпадения в список лучших совпадений
    for (diagnosticsBestCoefficient in diagnosticsMalfunctionClasses) {
        if (diagnosticsBestCoefficient.coefficient == bestCoefficient)
            diagnosticsBestList.add(diagnosticsBestCoefficient)
    }

    diagnosticsVerdict.add("| Наилучшие совпадения с классами неисправности")

    //Если лучший коэффициент является хоть сколь-нибудь значимым, то выводим на экран все неисправности
    if (bestCoefficient > 0.01) {
        diagnosticsVerdict.add("| Процент совпадения:")
        diagnosticsVerdict.add("|->| ${(diagnosticsBestList[0].coefficient * 100).toInt()}%")
        diagnosticsVerdict.add("| Перечень неисправностей:")
        for (diagnosticsBestListEntry in diagnosticsBestList) {
            diagnosticsVerdict.add("|->| ${diagnosticsBestListEntry.malfunctionName}")
        }
    } else {
        diagnosticsVerdict.add("|->| Предоставлено недостаточное количество данных,")
        diagnosticsVerdict.add("|->| либо введённые данные не соответствуют ни единой неисправности")
        diagnosticsVerdict.add("|->| для предоставления вердикта на основании результатов диагностики.")
    }

    return diagnosticsVerdict
}