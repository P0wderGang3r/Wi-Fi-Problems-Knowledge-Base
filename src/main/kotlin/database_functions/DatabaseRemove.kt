package database_functions

import attributePictures
import attributes
import malfunctions
import valuesByMalfunctions

fun removeMalfunction(malfunctionName: String): Boolean {
    val malfunction = findMalfunction(malfunctionName)

    if (malfunction != null) {
        malfunctions.remove(malfunction)
        return true
    }

    return false
}

fun removeAttribute(attributeName: String): Boolean {
    val attribute = findAttribute(attributeName)

    if (attribute != null) {
        attributes.remove(attribute)
        return true
    }

    return false
}

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

private fun removeAttributePicture(malfunctionName: String): Boolean {
    val currentMalfunction = findMalfunction(malfunctionName)

    if (currentMalfunction != null) {
        for (picture in attributePictures) {
            if (picture.malfunction == currentMalfunction) {

                attributePictures.remove(picture)
                return true
            }
        }

    }

    return false
}

fun removeAttributeFromPicture(malfunctionName: String, attributeName: String): Boolean {
    val currentMalfunction = findMalfunction(malfunctionName)
    val currentAttribute = findAttribute(attributeName)

    if (currentMalfunction != null && currentAttribute != null) {
        for (picture in attributePictures) {
            if (picture.malfunction == currentMalfunction) {

                for (attribute in picture.attributes) {
                    if (attribute == currentAttribute) {

                        picture.attributes.remove(currentAttribute)

                        if (picture.attributes.size == 0)
                            return removeAttributePicture(malfunctionName)

                        return true
                    }
                }

            }
        }
    }

    return false
}

private fun removeValuesByMalfunction(malfunctionName: String, attributeName: String): Boolean {
    val currentMalfunction = findMalfunction(malfunctionName)
    val currentAttribute = findAttribute(attributeName)

    if (currentMalfunction != null && currentAttribute != null) {

        //Ищем неисправность и признак, соответствующие искомому значению при неисправности
        for (malfunctionClass in valuesByMalfunctions) {
            if (malfunctionClass.malfunction == currentMalfunction &&
                malfunctionClass.attribute == currentAttribute) {

                //Если находим, то удаляем
                valuesByMalfunctions.remove(malfunctionClass)
                return true
            }
        }
    }

    return false

}

fun removeValueFromValuesByMalfunction(malfunctionName: String, attributeName: String, value: String): Boolean {
    val currentMalfunction = findMalfunction(malfunctionName)
    val currentAttribute = findAttribute(attributeName)

    if (currentMalfunction != null && currentAttribute != null) {

        //Ищем неисправность и признак, соответствующие искомому значению при неисправности
        for (malfunctionClass in valuesByMalfunctions) {
            if (malfunctionClass.malfunction == currentMalfunction &&
                malfunctionClass.attribute == currentAttribute) {

                //Проверяем, существует ли уже такое значение
                for (valueByMalfunction in malfunctionClass.values) {
                    if (value.equals(valueByMalfunction)) {
                        //Если да, то удаляем
                        malfunctionClass.values.remove(value)

                        if (malfunctionClass.values.size == 0)
                            return removeValuesByMalfunction(malfunctionName, attributeName)

                        return true
                    }
                }

            }
        }
    }

    return false
}