package database_functions

import attributePictures
import attributes
import malfunctions

/**
 * Удаление неисправности
 */
fun removeMalfunction(malfunctionName: String): Boolean {
    val malfunction = findMalfunction(malfunctionName)

    if (malfunction != null) {
        malfunctions.remove(malfunction)
        return true
    }

    return false
}

/**
 * Удаление признака
 */
fun removeAttribute(attributeName: String): Boolean {
    val attribute = findAttribute(attributeName)

    if (attribute != null) {
        attributes.remove(attribute)
        return true
    }

    return false
}

/**
 * Удаление возможного значения
 */
fun removeAvailableValue(attributeName: String, value: String): Boolean {
    val currentAttribute = findAttribute(attributeName)

    if (currentAttribute != null) {
        if (findValue(value, currentAttribute.availableValues)) {

            currentAttribute.availableValues.remove(value)
            return true
        }
    }

    return false
}

/**
 * Удаление нормального значения
 */
fun removeNormalValue(attributeName: String, value: String): Boolean {
    val currentAttribute = findAttribute(attributeName)

    if (currentAttribute != null) {
        if (findValue(value, currentAttribute.normalValues)) {

            currentAttribute.normalValues.remove(value)
            return true
        }
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

    if (!picture.isEditable)
        return false

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

    if (!picture.isEditable)
        return false

    val attributeInPicture = findAttributeInPicture(picture, attributeName) ?: return false

    for (lValue in attributeInPicture.values) {
        if (lValue == value) {
            attributeInPicture.values.remove(value)
            return true
        }
    }

    return false
}