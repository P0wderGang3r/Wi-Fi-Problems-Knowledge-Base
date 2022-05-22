package database_functions

import attributePictures
import data_classes.ValuesByAttributeClass
import errors.ErrorClass

/**
 * Изменение названия неисправности
 */
fun editMalfunction(malfunctionName: String, newName: String): ErrorClass {
    if (malfunctionName == "")
        return ErrorClass.EDIT_DEFAULT

    if (newName == "")
        return ErrorClass.EDIT_DEFAULT

    val malfunction = findMalfunction(malfunctionName) ?: return ErrorClass.EDIT_DEFAULT

    malfunction.name = newName
    return ErrorClass.NULL

}

/**
 * Изменение названия признака
 */
fun editAttribute(attributeName: String, newName: String): ErrorClass {
    if (attributeName == "")
        return ErrorClass.EDIT_DEFAULT

    if (newName == "")
        return ErrorClass.EDIT_DEFAULT

    val attribute = findAttribute(attributeName) ?: return ErrorClass.EDIT_DEFAULT

    attribute.name = newName
    return ErrorClass.NULL

}

/**
 * Изменение названия признака и значения признака
 */
fun editAvailableValue(attributeName: String, value: String, newAttributeName: String, newValueName: String): ErrorClass {
    if (attributeName == "" || value == "")
        return ErrorClass.EDIT_DEFAULT

    if (newAttributeName == "" || newValueName == "")
        return ErrorClass.EDIT_DEFAULT

    val attribute = findAttribute(attributeName) ?: return ErrorClass.EDIT_DEFAULT

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

    //Целостность данных - удаляем из нормальных значений
    for (currentValue in attribute.normalValues) {
        if (currentValue.equals(value)) {
            attribute.normalValues.remove(currentValue)
            isInNormalValues = true
            break
        }
    }

    //Добавляем в возможные значения
    attribute.availableValues.add(newValueName)

    //Целостность данных - добавляем в нормальные значения, если данное значение было среди таковых
    if (isInNormalValues)
        attribute.normalValues.add(newValueName)

    return ErrorClass.NULL
}

/**
 * Изменение названия признака и зависимости нормального значения от возможного
 */
fun editNormalValue(attributeName: String, value: String, newAttributeName: String, newValueName: String): ErrorClass {
    if (attributeName == "" || value == "")
        return ErrorClass.EDIT_DEFAULT

    if (newAttributeName == "" || newValueName == "")
        return ErrorClass.EDIT_DEFAULT

    val attribute = findAttribute(attributeName) ?: return ErrorClass.EDIT_DEFAULT

    //Изменяем название признака
    attribute.name = newAttributeName

    //Проверяем, принадлежит ли новое значение множеству возможных значений признака
    if (!findValue(newValueName, attribute.availableValues))
        return ErrorClass.EDIT_DEFAULT

    //Проверяем, что новое значение не сломает логику существующих значений при неисправности
    for (picture in attributePictures) {
        for (valuesByMalfunction in picture.valuesByAttributes)
            for (lValue in valuesByMalfunction.values)
                if (lValue.equals(newValueName))
                    return ErrorClass.EDIT_DEFAULT
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

    return ErrorClass.NULL
}

/**
 * Изменение названия неисправности и зависимости признака от неисправности
 */
fun editAttributePicture(malfunctionName: String, attributeName: String,
                         newMalfunctionName: String, newAttributeName: String): ErrorClass {
    if (malfunctionName == "" || attributeName == "")
        return ErrorClass.EDIT_DEFAULT

    if (newMalfunctionName == "" || newAttributeName == "")
        return ErrorClass.EDIT_DEFAULT

    val picture = findAttributePicture(malfunctionName) ?: return ErrorClass.EDIT_DEFAULT

    //Если признаковая картина не редактируема, то отказ
    if (!picture.isEditable)
        return ErrorClass.EDIT_DEFAULT

    //Изменение названия неисправности
    picture.malfunction.name = newMalfunctionName

    val newAttribute = findAttribute(newAttributeName) ?: return ErrorClass.EDIT_DEFAULT

    picture.valuesByAttributes.remove(findAttributeInPicture(picture, attributeName))
    picture.valuesByAttributes.add(ValuesByAttributeClass(newAttribute))

    return ErrorClass.NULL
}

/**
 * Изменение названий неисправности и признака, зависимости значения признака от признака
 */
fun editValuesByMalfunction(malfunctionName: String, attributeName: String, value: String,
                            newMalfunctionName: String, newAttributeName: String, newValueName: String): ErrorClass {

    if (malfunctionName == "" || attributeName == "" || value == "")
        return ErrorClass.EDIT_DEFAULT

    if (newMalfunctionName == "" || newAttributeName == "" || newValueName == "")
        return ErrorClass.EDIT_DEFAULT

    val picture = findAttributePicture(malfunctionName) ?: return ErrorClass.EDIT_DEFAULT

    if (!picture.isEditable)
        return ErrorClass.EDIT_DEFAULT

    val valuesByMalfunction = findAttributeInPicture(picture, attributeName) ?: return ErrorClass.EDIT_DEFAULT

    //Переименовываем название неисправности и признака
    picture.malfunction.name = newMalfunctionName
    valuesByMalfunction.attribute.name = newAttributeName

    //Проверяем, что новое значение присутствует в множестве допустимых значений
    if (!findValue(newValueName, valuesByMalfunction.attribute.availableValues))
        return ErrorClass.EDIT_DEFAULT

    for (lValue in valuesByMalfunction.values) {
        // ^v^v^ Ищем позицию значения в значениях при неисправности
        if (lValue.equals(value)) {
            //Если значение не из нормальных
            return if (!findValue(newValueName, valuesByMalfunction.attribute.normalValues)) {
                //То удаляем старое
                valuesByMalfunction.values.remove(lValue)
                //И добавляем новое
                valuesByMalfunction.values.add(newValueName)
                ErrorClass.NULL
            } else {
                ErrorClass.EDIT_DEFAULT
            }
        }

    }

    return ErrorClass.EDIT_DEFAULT
}
