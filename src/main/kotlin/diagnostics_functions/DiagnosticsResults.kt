package diagnostics_functions

import attributePictures
import attributes
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

    if (findValue(valueName, attribute.availableValues) == null) {
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
                diagnosticsVerdict.add("Отчёт о совпадении с классами неисправностей")
                diagnosticsVerdict.add("")
                diagnosticsVerdict.add("Ошибка вывода результата")
                diagnosticsVerdict.add("")
                diagnosticsVerdict.add("Следующим признакам были заданы некорректные значения:")
            }

            diagnosticsVerdict.add(diagnosticsEntry.attribute.name)
        }
    }

    return diagnosticsVerdict
}

private fun checkIfEverythingIsEmpty(): ArrayList<String> {
    val diagnosticsVerdict: ArrayList<String> = ArrayList()

    var isEverythingEmpty = true

    for (diagnosticsEntry in diagnostics) {
        if (diagnosticsEntry.value != "") {
            isEverythingEmpty = false
            break
        }
    }

    if (!isEverythingEmpty)
        return diagnosticsVerdict

    diagnosticsVerdict.add("Отчёт о совпадении с классами неисправностей")
    diagnosticsVerdict.add("")
    diagnosticsVerdict.add("Ошибка вывода результата")
    diagnosticsVerdict.add("")
    diagnosticsVerdict.add("Ни одному из признаков обследуемого объекта")
    diagnosticsVerdict.add("не были присвоены их значения.")

    return diagnosticsVerdict
}

/**
 * Проверка, что каждый из признаков из признаков при неисправности имеет хотя-бы одно значение
 *  из множества значений признака
 */
private fun checkAttributePictureValues(): ArrayList<String> {
    val diagnosticsVerdict: ArrayList<String> = ArrayList()

    for (picture in attributePictures) {
        for (attribute in picture.valuesByAttributes) {
            if (attribute.values.size == 0) {
                if (diagnosticsVerdict.size == 0) {
                    diagnosticsVerdict.add("Отчёт о совпадении с классами неисправностей")
                    diagnosticsVerdict.add("")
                    diagnosticsVerdict.add("Ошибка вывода результата")
                    diagnosticsVerdict.add("")
                    diagnosticsVerdict.add("Нарушена целостность базы знаний.")
                    diagnosticsVerdict.add("Множество значений при неисправности является")
                    diagnosticsVerdict.add("пустым для следующих признаков при неисправностях:")
                }
                diagnosticsVerdict.add("${attribute.attribute.name} при ${picture.malfunction.name}")
            }
        }
    }

    return diagnosticsVerdict
}

private fun calculateDiagnosticsResults
            (diagnosticsMalfunctionClasses: ArrayList<DiagnosticsMalfunctionClass>): ArrayList<String> {
    val diagnosticsVerdict: ArrayList<String> = checkAttributeValues()

    diagnosticsVerdict.add("Отчёт о совпадении с классами неисправностей")
    diagnosticsVerdict.add("")

    for (picture in attributePictures) {
        val localDiagnostics: ArrayList<String> = ArrayList()
        var numOfCorrectAttributes = 0.0
        var isWrong = false

        localDiagnostics.add("| Неисправность:")
        localDiagnostics.add("|->| ${picture.malfunction.name}")
        localDiagnostics.add("| Перечень совпадающих значений признаков:")

        //Проверка, что все поля соответствуют только одной клинической картине
            for (diagnosticsEntry in diagnostics) {
                if (diagnosticsEntry.value != "") {
                    isWrong = true

                    //Если один из признаков при неисправности совпадает с
                    // обозреваемым признаком обследуемого объекта, то...
                    for (valuesByAttribute in picture.valuesByAttributes) {
                        if (diagnosticsEntry.attribute == valuesByAttribute.attribute) {
                            //...картина мира не нарушена, живём
                            isWrong = false
                            break
                        }
                    }

                    //Если же чудо не произошло, то заканчиваем наши мучения...
                    if (isWrong)
                        break
                }
            }

        //...и анализируем следующую картину мира
        if (isWrong)
            continue

        //Проходим по всем признакам при неисправности
        for (valuesByAttribute in picture.valuesByAttributes) {

            //Проходим по каждому полю диагностики
            for (diagnosticsEntry in diagnostics) {
                //Если признак совпадает, то
                if (diagnosticsEntry.attribute == valuesByAttribute.attribute) {

                    //Проверяем каждое поле признака
                    for (valueByMalfunction in valuesByAttribute.values) {
                        //Если совпадает с одним из значений при неисправности, то добавляем как корректный
                        if (valueByMalfunction.value.equals(diagnosticsEntry.value)) {
                            localDiagnostics.add("|->| ${diagnosticsEntry.attribute.name}")
                            numOfCorrectAttributes += 1
                            break
                        }
                    }

                }
            }
        }

        if (numOfCorrectAttributes < 0.01)
            continue

        localDiagnostics.add("| Процент совпадения полей с неисправностью:")
        localDiagnostics.add("|->| ${((numOfCorrectAttributes / picture.valuesByAttributes.size) * 100).toInt()}%")
        localDiagnostics.add("")

        diagnosticsVerdict.addAll(localDiagnostics)

        //Добавляем неисправность в перечень неисправностей с их коэффициентами совпадения
        diagnosticsMalfunctionClasses.add(
            DiagnosticsMalfunctionClass(
                picture.malfunction.name,
                numOfCorrectAttributes / picture.valuesByAttributes.size
            )
        )
    }

    return diagnosticsVerdict
}

