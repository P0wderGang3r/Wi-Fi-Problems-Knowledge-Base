package database_functions

import attributePictures
import attributes
import malfunctions

/**
 * Удаление неисправности
 */
fun removeMalfunction(malfunctionName: String): Boolean {
    val malfunction = findMalfunction(malfunctionName) ?: return false

    //Целостность данных - если используется в картине признаков при неисправности, то отказ
    val picture = findAttributePicture(malfunctionName)
    if (picture != null) {
        return false
    }

    malfunctions.remove(malfunction)
    return true
}

/**
 * Удаление признака
 */
fun removeAttribute(attributeName: String): Boolean {
    val attribute = findAttribute(attributeName) ?: return false

    //Целостность данных - если признак из картины признаков при неисправности, то отказ
    for (picture in attributePictures) {
        val attributeInPicture = findAttributeInPicture(picture, attributeName)
        if (attributeInPicture != null) {
            return false
        }
    }

    attributes.remove(attribute)
    return true
}

/**
 * Удаление возможного значения
 */
fun removeAvailableValue(attributeName: String, value: String): Boolean {
    val currentAttribute = findAttribute(attributeName) ?: return false

    if (findValue(value, currentAttribute.availableValues)) {

        //Целостность данных - если количество возможных значений больше количества нормальных и...
        if (currentAttribute.availableValues.size > currentAttribute.normalValues.size)
            //удаляемое значение из множества нормальных значений, то отказ
            if (findValue(value, currentAttribute.normalValues)) {
                return false
            }

        currentAttribute.availableValues.remove(value)

        return true
    }

    return false
}

/**
 * Удаление нормального значения
 */
fun removeNormalValue(attributeName: String, value: String): Boolean {
    val currentAttribute = findAttribute(attributeName) ?: return false

    if (findValue(value, currentAttribute.normalValues)) {

        //Целостность данных - если нормальных значений задано не больше одного, то не удаляем
        if (currentAttribute.normalValues.size <= 1)
            return false

        currentAttribute.normalValues.remove(value)
        return true
    }

    return false
}

/**
 * Удаление признака из признаков при неисправности
 */
fun removeAttributeFromPicture(malfunctionName: String, attributeName: String): Boolean {
    val picture = findAttributePicture(malfunctionName) ?: return false
    val attributeInPicture = findAttributeInPicture(picture, attributeName) ?: return false

    picture.valuesByAttributes.remove(attributeInPicture)

    //Если картина из множества не редактируемых, то не удаляем
    if (!picture.isEditable)
        return false

    //Если в картине не осталось признаков, то удаляем картину
    if (picture.valuesByAttributes.size == 0) {
        attributePictures.remove(picture)
    }

    return true
}

/**
 * Удаление значения признака при неисправности
 */
fun removeValueFromValuesByMalfunction(malfunctionName: String, attributeName: String, value: String): Boolean {

    val picture = findAttributePicture(malfunctionName) ?: return false

    //Если картина из множества не редактируемых, то не удаляем
    if (!picture.isEditable)
        return false

    val attributeInPicture = findAttributeInPicture(picture, attributeName) ?: return false

    //Если находим значение в множестве значений признака при неисправности, то удаляем
    for (lValue in attributeInPicture.values) {
        if (lValue == value) {
            attributeInPicture.values.remove(value)
            return true
        }
    }

    return false
}