package database_functions

import valuesByMalfunctions


fun editMalfunction(malfunctionName: String, newName: String): Boolean {
    if (malfunctionName == "")
        return false

    if (newName == "")
        return false

    val malfunction = findMalfunction(malfunctionName) ?: return false

    malfunction.name = newName
    return true

}

fun editAttribute(attributeName: String, newName: String): Boolean {
    if (attributeName == "")
        return false

    val attribute = findAttribute(attributeName) ?: return false

    attribute.name = newName
    return true

}

fun editAvailableValue(attributeName: String, value: String, newAttributeName: String, newValueName: String): Boolean {
    if (attributeName == "" || value == "")
        return false

    if (newAttributeName == "" || newValueName == "")
        return false

    val attribute = findAttribute(attributeName) ?: return false

    var isInNormalValues = false

    //Удаляем из возможных значений
    for (currentValue in attribute.availableValues) {
        if (currentValue.equals(value)) {
            attribute.availableValues.remove(currentValue)
            break
        }
    }

    //Удаляем из нормальных значений
    for (currentValue in attribute.normalValues) {
        if (currentValue.equals(value)) {
            attribute.normalValues.remove(currentValue)
            isInNormalValues = true
            break
        }
    }

    //Добавляем в возможные значения
    attribute.availableValues.add(newValueName)

    //Добавляем в нормальные значения, если в них было
    if (isInNormalValues)
        attribute.normalValues.add(newValueName)

    attribute.name = newAttributeName

    return true
}

fun editNormalValue(attributeName: String, value: String, newAttributeName: String, newValueName: String): Boolean {
    if (attributeName == "" || value == "")
        return false

    if (newAttributeName == "" || newValueName == "")
        return false

    val attribute = findAttribute(attributeName) ?: return false

    attribute.name = newAttributeName

    //Проверяем, принадлежит ли новое значение множеству возможных значений признака
    if (!findValue(newValueName, attribute.availableValues))
        return false

    //Проверяем, что новое значение не сломает логику существующих значений при неисправности
    for (valuesByMalfunction in valuesByMalfunctions) {
        for (lValue in valuesByMalfunction.values)
            if (lValue.equals(newValueName))
                return false
    }

    //Удаляем из нормальных значений
    for (currentValue in attribute.normalValues) {
        if (currentValue.equals(value)) {
            attribute.normalValues.remove(currentValue)
            break
        }
    }

    //Добавляем новое нормальное значение
    attribute.normalValues.add(newValueName)

    return true
}

//TODO: Проверка, что новое нормальное значение не существует в множестве значений при неисправности

fun editAttributePicture(malfunctionName: String, attributeName: String,
                         newMalfunctionName: String, newAttributeName: String): Boolean {
    if (malfunctionName == "" || attributeName == "")
        return false

    if (newMalfunctionName == "" || newAttributeName == "")
        return false

    val picture = findAttributePicture(malfunctionName) ?: return false
    val attribute = findAttribute(attributeName) ?: return false

    picture.malfunction.name = newMalfunctionName

    val newAttribute = findAttribute(newAttributeName) ?: return false

    picture.attributes.remove(attribute)
    picture.attributes.add(newAttribute)

    return true
}

fun editValuesByMalfunction(malfunctionName: String, attributeName: String, value: String,
                            newMalfunctionName: String, newAttributeName: String, newValueName: String): Boolean {

    if (malfunctionName == "" || attributeName == "" || value == "")
        return false

    if (newMalfunctionName == "" || newAttributeName == "" || newValueName == "")
        return false

    val valuesByMalfunction = findValuesByMalfunction(malfunctionName, attributeName) ?: return false

    valuesByMalfunction.malfunction.name = newMalfunctionName
    valuesByMalfunction.attribute.name = newAttributeName

    //Проверяем, что новое значение присутствует в множестве допустимых значений
    if (!findValue(newValueName, valuesByMalfunction.attribute.availableValues))
        return false

    for (lValue in valuesByMalfunction.values) {
        // ^v^v^ Ищем позицию значения в значениях при неисправности
        if (lValue.equals(value)) {
            //Если значение не из нормальных
            return if (!findValue(newValueName, valuesByMalfunction.attribute.normalValues)) {
                //То удаляем старое
                valuesByMalfunction.values.remove(lValue)
                //И добавляем новое
                valuesByMalfunction.values.add(newValueName)
                true
            } else {
                false
            }
        }

    }

    return false
}