private fun calculateDiagnosticsBestList
            (diagnosticsMalfunctionClasses: ArrayList<DiagnosticsMalfunctionClass>)
: ArrayList<DiagnosticsMalfunctionClass> {
    val diagnosticsBestList: ArrayList<DiagnosticsMalfunctionClass> = ArrayList()

    //Вычисляем наивысшее значение коэффициента совпадения
    var bestCoefficient = 0.0
    for (diagnosticsBestCoefficient in diagnosticsMalfunctionClasses) {
        if (bestCoefficient < diagnosticsBestCoefficient.coefficient) {
            bestCoefficient = diagnosticsBestCoefficient.coefficient
        }
    }

    //Добавляем все неисправности с наивысшим коэффициентом совпадения в список лучших совпадений
    for (diagnosticsBestCoefficient in diagnosticsMalfunctionClasses) {
        if (diagnosticsBestCoefficient.coefficient == bestCoefficient)
            diagnosticsBestList.add(diagnosticsBestCoefficient)
    }

    return diagnosticsBestList
}

fun calculateIfThereAreNoMalfunctions(): List<String> {
    val diagnosticsVerdict: ArrayList<String> = checkAttributeValues()

    var isWrong = false

    //Берём следующее вхождение в признаковом описании обследуемого объекта
    //Если поле диагностики пустое, то пропускаем
    //Берём признак и ищем совпадение двух признаков
    //Если два признака совпали, то обвиняем признак в том, что он не верен нормальным значениям
    //Если он доказывает обратное, то переходим к следующему вхождению в признаково описание
    for (diagnosticsEntry in diagnostics) {
        if (diagnosticsEntry.value == "")
            continue

        for (attribute in attributes) {
            if (diagnosticsEntry.attribute == attribute) {

                isWrong = true
                for (normalValue in attribute.normalValues) {
                    if (diagnosticsEntry.value == normalValue.value) {
                        isWrong = false
                        break
                    }
                }
                break
            }
        }

        if (isWrong)
            break
    }

    if (isWrong)
        return diagnosticsVerdict

    diagnosticsVerdict.add("Введённые данные соответствуют нормальному")
    diagnosticsVerdict.add("состоянию обследуемого объекта.")

    return diagnosticsVerdict
}

/**
 * Если слот с неверным значением, создаём вердикт о некорректном значении (если пустой, то игнорируем)
 * Поочереди проходим по списку valuesByMalfunction, собирая значения при неисправности.
 * Составляем коэффициент правдоподобия для данного класса неисправности.
 * Составляем отчёт по данному классу.
 */
fun getDiagnosticsResults(): List<String> {
    val diagnosticsMalfunctionClasses: ArrayList<DiagnosticsMalfunctionClass> = ArrayList()

    var diagnosticsVerdict: ArrayList<String> = checkAttributeValues()
    if (diagnosticsVerdict.isNotEmpty()) {
        return diagnosticsVerdict
    }

    diagnosticsVerdict = checkAttributePictureValues()
    if (diagnosticsVerdict.isNotEmpty())
        return diagnosticsVerdict

    diagnosticsVerdict = checkIfEverythingIsEmpty()
    if (diagnosticsVerdict.isNotEmpty())
        return diagnosticsVerdict

    //---------------------------------------------------------------------------------
    diagnosticsVerdict = calculateDiagnosticsResults(diagnosticsMalfunctionClasses)
    //---------------------------------------------------------------------------------
    val diagnosticsBestList = calculateDiagnosticsBestList(diagnosticsMalfunctionClasses)
    //---------------------------------------------------------------------------------

    //Если лучший коэффициент является хоть сколь-нибудь значимым, то выводим на экран все неисправности
    if (diagnosticsBestList.isNotEmpty()) {
        diagnosticsVerdict.add("| Наилучшие совпадения с классами неисправности")
        diagnosticsVerdict.add("| Процент совпадения:")
        diagnosticsVerdict.add("|->| ${(diagnosticsBestList[0].coefficient * 100).toInt()}%")
        diagnosticsVerdict.add("| Перечень неисправностей:")
        for (diagnosticsBestListEntry in diagnosticsBestList) {
            diagnosticsVerdict.add("|->| ${diagnosticsBestListEntry.malfunctionName}")
        }
    } else {
        val localDiagnostics = calculateIfThereAreNoMalfunctions()

        if (localDiagnostics.isNotEmpty()) {
            diagnosticsVerdict.addAll(localDiagnostics)
        }
        else {
            diagnosticsVerdict.add("Ошибка вывода результата")
            diagnosticsVerdict.add("")
            diagnosticsVerdict.add("Введённые данные не соответствуют ни единой неисправности")
            diagnosticsVerdict.add("для предоставления вердикта на основании результатов диагностики.")
        }
    }

    return diagnosticsVerdict
}
