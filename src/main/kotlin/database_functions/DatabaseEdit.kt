package database_functions

import attributePictures
import data_classes.ValuesByAttributeClass
import errors.ErrorClass

/**
 * Изменение названия неисправности
 */
fun editMalfunction(malfunctionName: String, newName: String): ErrorClass {
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
    if (newAttributeName == "" || newValueName == "")
        return ErrorClass.EDIT_DEFAULT

    val attribute = findAttribute(attributeName) ?: return ErrorClass.EDIT_DEFAULT

    //Изменяем название признака
    attribute.name = newAttributeName

    val currentValue = findValue(value, attribute.availableValues) ?: return ErrorClass.EDIT_DEFAULT

    //Изменяем значение
    currentValue.value = newValueName

    return ErrorClass.NULL
}

/**
 * Изменение названия признака и зависимости нормального значения от возможного
 */
fun editNormalValue(attributeName: String, value: String, newAttributeName: String, newValueName: String): ErrorClass {
    if (newAttributeName == "" || newValueName == "")
        return ErrorClass.EDIT_DEFAULT

    val attribute = findAttribute(attributeName) ?: return ErrorClass.EDIT_DEFAULT

    //Изменяем название признака
    attribute.name = newAttributeName

    val currentNormalValue = findValue(value, attribute.normalValues) ?: return ErrorClass.EDIT_DEFAULT

    val newNormalValue = findValue(newValueName, attribute.availableValues) ?: return ErrorClass.EDIT_DEFAULT

    //Проверяем, не принадлежит ли новое значение множеству нормальных значений
    if (findValue(newValueName, attribute.normalValues) != null)
        return ErrorClass.EDIT_DEFAULT

    //Проверяем, что новое значение не сломает логику существующих значений при неисправности
    for (picture in attributePictures) {
        for (valuesByMalfunction in picture.valuesByAttributes)
            for (lValue in valuesByMalfunction.values)
                if (lValue.equals(newValueName))
                    return ErrorClass.EDIT_DEFAULT
    }

    //Добавляем новое нормальное значение
    attribute.normalValues.remove(currentNormalValue)
    attribute.normalValues.add(newNormalValue)

    return ErrorClass.NULL
}

/**
 * Изменение названия неисправности и зависимости признака от неисправности
 */
fun editAttributePicture(malfunctionName: String, attributeName: String,
                         newMalfunctionName: String, newAttributeName: String): ErrorClass {
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
    val newValue = findValue(newValueName, valuesByMalfunction.attribute.availableValues) ?: return ErrorClass.EDIT_DEFAULT

    //Если значений признаков при неисправности не существует, то просто добавляем новое значение
    if (valuesByMalfunction.values.size == 0) {

        //Если новое значение не принадлежит множеству нормальных значений
        if (findValue(newValueName, valuesByMalfunction.attribute.normalValues) != null)
            return ErrorClass.EDIT_DEFAULT

        //то добавляем в картину
        valuesByMalfunction.values.add(newValue)
        return ErrorClass.NULL
    }

    //Иначе ищем предыдущее значение в списке признаков при неисправности
    for (lValue in valuesByMalfunction.values) {
        // ^v^v^ Ищем позицию значения в значениях при неисправности
        if (lValue.value.equals(value)) {
            //Если новое значение не из нормальных
            if (findValue(newValueName, valuesByMalfunction.attribute.normalValues) == null) {
                //То удаляем старое
                valuesByMalfunction.values.remove(lValue)
                //И добавляем новое
                valuesByMalfunction.values.add(newValue)
                return ErrorClass.NULL
            }
        }
    }

    return ErrorClass.EDIT_DEFAULT
}
