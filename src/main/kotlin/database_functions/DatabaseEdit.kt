package database_functions

import attributePictures
import data_classes.ValuesByAttributeClass

/**
 * Изменение названия неисправности
 */
fun editMalfunction(malfunctionName: String, newName: String): Boolean {
    if (malfunctionName == "")
        return false

    if (newName == "")
        return false

    val malfunction = findMalfunction(malfunctionName) ?: return false

    malfunction.name = newName
    return true

}

/**
 * Изменение названия признака
 */
fun editAttribute(attributeName: String, newName: String): Boolean {
    if (attributeName == "")
        return false

    if (newName == "")
        return false

    val attribute = findAttribute(attributeName) ?: return false

    attribute.name = newName
    return true

}

/**
 * Изменение названия признака и значения признака
 */
fun editAvailableValue(attributeName: String, value: String, newAttributeName: String, newValueName: String): Boolean {
    if (attributeName == "" || value == "")
        return false

    if (newAttributeName == "" || newValueName == "")
        return false

    val attribute = findAttribute(attributeName) ?: return false

    //Изменяем название признака
    attribute.name = newAttributeName

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

    //Добавляем в нормальные значения, если данное значение было среди таковых
    if (isInNormalValues)
        attribute.normalValues.add(newValueName)

    return true
}

/**
 * Изменение названия признака и зависимости нормального значения от возможного
 */
fun editNormalValue(attributeName: String, value: String, newAttributeName: String, newValueName: String): Boolean {
    if (attributeName == "" || value == "")
        return false

    if (newAttributeName == "" || newValueName == "")
        return false

    val attribute = findAttribute(attributeName) ?: return false

    //Изменяем название признака
    attribute.name = newAttributeName

    //Проверяем, принадлежит ли новое значение множеству возможных значений признака
    if (!findValue(newValueName, attribute.availableValues))
        return false

    //Проверяем, что новое значение не сломает логику существующих значений при неисправности
    for (picture in attributePictures) {
        for (valuesByMalfunction in picture.valuesByAttributes)
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

/**
 * Изменение названия неисправности и зависимости признака от неисправности
 */
fun editAttributePicture(malfunctionName: String, attributeName: String,
                         newMalfunctionName: String, newAttributeName: String): Boolean {
    if (malfunctionName == "" || attributeName == "")
        return false

    if (newMalfunctionName == "" || newAttributeName == "")
        return false

    val picture = findAttributePicture(malfunctionName) ?: return false

    //Если признаковая картина не редактируема, то отказ
    if (!picture.isEditable)
        return false

    //Изменение названия неисправности
    picture.malfunction.name = newMalfunctionName

    val newAttribute = findAttribute(newAttributeName) ?: return false

    picture.valuesByAttributes.remove(findAttributeInPicture(picture, attributeName))
    picture.valuesByAttributes.add(ValuesByAttributeClass(newAttribute))

    return true
}

/**
 * Изменение названий неисправности и признака, зависимости значения признака от признака
 */
fun editValuesByMalfunction(malfunctionName: String, attributeName: String, value: String,
                            newMalfunctionName: String, newAttributeName: String, newValueName: String): Boolean {

    if (malfunctionName == "" || attributeName == "" || value == "")
        return false

    if (newMalfunctionName == "" || newAttributeName == "" || newValueName == "")
        return false

    val picture = findAttributePicture(malfunctionName) ?: return false

    if (!picture.isEditable)
        return false

    val valuesByMalfunction = findAttributeInPicture(picture, attributeName) ?: return false

    //Переименовываем название неисправности и признака
    picture.malfunction.name = newMalfunctionName
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
