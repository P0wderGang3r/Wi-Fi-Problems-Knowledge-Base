package database_functions

import attributePictures
import attributes
import errors.ErrorClass
import malfunctions

/**
 * Удаление неисправности
 */
fun removeMalfunction(malfunctionName: String): ErrorClass {
    val malfunction = findMalfunction(malfunctionName) ?: return ErrorClass.REMOVE_DEFAULT

    //Целостность данных - если используется в картине признаков при неисправности, то отказ
    val picture = findAttributePicture(malfunctionName)
    if (picture != null) {
        return ErrorClass.REMOVE_DEFAULT
    }

    malfunctions.remove(malfunction)
    return ErrorClass.NULL
}

/**
 * Удаление признака
 */
fun removeAttribute(attributeName: String): ErrorClass {
    val attribute = findAttribute(attributeName) ?: return ErrorClass.REMOVE_DEFAULT

    //Целостность данных - если признак из картины признаков при неисправности, то отказ
    for (picture in attributePictures) {
        val attributeInPicture = findAttributeInPicture(picture, attributeName)
        if (attributeInPicture != null) {
            return ErrorClass.REMOVE_DEFAULT
        }
    }

    attributes.remove(attribute)
    return ErrorClass.NULL
}

/**
 * Удаление возможного значения
 */
fun removeAvailableValue(attributeName: String, value: String): ErrorClass {
    val currentAttribute = findAttribute(attributeName) ?: return ErrorClass.REMOVE_DEFAULT

    if (findValue(value, currentAttribute.availableValues)) {

        //Целостность данных - если количество возможных значений больше количества нормальных и...
        if (currentAttribute.availableValues.size > currentAttribute.normalValues.size)
            //удаляемое значение из множества нормальных значений, то отказ
            if (findValue(value, currentAttribute.normalValues)) {
                return ErrorClass.REMOVE_DEFAULT
            }

        currentAttribute.availableValues.remove(value)

        return ErrorClass.NULL
    }

    return ErrorClass.REMOVE_DEFAULT
}

/**
 * Удаление нормального значения
 */
fun removeNormalValue(attributeName: String, value: String): ErrorClass {
    val currentAttribute = findAttribute(attributeName) ?: return ErrorClass.REMOVE_DEFAULT

    if (findValue(value, currentAttribute.normalValues)) {

        //Целостность данных - если нормальных значений задано не больше одного, то не удаляем
        if (currentAttribute.normalValues.size <= 1)
            return ErrorClass.REMOVE_DEFAULT

        currentAttribute.normalValues.remove(value)
        return ErrorClass.NULL
    }

    return ErrorClass.REMOVE_DEFAULT
}

/**
 * Удаление признака из признаков при неисправности
 */
fun removeAttributeFromPicture(malfunctionName: String, attributeName: String): ErrorClass {
    val picture = findAttributePicture(malfunctionName) ?: return ErrorClass.REMOVE_DEFAULT
    val attributeInPicture = findAttributeInPicture(picture, attributeName) ?: return ErrorClass.REMOVE_DEFAULT

    //Если картина из множества не редактируемых, то не удаляем
    if (!picture.isEditable)
        return ErrorClass.REMOVE_DEFAULT

    picture.valuesByAttributes.remove(attributeInPicture)

    //Если в картине не осталось признаков, то удаляем картину
    if (picture.valuesByAttributes.size == 0) {
        attributePictures.remove(picture)
    }

    return ErrorClass.NULL
}

/**
 * Удаление значения признака при неисправности
 */
fun removeValueFromValuesByMalfunction(malfunctionName: String, attributeName: String, value: String): ErrorClass {

    val picture = findAttributePicture(malfunctionName) ?: return ErrorClass.REMOVE_DEFAULT

    //Если картина из множества не редактируемых, то не удаляем
    if (!picture.isEditable)
        return ErrorClass.REMOVE_DEFAULT
    
    val attributeInPicture = findAttributeInPicture(picture, attributeName) ?: return ErrorClass.REMOVE_DEFAULT

    //Если находим значение в множестве значений признака при неисправности, то удаляем
    for (lValue in attributeInPicture.values) {
        if (lValue == value) {
            attributeInPicture.values.remove(value)
            return ErrorClass.NULL
        }
    }

    return ErrorClass.REMOVE_DEFAULT
}