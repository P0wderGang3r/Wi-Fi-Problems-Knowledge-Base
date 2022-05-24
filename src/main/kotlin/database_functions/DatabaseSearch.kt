package database_functions

import attributePictures
import attributes
import data_classes.*
import malfunctions

/**
 * Проверка, есть ли данное значение среди множества значений
 */
fun findValue(value: String, values: ArrayList<AttributeValueClass>): AttributeValueClass? {
    for (valueInList in values) {
        if (valueInList.value.equals(value)) {
            return valueInList
        }
    }

    return null
}

/**
 * Поиск неисправности среди множества неисправностей
 */
fun findMalfunction(name: String): MalfunctionClass? {
    for (malfunction in malfunctions) {
        if (malfunction.name.equals(name)) {
            return malfunction
        }
    }

    return null
}

/**
 * Поиск признаков среди множества признаков
 */
fun findAttribute(name: String): AttributeClass? {
    for (attribute in attributes) {
        if (attribute.name.equals(name)) {
            return attribute
        }
    }

    return null
}

/**
 * Поиск признаков при неисправности из множества признаков при неисправности
 */
fun findAttributePicture(name: String): AttributePictureClass? {
    for (attributePicture in attributePictures) {
        if (attributePicture.malfunction.name.equals(name)) {
            return attributePicture
        }
    }

    return null
}

/**
 * Поиск признака при неисправности из множества признаков при неисправности
 */
fun findAttributeInPicture(picture: AttributePictureClass, name: String): ValuesByAttributeClass? {
    for (attribute in picture.valuesByAttributes) {
        if (attribute.attribute.name.equals(name)) {
            return attribute
        }
    }

    return null
}